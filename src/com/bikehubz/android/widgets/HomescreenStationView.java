package com.bikehubz.android.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.bikehubz.android.R;

public class HomescreenStationView extends LinearLayout
{
	public HomescreenStationView(Context context)
	{
		super(context);
	}

	public HomescreenStationView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		TypedArray a = context.obtainStyledAttributes(attrs,
				R.styleable.HomescreenStationView, 0, 0);
		// TODO pick attributes to put there and get them!

		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.view_homescreen_station, this, true);
	}

	public void setStationAvailability(int numAvailable, int numTaken, int index)
	{
		LinearLayout parent = (LinearLayout) findViewById(R.id.detail_view_container);
		HomescreenStationDetailView child = (HomescreenStationDetailView) parent
				.getChildAt(index);
		child.setStationAvailability(numAvailable, numTaken);
	}
}
