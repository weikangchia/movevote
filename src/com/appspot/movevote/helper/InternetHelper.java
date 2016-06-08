package com.appspot.movevote.helper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class InternetHelper {
	private HttpURLConnection connection;
	private URL url;

	public InternetHelper(String url) {
		try {
			this.url = new URL(url);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String downloadJson() {
		String json = null;

		try {
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setConnectTimeout(5000);
			connection.connect();

			if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
				BufferedReader br = new BufferedReader(
						new InputStreamReader(connection.getInputStream(), "UTF-8"));
				json = br.readLine();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			connection.disconnect();
		}

		return json;
	}
}