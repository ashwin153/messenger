package com.ashwin.messenger.scraper;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.ashwin.messenger.model.Course;
import com.ashwin.messenger.model.Student;
import com.ashwin.messenger.model.Timing;
import com.ashwin.messenger.model.Timing.Day;

public class StudentScraper {
	
	public static Student scrape(String eid, String password) {
		try {
			HttpClient client = ScraperUtils.getHttpClient();
			if(!ScraperUtils.authenticate(client, eid, password))
				return null;
			
			Student student = new Student();
			student.setEID(ScraperUtils.getMD5(eid));
			
			scrapeHousing(client, student);
			scrapeAddresses(client, student);
			scrapeAdmission(client, student);
			scrapeClasses(client, student);
			
			ScraperUtils.logoff(client);
			return student;
		} catch(Exception e) {
			// If there is an exception thrown, write to logs or print out stack trace
			// and return null. This is better than passing the errors back up the service.
			e.printStackTrace();
			return null;
		}
	}
	
	protected static void scrapeHousing(HttpClient client, Student student) throws ParseException, ClientProtocolException, IOException {
		Document housing = ScraperUtils.getDocument(client, "https://utdirect.utexas.edu/hfis/index.WBX");
		
		// Scrape the name of the student
		String[] names = housing.select("#service_content h1:first-of-type").first().html().split(" ");	
		student.setName(names[0].toUpperCase() + " " + names[names.length - 1].toUpperCase());

		// Scrape the name of their dorm room
		String dorm = housing.select("ul li span.inputfield").first().html();
		Pattern pattern = Pattern.compile("\\w\\d+\\s");
		Matcher matcher = pattern.matcher(dorm);

		if(matcher.find()) 
			student.setDorm(dorm.substring(matcher.start(), matcher.end()));
	}
	
	protected static void scrapeAddresses(HttpClient client, Student student) throws ParseException, ClientProtocolException, IOException, java.text.ParseException {
		// Get their phone/address/email
		Document userInfo = ScraperUtils.getDocument(client, "https://utdirect.utexas.edu/apps/utd/all_my_addresses/");
		
//		// This information is unneccesary, don't really need to scrape this from the user
//		String addressLine1 = userInfo.select("#nr_penp_address_ln_1").first().attr("value");
//		String addressLine2 = userInfo.select("#nr_penp_address_ln_2").first().attr("value");
//		String city = userInfo.select("#nr_penp_city").first().attr("value");
//		String state = userInfo.select("#nr_penp_state").first().attr("value");
//		String zipCode = userInfo.select("#nr_penp_zip_code").first().attr("value");
//		System.out.println("ADDRESS: " + addressLine1 + addressLine2 + ", " + city + ", " + state + " " + zipCode);
		
		String email = userInfo.select("#nr_emal_emailinternet_addr1").first().attr("value");
		student.setEmail(email);
		
		String phone = userInfo.select("#nr_phon_phone_1").first().attr("value");
		student.setPhone(phone.replaceAll("\\D", ""));
		
		SimpleDateFormat inputFormat = new SimpleDateFormat("MM/dd/yy");
		String birthdayString = userInfo.select("form[action=/apps/utd/all_my_addresses/] div.form_field span.field_value").get(1).html();
		Date birthday = inputFormat.parse(birthdayString);
		student.setBirthday(birthday.getTime());
	}
	
	protected static void scrapeAdmission(HttpClient client, Student student) throws ParseException, ClientProtocolException, IOException {
		// Get their major and graduating class
		Document admission = ScraperUtils.getDocument(client, "https://utdirect.utexas.edu/apps/adm/mystatus/admission/00/");
		Elements elements = admission.select(".statusItemDetails p:first-child strong");
		
		int year = Integer.valueOf(elements.get(0).html().replaceAll("[^0-9]", ""));
		student.setYear(year);
		
		String major = elements.get(1).html();
		student.setMajor(major);
		
		String college = elements.get(2).html();
		student.setCollege(college);
	}
	
	protected static void scrapeClasses(HttpClient client, Student student)  throws ParseException, ClientProtocolException, IOException, java.text.ParseException {
		Document registration = ScraperUtils.getDocument(client, "https://utdirect.utexas.edu/registration/classlist.WBX?sem=" + getSemester());
		Elements classes = registration.select("tbody tr[valign=top]");
		SimpleDateFormat inputFormat = new SimpleDateFormat("h:mma");
		
		for(Element tr : classes) {
			Elements fields = tr.select("td");
			
			int index = fields.get(1).html().trim().lastIndexOf(" ");
			String code = fields.get(0).html();
			String department = fields.get(1).html().substring(0, index);
			String number =  fields.get(1).html().substring(index + 1);
			String name = fields.get(2).html();
			Course course = new Course(null, department, code, name, number, null);
			
			// Scrape array fields, and parse them back in the subsequent FOR loop
			Elements buildings = fields.get(3).select("a");
			String[] rooms = fields.get(4).html().split("<br />");
			String[] dates = fields.get(5).html().split("<br />");
			String[] times = fields.get(6).html().split("<br />");
			
			for(int i = 0; i < rooms.length; i++) {
				String building = buildings.get(i).html();
				String room = rooms[i];
				
				String[] timesList = times[i].split("-");
				long startTime = inputFormat.parse(timesList[0].trim()).getTime();
				long endTime = inputFormat.parse(timesList[1].trim()).getTime();
				List<Day> days = getDays(dates[i]);
				
				for(Day day : days) {
					Timing timing = new Timing(null, building, room, day, startTime, endTime);
					course.addTiming(timing);
				}
			}
			
			student.addCourse(course);
		}
	}
	
	// Converts a day string (ie. MWF into a List of Strings: MONDAY, WEDNESDAY, FRIDAY)
	protected static List<Day> getDays(String str) {
		List<Day> days = new ArrayList<Day>();
		for(int i = 0; i < str.length(); i++) {
			switch(str.charAt(i)) {
				case 'M': days.add(Day.MONDAY); break;
				case 'W': days.add(Day.WEDNESDAY); break;
				case 'F': days.add(Day.FRIDAY); break;
				case 'T': 
					if(i == str.length() - 1 || str.charAt(i + 1) != 'H')
						days.add(Day.TUESDAY);
					else
						days.add(Day.THURSDAY);
					break;
			}
		}
		
		return days;
	}
	
	protected static String getSemester() {
		Calendar calendar = Calendar.getInstance();
		String year = String.valueOf(calendar.get(Calendar.YEAR));
		
		// If the month is after August, get fall term classes
		if(calendar.get(Calendar.MONTH) >= Calendar.AUGUST)
			return year + "9";
		// If the month is after May, get summer term classes
		else if (calendar.get(Calendar.MONTH) >= Calendar.MAY)
			return year + "6";
		// If the month is before May, get the spring term classes
		else
			return year + "2";
	}
}
