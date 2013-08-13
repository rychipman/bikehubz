package com.bikehubz.android.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Xml;

public class HubwayXMLParser
{
	private static final String ns = null;

	public List<Station> parse(InputStream in) throws XmlPullParserException,
			IOException
	{
		try
		{
			XmlPullParser parser = Xml.newPullParser();
			parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
			parser.setInput(in, null);
			parser.nextTag();
			return readFeed(parser);
		} finally
		{
			in.close();
		}
	}

	public ArrayList<HashMap<String, String>> parseForKVP(InputStream in)
			throws XmlPullParserException, IOException
	{
		return getKeyValuePairs(parse(in));
	}

	private List<Station> readFeed(XmlPullParser parser)
			throws XmlPullParserException, IOException
	{
		List<Station> stations = new ArrayList<Station>();

		parser.require(XmlPullParser.START_TAG, ns, "stations");
		while (parser.next() != XmlPullParser.END_TAG)
		{
			if (parser.getEventType() != XmlPullParser.START_TAG)
			{
				continue;
			}
			String name = parser.getName();
			// Starts by looking for the entry tag
			if (name.equals("station"))
			{
				stations.add(readStation(parser));
			} else
			{
				skip(parser);
			}
		}
		return stations;
	}

	private void skip(XmlPullParser parser) throws XmlPullParserException,
			IOException
	{
		if (parser.getEventType() != XmlPullParser.START_TAG)
		{
			throw new IllegalStateException();
		}
		int depth = 1;
		while (depth != 0)
		{
			switch (parser.next())
			{
			case XmlPullParser.END_TAG:
				depth--;
				break;
			case XmlPullParser.START_TAG:
				depth++;
				break;
			}
		}
	}

	public static class Station
	{
		public final String id;
		public final String name;
		public final String nbBikes;
		public final String nbEmptyDocks;
		public final String lat;
		public final String lonG;

		private Station(String id, String name, String nbBikes,
				String nbEmptyDocks, String lat, String lonG)
		{
			this.id = id;
			this.name = name;
			this.nbBikes = nbBikes;
			this.nbEmptyDocks = nbEmptyDocks;
			this.lat = lat;
			this.lonG = lonG;
		}
	}

	// Parses the contents of an entry. If it encounters a title, summary, or
	// link tag, hands them off
	// to their respective "read" methods for processing. Otherwise, skips the
	// tag.
	private Station readStation(XmlPullParser parser)
			throws XmlPullParserException, IOException
	{
		parser.require(XmlPullParser.START_TAG, ns, "station");
		String id = null;
		String name = null;
		String nbBikes = null;
		String nbEmptyDocks = null;
		String lat = null;
		String lonG = null;
		while (parser.next() != XmlPullParser.END_TAG)
		{
			if (parser.getEventType() != XmlPullParser.START_TAG)
			{
				continue;
			}
			String tagName = parser.getName();
			if (tagName.equals("id"))
			{
				id = readId(parser);
			} else if (tagName.equals("name"))
			{
				name = readName(parser);
			} else if (tagName.equals("nbBikes"))
			{
				nbBikes = readNbBikes(parser);
			} else if (tagName.equals("nbEmptyDocks"))
			{
				nbEmptyDocks = readNbEmptyDocks(parser);
			} else if (tagName.equals("lat"))
			{
				lat = readLat(parser);
			} else if (tagName.equals("long"))
			{
				lonG = readLonG(parser);
			} else
			{
				skip(parser);
			}
		}
		return new Station(id, name, nbBikes, nbEmptyDocks, lat, lonG);
	}

	// Processes title tags in the feed.
	private String readId(XmlPullParser parser) throws IOException,
			XmlPullParserException
	{
		parser.require(XmlPullParser.START_TAG, ns, "id");
		String id = readText(parser);
		parser.require(XmlPullParser.END_TAG, ns, "id");
		return id;
	}

	// Processes link tags in the feed.
	private String readName(XmlPullParser parser) throws IOException,
			XmlPullParserException
	{
		parser.require(XmlPullParser.START_TAG, ns, "name");
		String name = readText(parser);
		parser.require(XmlPullParser.END_TAG, ns, "name");
		return name;
	}

	// Processes summary tags in the feed.
	private String readNbBikes(XmlPullParser parser) throws IOException,
			XmlPullParserException
	{
		parser.require(XmlPullParser.START_TAG, ns, "nbBikes");
		String nbBikes = readText(parser);
		parser.require(XmlPullParser.END_TAG, ns, "nbBikes");
		return nbBikes;
	}

	private String readNbEmptyDocks(XmlPullParser parser) throws IOException,
			XmlPullParserException
	{
		parser.require(XmlPullParser.START_TAG, ns, "nbEmptyDocks");
		String nbEmptyDocks = readText(parser);
		parser.require(XmlPullParser.END_TAG, ns, "nbEmptyDocks");
		return nbEmptyDocks;
	}

	private String readLat(XmlPullParser parser) throws IOException,
			XmlPullParserException
	{
		parser.require(XmlPullParser.START_TAG, ns, "lat");
		String lat = readText(parser);
		parser.require(XmlPullParser.END_TAG, ns, "lat");
		return lat;
	}

	private String readLonG(XmlPullParser parser) throws IOException,
			XmlPullParserException
	{
		parser.require(XmlPullParser.START_TAG, ns, "long");
		String lonG = readText(parser);
		parser.require(XmlPullParser.END_TAG, ns, "long");
		return lonG;
	}

	// For the tags title and summary, extracts their text values.
	private String readText(XmlPullParser parser) throws IOException,
			XmlPullParserException
	{
		String result = "";
		if (parser.next() == XmlPullParser.TEXT)
		{
			result = parser.getText();
			parser.nextTag();
		}
		return result;
	}

	private ArrayList<HashMap<String, String>> getKeyValuePairs(
			List<Station> list)
	{
		ArrayList<HashMap<String, String>> arrayList = new ArrayList<HashMap<String, String>>();
		for (Station station : list)
		{
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("id", station.id);
			map.put("name", station.name);
			map.put("nbBikes", station.nbBikes);
			map.put("nbEmptyDocks", station.nbEmptyDocks);
			map.put("lat", station.lat);
			map.put("long", station.lonG);
			arrayList.add(map);
		}
		return arrayList;
	}
}
