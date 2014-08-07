package com.ashwin.messenger.server.old;
//package com.ashwin.messenger.old;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
//import org.apache.http.HttpResponse;
//import org.apache.http.NameValuePair;
//import org.apache.http.ParseException;
//import org.apache.http.client.ClientProtocolException;
//import org.apache.http.client.entity.UrlEncodedFormEntity;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.impl.client.DefaultHttpClient;
//import org.apache.http.message.BasicNameValuePair;
//import org.apache.http.util.EntityUtils;
//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
//import org.jsoup.nodes.Element;
//import org.jsoup.select.Elements;
//
//public class BuildingScraper {
//
//	public List<Building> scrape() throws ParseException, ClientProtocolException, IOException  {
//		DefaultHttpClient client = new DefaultHttpClient();
//		Document doc = ScraperUtils.getDocument(client, "http://www.utexas.edu/maps/main/buildings/");
//		Elements elements = doc.select("table").get(1).select("tbody tr");
//		List<Building> buildings = new ArrayList<Building>();
//		
//		for(Element tr : elements) {
//			if(tr.select("td a").size() > 0) {
//				String code = tr.select("th").first().html();
//				String address = getAddress(client, code);
//				String name = tr.select("td a").first().html();
//				buildings.add(new Building(null, name, code, address));
//			}
//		}
//		
//		return buildings;
//	}
//	
//	protected static String getAddress(DefaultHttpClient client, String code) throws ClientProtocolException, IOException {
//		HttpPost post = new HttpPost("http://www.utexas.edu/documentsolutions/mail/mailcodes/");
//		post.addHeader("Host", "www.utexas.edu");
//		post.addHeader("Connection", "keep-alive");
//		post.addHeader("Cache-Control", "max-age=0");
//		post.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
//		post.addHeader("Origin", "http://www.utexas.edu");
//		post.addHeader("User-Agent", ScraperUtils.USER_AGENT);
//		post.addHeader("Content-Type", "application/x-www-form-urlencoded");
//		post.addHeader("Referer", "Referer: http://www.utexas.edu/documentsolutions/mail/mailcodes/");
//		post.addHeader("Accept-Encoding", "gzip,deflate,sdch");
//		post.addHeader("Accept-Language", "en-US,en;q=0.8");
//		
//		List<NameValuePair> params = new ArrayList <NameValuePair>();
//		params.add(new BasicNameValuePair("mcba", code));
//		params.add(new BasicNameValuePair("lookup", "Lookup"));	
//		post.setEntity(new UrlEncodedFormEntity(params));
//		
//		HttpResponse response = client.execute(post);
//		String html = EntityUtils.toString(response.getEntity());
//		EntityUtils.consume(response.getEntity());
//		
//		Document doc = Jsoup.parse(html);
//		Elements elements = doc.select("table tbody tr td");
//		if(elements.size() < 4)
//			return null;
//		
//		return elements.get(3).html().replaceAll("Stop " + elements.get(0).html() + ", ", "");
//	}
//}
