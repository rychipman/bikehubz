package com.bikehubz.android;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.bikehubz.android.utils.ParseCalls;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.parse.ParseAnalytics;
import com.parse.ParseFacebookUtils;

public class StartActivity extends FragmentActivity
{
	private static final int LOGIN = 0;
	private static final int SELECTION = 1;
	private static final int FRAGMENT_COUNT = SELECTION + 1;
	private Fragment[] fragments = new Fragment[FRAGMENT_COUNT];

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		uiHelper = new UiLifecycleHelper(this, callback);
		uiHelper.onCreate(savedInstanceState);

		ParseAnalytics.trackAppOpened(getIntent());

		FragmentManager fm = getSupportFragmentManager();
		fragments[LOGIN] = fm.findFragmentById(R.id.loginFragment);
		fragments[SELECTION] = fm.findFragmentById(R.id.selectionFragment);
		FragmentTransaction transaction = fm.beginTransaction();
		for (int i = 0; i < fragments.length; i++)
		{
			transaction.hide(fragments[i]);
		}
		transaction.commit();

		if (ParseCalls.isSignedIn())
			fm.beginTransaction().show(fragments[SELECTION]).commit();
		else
			fm.beginTransaction().show(fragments[LOGIN]).commit();

	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);
		ParseFacebookUtils.finishAuthentication(requestCode, resultCode, data);
	}

	@Override
	public void onResume()
	{
		super.onResume();
		uiHelper.onResume();
	}

	@Override
	public void onSaveInstanceState(Bundle bundle)
	{
		super.onSaveInstanceState(bundle);
		uiHelper.onSaveInstanceState(bundle);
	}

	@Override
	public void onPause()
	{
		super.onPause();
		uiHelper.onPause();
	}

	@Override
	public void onDestroy()
	{
		super.onDestroy();
		uiHelper.onDestroy();
	}

	private UiLifecycleHelper uiHelper;
	private Session.StatusCallback callback = new Session.StatusCallback() {
		@Override
		public void call(final Session session, final SessionState state, final Exception exception)
		{
			onSessionStateChange(session, state, exception);
		}
	};

	private void onSessionStateChange(final Session session, SessionState state, Exception exception)
	{
		Session parseSession = ParseFacebookUtils.getSession();
		if (parseSession != null && parseSession.isOpened())
		{
			if (!ParseCalls.checkHasRegistered())
				ParseCalls.setNewUserFBInfo(new ParseCalls.OnInfoSetListener() {

					@Override
					public void onInfoSet()
					{
						FragmentManager fm = getSupportFragmentManager();
						fm.beginTransaction().hide(fragments[LOGIN]).show(fragments[SELECTION]).commit();
						((SelectionFragment) fragments[SELECTION]).getUserData();
					}
				});
			else
			{
				FragmentManager fm = getSupportFragmentManager();
				fm.beginTransaction().hide(fragments[LOGIN]).show(fragments[SELECTION]).commit();
			}
		}
		// TODO handle errors
	}
}