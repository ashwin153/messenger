package com.ashwin.messenger.scraper;

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.apache.http.Header;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class ScraperUtils {

	public static HttpClient getHttpClient() {
		List<Header> headers = new ArrayList<Header>();
		headers.add(new BasicHeader(HttpHeaders.ACCEPT, "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8"));
		headers.add(new BasicHeader(HttpHeaders.ACCEPT_ENCODING, "gzip,deflate,sdch"));
		headers.add(new BasicHeader(HttpHeaders.ACCEPT_LANGUAGE, "en-US,en;q=0.8"));
		headers.add(new BasicHeader(HttpHeaders.CACHE_CONTROL, "max-age=0"));
		headers.add(new BasicHeader(HttpHeaders.CONNECTION, "keep-alive"));
		headers.add(new BasicHeader(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded"));
		headers.add(new BasicHeader(HttpHeaders.USER_AGENT, "Mozilla/5.0 (Windows NT 6.3; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/37.0.2049.0 Safari/537.36"));
		return HttpClientBuilder.create().setDefaultHeaders(headers).build();
	}
	
	public static String getMD5(String str) {
		try {
			MessageDigest digest = MessageDigest.getInstance("MD5");		
			return new BigInteger(1, digest.digest(str.getBytes())).toString(16);
		} catch(NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/** Authenticates a user. Returns true if auth was success and false if not. */
	public static boolean authenticate(HttpClient client, String eid, String password) throws ClientProtocolException, IOException {
		// Get the index.html page to initialize the basic cookies you need to auth.
		getDocument(client, "https://utdirect.utexas.edu/index.html");
		
		HttpPost post = new HttpPost("https://utdirect.utexas.edu/security-443/logon_check.logonform");
		post.addHeader("Host", "utdirect.utexas.edu");
		post.addHeader("Origin", "https://utdirect.utexas.edu");
		post.addHeader("Referer", "https://utdirect.utexas.edu/security-443/logon_check.logonform");
					
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
	    df.setTimeZone(TimeZone.getTimeZone("CST"));
	    
		List<NameValuePair> params = new ArrayList <NameValuePair>();
		params.add(new BasicNameValuePair("CDT", df.format(new Date())));
		params.add(new BasicNameValuePair("NEW_PASSWORD", ""));
		params.add(new BasicNameValuePair("CONFIRM_NEW_PASSWORD", ""));
		params.add(new BasicNameValuePair("PASSWORDS", ""));
		params.add(new BasicNameValuePair("LOGON", eid));
		params.add(new BasicNameValuePair("PASSWORDS", password));
		post.setEntity(new UrlEncodedFormEntity(params));
				
		// If the response is has only 1 'Set-cookie' header, it is a failed request
		HttpResponse response = client.execute(post);
		EntityUtils.consume(response.getEntity());
		return response.getHeaders("Set-cookie").length > 1;
	}
	
	/** Logs off from the users account. */
	public static void logoff(HttpClient client) throws ClientProtocolException, IOException {
		HttpGet get = new HttpGet("https://utdirect.utexas.edu/security-443/logoff.cgi");
		client.execute(get);
	}
	
	/** Performs an HttpGet and returns a JSoup Document for a particular website. */
	public static Document getDocument(HttpClient client, String url) throws ParseException, ClientProtocolException, IOException {
		HttpGet get = new HttpGet(url);
		HttpResponse response = client.execute(get);

		String html = EntityUtils.toString(response.getEntity());
		EntityUtils.consume(response.getEntity());
		
		return Jsoup.parse(html);
	}
}
