package com.bikehubz.android.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bikehubz.android.R;

public class HomescreenStationDetailView extends RelativeLayout
{
	private ImageView stationLogo;
	private FontTextView stationInfo;
	private RackAvailabilityView rav;

	private int numBikes;
	private int numFree;
	private int numFriends;

	public HomescreenStationDetailView(Context context)
	{
		super(context);
	}

	public HomescreenStationDetailView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		TypedArray a = context.obtainStyledAttributes(attrs,
				R.styleable.HomescreenStationDetailView, 0, 0);
		// TODO pick attributes to put there and get them!

		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.view_homescreen_station_detail, this, true);

		stationLogo = (ImageView) getChildAt(0);
		stationInfo = (FontTextView) getChildAt(1);
		rav = (RackAvailabilityView) getChildAt(2);
	}

	public void setStationAvailability(int available, int taken)
	{
		numBikes = available;
		numFree = taken;
		rav.setAvailability(available, taken);
		updateText();
	}

	public void setFriends(int numFriends)
	{
		this.numFriends = numFriends;
		updateText();
	}

	public void updateText()
	{
		String text = numBikes + " Bikes\n" + numFree + " Docks\n" + numFriends
				+ " Friends";
		stationInfo.setText(text);
	}
}
