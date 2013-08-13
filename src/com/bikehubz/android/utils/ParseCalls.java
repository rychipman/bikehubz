package com.bikehubz.android.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.model.GraphUser;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class ParseCalls
{
	public static String KEY_HAS_REGISTERED = "has_registered";
	public static String KEY_NAME = "name";
	public static String KEY_PROFILE_PICTURE = "profile_picture";
	public static String KEY_FACEBOOK_ID = "facebook_id";
	public static String KEY_USES_FACEBOOK = "is_facebook_user";

	public static String FILE_PROFILE_PICTURE = "profile_picture.png";

	// Methods to check the status of various parse states
	public static boolean isSignedIn()
	{
		ParseUser currentUser = ParseUser.getCurrentUser();
		if (currentUser != null)
			return true;
		return false;
	}

	public static boolean checkHasRegistered()
	{
		ParseUser currentUser = ParseUser.getCurrentUser();
		boolean registered = currentUser.getBoolean(KEY_HAS_REGISTERED);
		return registered;
	}

	// Getters
	public static String getName()
	{
		ParseUser user = ParseUser.getCurrentUser();
		return user.getString(KEY_NAME);
	}

	public static Drawable getProfilePicture()
	{
		ParseUser user = ParseUser.getCurrentUser();
		byte[] bytes = null;
		if (user != null)
		{
			ParseFile file = (ParseFile) user.get(KEY_PROFILE_PICTURE);
			try
			{
				bytes = file.getData();
			} catch (ParseException e)
			{
				e.printStackTrace();
			}
		}
		Drawable image = null;
		image = new BitmapDrawable(BitmapFactory.decodeByteArray(bytes, 0, bytes.length));
		return image;
	}

	// Setters
	public static void setNewUserFBInfo(final OnInfoSetListener listener)
	{
		Session session = ParseFacebookUtils.getSession();
		boolean open = session.isOpened();
		if (open)
		{
			Request.executeMeRequestAsync(session, new Request.GraphUserCallback() {
				@Override
				public void onCompleted(GraphUser user, Response response)
				{
					if (user != null)
					{
						final String name = user.getName();
						final String id = user.getId();
						boolean facebook_user = true;

						ParseUser pUser = ParseUser.getCurrentUser();
						pUser.put(KEY_NAME, name);
						pUser.put(KEY_FACEBOOK_ID, id);
						pUser.put(KEY_USES_FACEBOOK, facebook_user);
						pUser.put(KEY_HAS_REGISTERED, true);
						pUser.saveInBackground(new SaveCallback() {

							@Override
							public void done(ParseException e)
							{
								new SetProfilePictureTask().setOnCompleteListener(listener).execute(id);

							}
						});
					}
				}
			});
		}
	}

	// Helper methods/Classes
	private static byte[] extractByteArray(InputStream inputStream) throws IOException
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int read = 0;
		while ((read = inputStream.read(buffer, 0, buffer.length)) != -1)
		{
			baos.write(buffer, 0, read);
		}
		baos.flush();
		return baos.toByteArray();
	}

	private static class SetProfilePictureTask extends AsyncTask<String, String, byte[]>
	{
		private OnInfoSetListener mListener;

		@Override
		protected byte[] doInBackground(String... args)
		{
			String userId = args[0];
			String imageURL;
			Log.d("FB Calls", "Loading Picture");
			imageURL = "http://graph.facebook.com/" + userId + "/picture?type=large";
			byte[] bytes = null;
			try
			{
				InputStream is = (InputStream) new URL(imageURL).getContent();
				bytes = extractByteArray(is);

			} catch (MalformedURLException e)
			{
				e.printStackTrace();
			} catch (IOException e)
			{
				e.printStackTrace();
			}
			return bytes;
		}

		@Override
		protected void onPostExecute(byte[] bytes)
		{
			super.onPostExecute(bytes);
			ParseFile proFile = new ParseFile(FILE_PROFILE_PICTURE, bytes);
			proFile.saveInBackground();
			ParseUser pUser = ParseUser.getCurrentUser();
			pUser.put(KEY_PROFILE_PICTURE, proFile);
			pUser.saveInBackground(new SaveCallback() {
				@Override
				public void done(ParseException e)
				{
					mListener.onInfoSet();
				}
			});
		}

		protected SetProfilePictureTask setOnCompleteListener(OnInfoSetListener l)
		{
			mListener = l;
			return this;
		}

	}

	public interface OnInfoSetListener
	{
		void onInfoSet();
	}
}
