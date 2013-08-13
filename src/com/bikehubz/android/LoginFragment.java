package com.bikehubz.android;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.bikehubz.android.utils.ParseCalls;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseUser;

public class LoginFragment extends Fragment
{
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.fragment_login, container, false);
		Button login = (Button) view.findViewById(R.id.login_button);
		login.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v)
			{
				parseFacebookLogin();
			}
		});
		return view;
	}

	private void parseFacebookLogin()
	{
		ParseFacebookUtils.logIn(getActivity(), new LogInCallback() {
			@Override
			public void done(ParseUser user, ParseException err)
			{
				if (user == null)
				{
					Log.d("BikeHubz", "Uh oh. The user cancelled the Facebook login.");
					Toast.makeText(getActivity(), "Facebook Login Failed", Toast.LENGTH_SHORT).show();

				} else if (user.isNew())
				{
					user.put(ParseCalls.KEY_USES_FACEBOOK, true);
					user.saveInBackground();
					Log.d("BikeHubz", "User signed up and logged in through Facebook!");
				} else
				{
					Log.d("BikeHubz", "User logged in through Facebook!");
				}
			}
		});

		// TODO only prompt for friend inviting sometimes

	}
}
