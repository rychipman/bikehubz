package com.bikehubz.android.widgets;

import com.bikehubz.android.R;
import com.bikehubz.android.R.layout;
import com.bikehubz.android.R.styleable;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

public class HomescreenNewsfeedView extends RelativeLayout
{
	public HomescreenNewsfeedView(Context context)
	{
		super(context);
	}

	public HomescreenNewsfeedView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		TypedArray a = context.obtainStyledAttributes(attrs,
				R.styleable.HomescreenNewsfeedView, 0, 0);
		// TODO pick attributes to put there and get them!

		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.view_homescreen_newsfeed, this, true);
	}
}
