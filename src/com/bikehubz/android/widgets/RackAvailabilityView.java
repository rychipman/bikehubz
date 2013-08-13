package com.bikehubz.android.widgets;

import java.util.ArrayList;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bikehubz.android.R;

public class RackAvailabilityView extends RelativeLayout
{
	private Drawable[] availabilityIcons = new Drawable[48];
	private Context ctx;

	private int viewWidth;
	private int viewHeight;

	public RackAvailabilityView(Context context)
	{
		super(context);
		ctx = context;
	}

	public RackAvailabilityView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		ctx = context;
		TypedArray a = context.obtainStyledAttributes(attrs,
				R.styleable.RackAvailabilityView, 0, 0);

		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.view_rack_availability, this, true);

		ViewTreeObserver viewTreeObserver = getViewTreeObserver();
		if (viewTreeObserver.isAlive())
		{
			viewTreeObserver
					.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
						@Override
						public void onGlobalLayout()
						{
							getViewTreeObserver().removeOnGlobalLayoutListener(
									this);
							viewWidth = getWidth();
							viewHeight = getHeight();

							// setDynamicSizes();
						}
					});
		}
	}

	public void setAvailability(int numFree, int numTaken)
	{
		int[] availability = new int[48];
		for (int i = 0; i < availability.length; i++)
		{
			if (i < numFree)
				availability[i] = 1;
			else if (i < numTaken + numFree)
				availability[i] = 2;
			else
				availability[i] = 0;
		}
		for (int i = 0; i < availability.length; i++)
		{
			if (availability[i] == 1)
				availabilityIcons[i] = ctx.getResources().getDrawable(
						R.drawable.yes_bike);
			else if (availability[i] == 2)
				availabilityIcons[i] = ctx.getResources().getDrawable(
						R.drawable.no_bike);
			else
				availabilityIcons[i] = null;
		}
		updateAvailabilities();
	}

	private void updateAvailabilities()
	{
		LinearLayout upperLL = (LinearLayout) findViewById(R.id.upper);
		LinearLayout lowerLL = (LinearLayout) findViewById(R.id.lower);
		ArrayList<ImageView> views = new ArrayList<ImageView>();

		upperLL.removeAllViews();
		lowerLL.removeAllViews();

		for (Drawable drawable : availabilityIcons)
		{
			ImageView view = new ImageView(ctx);
			int height = viewHeight * 9 / 20;
			int width = height * 56 / 296;
			int marginBetween = viewHeight / 35;
			LayoutParams lp = new LayoutParams(width, height);
			lp.bottomMargin = marginBetween;
			view.setLayoutParams(lp);
			if (drawable != null)
			{
				view.setImageDrawable(drawable);
				views.add(view);
			}
		}
		if (views.size() <= 24)
		{
			LayoutParams params = (LayoutParams) upperLL.getLayoutParams();
			params.height = (viewHeight - lowerLL.getHeight()) / 2;
			upperLL.setLayoutParams(params);

			((LayoutParams) lowerLL.getLayoutParams()).addRule(CENTER_VERTICAL);
			for (ImageView view : views.subList(0, views.size()))
				lowerLL.addView(view);
		} else
		{
			upperLL.getLayoutParams().height = lowerLL.getHeight();
			for (ImageView view : views.subList(0, 24))
				lowerLL.addView(view);
			for (ImageView view : views.subList(24, views.size()))
				upperLL.addView(view);
		}
	}
}
