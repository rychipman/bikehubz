package com.bikehubz.android.widgets;

import com.bikehubz.android.R;
import com.bikehubz.android.R.styleable;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

public class FontTextView extends TextView
{

	/**
	 * Note that when generating the class from code, you will need to call
	 * setCustomFont() manually.
	 */
	public FontTextView(Context context)
	{
		super(context);
	}

	public FontTextView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		setCustomFont(getContext(), attrs);
	}

	public FontTextView(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
		setCustomFont(getContext(), attrs);
	}

	private void setCustomFont(Context context, AttributeSet attrs)
	{
		if (isInEditMode())
		{
			// Ignore if within Eclipse
			return;
		}
		String font = "myDefaultFont.ttf";
		if (attrs != null)
		{
			// Look up any layout-defined attributes
			TypedArray a = getContext().obtainStyledAttributes(attrs,
					R.styleable.FontTextView);
			for (int i = 0; i < a.getIndexCount(); i++)
			{
				int attr = a.getIndex(i);
				switch (attr)
				{
				case R.styleable.FontTextView_customFont:
					font = a.getString(attr);
					break;
				}
			}
			a.recycle();
		}
		Typeface tf = null;
		try
		{
			tf = Typeface.createFromAsset(getContext().getAssets(), font);
		} catch (Exception e)
		{
			Log.e("Load Typeface", "Could not get typeface: " + e.getMessage());
		}
		setTypeface(tf);
	}

}
