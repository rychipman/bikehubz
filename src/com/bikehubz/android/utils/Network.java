package com.bikehubz.android.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class Network
{
	public static InputStream downloadUrl(String urlString) throws IOException
	{
		URL url = new URL(urlString);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setReadTimeout(10000 /* milliseconds */);
		conn.setConnectTimeout(15000 /* milliseconds */);
		conn.setRequestMethod("GET");
		conn.setDoInput(true);
		// Starts the query
		conn.connect();
		return conn.getInputStream();
	}

	public static ArrayList<HashMap<String, String>> getListFromURL(String url)
	{
		InputStream stream = null;
		ArrayList<HashMap<String, String>> kvp = null;
		HubwayXMLParser parser = new HubwayXMLParser();
		try
		{
			stream = downloadUrl(url);
			// stations = parser.parse(stream);
			kvp = parser.parseForKVP(stream);
			// Makes sure that the InputStream is closed after the app is
			// finished using it.
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			if (stream != null)
			{
				try
				{
					stream.close();
				} catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}
		return kvp;
	}
}
