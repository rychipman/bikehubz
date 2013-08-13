package com.bikehubz.android;

import android.app.Application;
import android.content.Context;

import com.parse.Parse;
import com.parse.ParseFacebookUtils;

public class HubzApplication extends Application
{
	public static String FB_APP_ID = "127115924147419";
	public static String APP_ID = "IMapFoEQ8H8xQgWOqyutHLowfIyqcC5Q5CtMjZ6p";
	public static String CLIENT_KEY = "vuQZ0r4YwlDvIjJCivzXDMIa99QH3CbzPthlGEs2";

	@Override
	public void onCreate()
	{
		super.onCreate();
		initialize(this);
	}

	public static void initialize(Context ctx)
	{
		Parse.initialize(ctx, APP_ID, CLIENT_KEY);
		ParseFacebookUtils.initialize(FB_APP_ID);
	}
}
