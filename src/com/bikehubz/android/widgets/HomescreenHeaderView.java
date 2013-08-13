package com.bikehubz.android.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bikehubz.android.R;

public class HomescreenHeaderView extends RelativeLayout
{
	private ImageView profilePic;
	private FontTextView name;
	private FontTextView tagline;
	private WingedMilesView miles;
	private int viewHeight;
	private int viewWidth;

	public HomescreenHeaderView(Context context)
	{
		super(context);
	}

	public HomescreenHeaderView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		TypedArray a = context.obtainStyledAttributes(attrs,
				R.styleable.HomescreenHeaderView, 0, 0);
		// TODO pick attributes to put there and get them!

		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.view_homescreen_header, this, true);

		profilePic = (ImageView) getChildAt(0);
		tagline = (FontTextView) getChildAt(1);
		name = (FontTextView) getChildAt(2);
		miles = (WingedMilesView) getChildAt(3);

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

	protected void setDynamicSizes()
	{
		int ppWidth = viewWidth / 10;
		int ppHeight = 8 * viewHeight / 10;
		LayoutParams lp = new LayoutParams(ppWidth, ppHeight);
		lp.addRule(ALIGN_PARENT_LEFT);
		lp.addRule(CENTER_VERTICAL);
		lp.leftMargin = viewWidth / 70;
		profilePic.setLayoutParams(lp);

		int taglineWidth = LayoutParams.WRAP_CONTENT;
		lp = new LayoutParams(taglineWidth, LayoutParams.WRAP_CONTENT);
		lp.leftMargin = viewWidth / 70;
		lp.bottomMargin = viewHeight / 10;
		lp.addRule(RIGHT_OF, R.id.profile_pic);
		tagline.setLayoutParams(lp);
		//tagline.setGravity(TEXT_ALIGNMENT_CENTER);

		int nameWidth = LayoutParams.WRAP_CONTENT;
		lp = new LayoutParams(nameWidth, LayoutParams.WRAP_CONTENT);
		lp.leftMargin = viewWidth / 70;
		lp.bottomMargin = 0;
		lp.addRule(RIGHT_OF, R.id.profile_pic);
		lp.addRule(ABOVE, R.id.user_tagline);
		name.setLayoutParams(lp);
		//name.setGravity(TEXT_ALIGNMENT_CENTER);

		int wingsWidth = 5;
		lp = new LayoutParams(wingsWidth, LayoutParams.WRAP_CONTENT);
		miles.setLayoutParams(lp);

	}

	public void setProfilePic(Drawable profilePic)
	{
		this.profilePic.setImageDrawable(profilePic);
	}

	public void setName(String name)
	{
		this.name.setText(name);
	}

	public void setTagline(String tagline)
	{
		this.tagline.setText("\"" + tagline + "\"");
	}

	public void setMiles(int miles)
	{
		this.miles.setMiles(miles);
	}

}
