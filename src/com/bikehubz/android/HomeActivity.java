package com.bikehubz.android;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;

import com.bikehubz.android.utils.Network;
import com.bikehubz.android.utils.ParseCalls;
import com.bikehubz.android.widgets.HomescreenBadgeView;
import com.bikehubz.android.widgets.HomescreenHeaderView;
import com.bikehubz.android.widgets.HomescreenNewsfeedView;
import com.bikehubz.android.widgets.HomescreenStationView;

public class HomeActivity extends Activity
{
	private HomescreenHeaderView header;
	private HomescreenBadgeView badges;
	private HomescreenStationView stations;
	private HomescreenNewsfeedView news;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_home, menu);
		return true;
	}

	@Override
	protected void onStart()
	{
		super.onStart();
		header = (HomescreenHeaderView) findViewById(R.id.home_header);
		badges = (HomescreenBadgeView) findViewById(R.id.home_badges);
		stations = (HomescreenStationView) findViewById(R.id.home_stations);
		news = (HomescreenNewsfeedView) findViewById(R.id.home_newsfeed);

		new StationInfoTask().execute("");
		header.setName(ParseCalls.getName());
		header.setProfilePic(ParseCalls.getProfilePicture());
		// header.setMiles(ParseCalls.getMiles());
		// header.setTagline(ParseCalls.getTagline());
	}

	private class StationInfoTask extends AsyncTask<String, String, ArrayList<HashMap<String, String>>>
	{

		@Override
		protected ArrayList<HashMap<String, String>> doInBackground(String... uid)
		{
			ArrayList<HashMap<String, String>> list = Network.getListFromURL("http://thehubway.com/data/stations/bikeStations.xml");
			return list;
		}

		@Override
		protected void onPostExecute(ArrayList<HashMap<String, String>> stations)
		{
			HomescreenStationView view = (HomescreenStationView) findViewById(R.id.home_stations);
			for (HashMap<String, String> map : stations)
			{
				if (map.get("id").equals("67"))
					view.setStationAvailability(Integer.parseInt(map.get("nbBikes")), Integer.parseInt(map.get("nbEmptyDocks")), 0);
				if (map.get("id").equals("80"))
					view.setStationAvailability(Integer.parseInt(map.get("nbBikes")), Integer.parseInt(map.get("nbEmptyDocks")), 1);
				if (map.get("id").equals("107"))
					view.setStationAvailability(Integer.parseInt(map.get("nbBikes")), Integer.parseInt(map.get("nbEmptyDocks")), 2);
				if (map.get("id").equals("53"))
					view.setStationAvailability(Integer.parseInt(map.get("nbBikes")), Integer.parseInt(map.get("nbEmptyDocks")), 3);
			}
		}
	}
}