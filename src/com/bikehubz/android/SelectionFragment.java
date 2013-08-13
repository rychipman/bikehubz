package com.bikehubz.android;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bikehubz.android.utils.ParseCalls;
import com.bikehubz.android.widgets.FontTextView;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;

public class SelectionFragment extends Fragment
{
	private ImageView profilePictureView;
	private FontTextView userNameView;
	private static final int REAUTH_ACTIVITY_CODE = 100;
	private static final int PICKER_REQUEST_CODE = 987;
	public static final String PREFS_NAME = "bikehubz_prefs";

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		uiHelper = new UiLifecycleHelper(getActivity(), callback);
		uiHelper.onCreate(savedInstanceState);

		SharedPreferences settings = getActivity().getSharedPreferences(PREFS_NAME, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.commit();

		HubzApplication.initialize(getActivity());
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		super.onCreateView(inflater, container, savedInstanceState);
		View view = inflater.inflate(R.layout.fragment_selection, container, false);

		// Find the user's profile picture custom view
		profilePictureView = (ImageView) view.findViewById(R.id.selection_profile_pic);

		// Find the user's name view
		userNameView = (FontTextView) view.findViewById(R.id.selection_user_name);

		TextView a = (TextView) view.findViewById(R.id.skip_invite_friends_button);
		a.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v)
			{
				Intent intent = new Intent();
				intent.setClass(getActivity(), HomeActivity.class);
				startActivity(intent);
			}
		});

		TextView b = (TextView) view.findViewById(R.id.invite_friends_button);
		b.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v)
			{
				startPickerActivity(FriendPickerActivity.FRIEND_PICKER, PICKER_REQUEST_CODE);
			}
		});

		return view;
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REAUTH_ACTIVITY_CODE)
		{
			uiHelper.onActivityResult(requestCode, resultCode, data);
		} else if (resultCode == Activity.RESULT_OK)
		{
			if (requestCode == PICKER_REQUEST_CODE)
			{
				// TODO do all the stuff to send invites to friends
				Intent intent = new Intent();
				intent.setClass(getActivity(), HomeActivity.class);
				startActivity(intent);
			}
		}
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

	private void startPickerActivity(Uri data, int requestCode)
	{
		Intent intent = new Intent();
		intent.setData(data);
		intent.setClass(getActivity(), FriendPickerActivity.class);
		startActivityForResult(intent, requestCode);
	}

	public void getUserData()
	{
		Drawable drawable = ParseCalls.getProfilePicture();
		String words = ParseCalls.getName();
		if (drawable == null)
		{
			int i = 5;
		}
		profilePictureView.setImageDrawable(drawable);
		userNameView.setText(words);
	}

	private void onSessionStateChange(final Session session, SessionState state, Exception exception)
	{
		if (session != null && session.isOpened())
		{
			// TODO something
		}
	}

	private UiLifecycleHelper uiHelper;
	private Session.StatusCallback callback = new Session.StatusCallback() {
		@Override
		public void call(final Session session, final SessionState state, final Exception exception)
		{
			onSessionStateChange(session, state, exception);
		}
	};
}
