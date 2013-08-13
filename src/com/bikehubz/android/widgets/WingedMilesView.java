package com.bikehubz.android.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bikehubz.android.R;

public class WingedMilesView extends RelativeLayout
{
	private ImageView leftWing;
	private FontTextView number;
	private ImageView rightWing;
	private FontTextView miles;

	public WingedMilesView(Context context)
	{
		super(context);
	}

	public WingedMilesView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		TypedArray a = context.obtainStyledAttributes(attrs,
				R.styleable.WingedMilesView, 0, 0);
		// TODO pick attributes to put there and get them!

		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.view_winged_miles, this, true);

		leftWing = (ImageView) getChildAt(0);
		number = (FontTextView) ((LinearLayout) getChildAt(1)).getChildAt(0);
		miles = (FontTextView) ((LinearLayout) getChildAt(1)).getChildAt(1);
		rightWing = (ImageView) getChildAt(2);
	}

	public void setMiles(int num)
	{
		number.setText(new Integer(num).toString());
	}
}
