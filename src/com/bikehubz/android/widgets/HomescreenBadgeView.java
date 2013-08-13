package com.bikehubz.android.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bikehubz.android.R;

public class HomescreenBadgeView extends RelativeLayout implements
		OnPageChangeListener
{

	private ViewPager mCardsViewPager;
	private float MIN_SCALE = 1f - 1f / 7f;
	private float MAX_SCALE = 1f;

	public HomescreenBadgeView(Context context)
	{
		super(context);
	}

	public HomescreenBadgeView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		TypedArray a = context.obtainStyledAttributes(attrs,
				R.styleable.HomescreenBadgeView, 0, 0);
		// TODO pick attributes to put there and get them!

		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.view_homescreen_badges, this, true);

		mCardsViewPager = (ViewPager) findViewById(R.id.badge_pager);
		mCardsViewPager.setAdapter(new CardsPagerAdapter());
		mCardsViewPager.setPageMargin(-156);
		mCardsViewPager.setOffscreenPageLimit(3);
		mCardsViewPager.setOnPageChangeListener(this);
	}

	private class CardsPagerAdapter extends PagerAdapter
	{

		private boolean mIsDefaultItemSelected = false;

		private int[] mCards = { R.drawable.selected_badge,
				R.drawable.selected_badge, R.drawable.selected_badge,
				R.drawable.selected_badge, R.drawable.selected_badge,
				R.drawable.selected_badge };

		@Override
		public Object instantiateItem(ViewGroup container, int position)
		{
			ImageView cardImageView = (ImageView) View.inflate(
					container.getContext(), R.layout.imageview_card, null);
			cardImageView.setImageDrawable(getResources().getDrawable(
					mCards[position]));
			cardImageView.setTag(position);

			if (!mIsDefaultItemSelected)
			{
				cardImageView.setScaleX(MAX_SCALE);
				cardImageView.setScaleY(MAX_SCALE);
				mIsDefaultItemSelected = true;
			} else
			{
				cardImageView.setScaleX(MIN_SCALE);
				cardImageView.setScaleY(MIN_SCALE);
			}

			container.addView(cardImageView);
			return cardImageView;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object)
		{
			container.removeView((View) object);
		}

		@Override
		public int getCount()
		{
			return mCards.length;
		}

		@Override
		public boolean isViewFromObject(View view, Object object)
		{
			return view == object;
		}
	}

	@Override
	public void onPageScrollStateChanged(int state)
	{

	}

	@Override
	public void onPageScrolled(int position, float positionOffset,
			int positionOffsetPixels)
	{
		for (int i = 0; i < mCardsViewPager.getChildCount(); i++)
		{
			View cardView = mCardsViewPager.getChildAt(i);
			int itemPosition = (Integer) cardView.getTag();

			if (itemPosition == position)
			{
				cardView.setScaleX(MAX_SCALE - positionOffset / 7f);
				cardView.setScaleY(MAX_SCALE - positionOffset / 7f);
			}

			if (itemPosition == (position + 1))
			{
				cardView.setScaleX(MIN_SCALE + positionOffset / 7f);
				cardView.setScaleY(MIN_SCALE + positionOffset / 7f);
			}
		}
	}

	@Override
	public void onPageSelected(int position)
	{

	}
}
