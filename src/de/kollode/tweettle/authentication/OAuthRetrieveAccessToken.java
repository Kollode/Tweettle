package de.kollode.tweettle.authentication;

import oauth.signpost.OAuth;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthProvider;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

public class OAuthRetrieveAccessToken  extends AsyncTask<Uri, Void, Void>  {
	
	private Context	context;
	private CommonsHttpOAuthProvider provider;
	private CommonsHttpOAuthConsumer consumer;
	
	public OAuthRetrieveAccessToken(Context context, CommonsHttpOAuthProvider provider, CommonsHttpOAuthConsumer consumer) {
		this.context = context;
		this.provider = provider;
		this.consumer = consumer;
	}
	
	@Override
	protected Void doInBackground(Uri...params) {
		Uri uri = params[0];
			
		String oauth_verifier = uri.getQueryParameter(OAuth.OAUTH_VERIFIER);

		try {
			provider.retrieveAccessToken(consumer, oauth_verifier);

			Log.i("access_token", consumer.getToken());
			Log.i("access_token_secret", consumer.getTokenSecret());
			
		} catch (Exception e) {
			Log.e("xRelOAuth", "Error while retrieving access token", e);
		}

		return null;
	}
}
