package de.kollode.tweettle.authentication;

import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.Window;


public class OAuthRetrieveTempToken extends AsyncTask<Void, Void, Void>  {
	
	private Context	context;
	private CommonsHttpOAuthProvider provider;
	private CommonsHttpOAuthConsumer consumer;

	public OAuthRetrieveTempToken(Context context, CommonsHttpOAuthProvider provider, CommonsHttpOAuthConsumer consumer) {
		this.context = context;
		this.provider = provider;
		this.consumer = consumer;
	}
	
	@Override
	protected Void doInBackground(Void... params) {		
		try {
			String result = provider.retrieveRequestToken(consumer, TwitterAuthenticatorActivity.CALLBACK_URL);			
			
			Log.i("OAuth", "Create intent for the URL: "+result);
			Intent intent = new Intent(Intent.ACTION_VIEW);
			intent.setData(Uri.parse(result.trim()));
			intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
			intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
			context.startActivity(intent);
		} catch (Exception e) {
			Log.e("OAuth", "Can't request the RequestToken", e);
		}

		return null;
	}
}
