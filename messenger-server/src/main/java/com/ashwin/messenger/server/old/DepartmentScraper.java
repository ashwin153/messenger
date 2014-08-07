package com.ashwin.messenger.server.old;
//package com.ashwin.messenger.old;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
//import org.apache.http.client.ClientProtocolException;
//import org.apache.http.impl.client.DefaultHttpClient;
//import org.jsoup.nodes.Document;
//import org.jsoup.nodes.Element;
//import org.jsoup.select.Elements;
//
//public class DepartmentScraper {
//
//	public static List<Department> scrape(String eid, String password) throws ClientProtocolException, IOException {
//		DefaultHttpClient client = new DefaultHttpClient();
//		ScraperUtils.authenticate(client, eid, password);
//	
//		Document doc = ScraperUtils.getDocument(client, "https://utdirect.utexas.edu/registrar/nrclav/index.WBX?s_ccyys=20149");
//		Elements elements = doc.select("#s_fos_fl option[selected!=selected]");
//		List<Department> departments = new ArrayList<Department>();
//		
//		for(Element option : elements)
//			departments.add(new Department(null, option.html().split("-")[1].trim(), option.attr("value")));
//		
//		ScraperUtils.logoff(client);
//		return departments;
//	}
//}
