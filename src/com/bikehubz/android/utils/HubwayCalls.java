package com.bikehubz.android.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class HubwayCalls
{
	public static final String GET_LIVE_STATION_DATA = "http://thehubway.com/data/stations/bikeStations.xml";

	public static JSONObject getJSON(String uri)
	{
		HttpClient httpclient = new DefaultHttpClient();
		HttpResponse response;
		JSONObject finalResult = null;
		try
		{
			response = httpclient.execute(new HttpGet(uri));
			StatusLine statusLine = response.getStatusLine();
			if (statusLine.getStatusCode() == HttpStatus.SC_OK)
			{
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(
								response.getEntity().getContent(), "UTF-8"));
				String json = reader.readLine();
				JSONTokener tokener = new JSONTokener(json);
				finalResult = new JSONObject(tokener);
			} else
			{
				// Closes the connection.
				response.getEntity().getContent().close();
				throw new IOException(statusLine.getReasonPhrase());
			}
		} catch (ClientProtocolException e)
		{
			// TODO Handle problems..
		} catch (IOException e)
		{
			// TODO Handle problems..
		} catch (JSONException e)
		{
			// TODO Auto-generated catch block
		}
		return finalResult;
	}

	public static String getXML(String uri)
	{
		HttpClient httpclient = new DefaultHttpClient();
		HttpResponse response;
		String xml = null;
		try
		{
			response = httpclient.execute(new HttpGet(uri));
			StatusLine statusLine = response.getStatusLine();
			if (statusLine.getStatusCode() == HttpStatus.SC_OK)
			{
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(
								response.getEntity().getContent(), "UTF-8"));
				xml = reader.readLine();
			} else
			{
				// Closes the connection.
				response.getEntity().getContent().close();
				throw new IOException(statusLine.getReasonPhrase());
			}
		} catch (ClientProtocolException e)
		{
			// TODO Handle problems..
		} catch (IOException e)
		{
			// TODO Handle problems..
		}
		return xml;
	}
}
