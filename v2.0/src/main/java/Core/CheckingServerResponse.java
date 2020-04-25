package Core;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class CheckingServerResponse {

	public static int HTTPLinkstatusChecker(String linkUrl) throws IOException
	{
		int s=0;
		URL url = new URL(linkUrl);
		HttpURLConnection h=(HttpURLConnection)url.openConnection();
		h.setConnectTimeout(10000);
		h.connect();
		int x = h.getResponseCode();
		return x;
	
	}
	
	public static void main(String[] args) throws IOException {
		
		System.out.println(CheckingServerResponse.HTTPLinkstatusChecker("http://192.168.1.98:9020/QC/WorkingStandards?id=3122&tabId=0"));
		System.out.println("Testing");
	}

	}


