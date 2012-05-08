package de.kollode.tweettle.authentication;

import oauth.signpost.OAuth;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthProvider;
import android.accounts.Account;
import android.accounts.AccountAuthenticatorActivity;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

public class OAuthRetrieveAccessToken extends AsyncTask<Uri, Void, Void> {

	private static final String TAG = "Twitter";
	private AccountAuthenticatorActivity context;
	private CommonsHttpOAuthProvider provider;
	private CommonsHttpOAuthConsumer consumer;

	public OAuthRetrieveAccessToken(AccountAuthenticatorActivity context, CommonsHttpOAuthProvider provider, CommonsHttpOAuthConsumer consumer) {
		this.context = context;
		this.provider = provider;
		this.consumer = consumer;
	}

	@Override
	protected Void doInBackground(Uri... params) {
		Uri uri = params[0];

		String oauth_verifier = uri.getQueryParameter(OAuth.OAUTH_VERIFIER);

		try {
			provider.retrieveAccessToken(consumer, oauth_verifier);

			Log.i("access_token", consumer.getToken());
			Log.i("access_token_secret", consumer.getTokenSecret());

			String accountType = this.context.getIntent().getStringExtra("de.kollode.tweettle");
			if (accountType == null) {
				accountType = "de.kollode.tweettle";
			}

			AccountManager accMgr = AccountManager.get(this.context);

			// This is the magic that adds the account to the Android
			// Account Manager
			final Account account = new Account("kollode", TwitterAuthenticatorActivity.ACCOUNT_TYPE);
			accMgr.addAccountExplicitly(account, null, null);
			accMgr.setAuthToken(account, "token", consumer.getToken());
			accMgr.setAuthToken(account, "tokenSecret", consumer.getTokenSecret());

			// Now we tell our caller, could be the Android Account Manager
			// or even our own application
			// that the process was successful

			final Intent intent = new Intent();
			intent.putExtra(AccountManager.KEY_ACCOUNT_NAME, "kollode");
			intent.putExtra(AccountManager.KEY_ACCOUNT_TYPE, TwitterAuthenticatorActivity.ACCOUNT_TYPE);
			intent.putExtra(AccountManager.KEY_AUTHTOKEN, TwitterAuthenticatorActivity.ACCOUNT_TYPE);
			this.context.setAccountAuthenticatorResult(intent.getExtras());
			this.context.setResult(Activity.RESULT_OK, intent);
			this.context.finish();
			

			/*
			 * consumer.setTokenWithSecret(consumer.getToken(),
			 * consumer.getTokenSecret()); // create an HTTP request to a
			 * protected resource String url =
			 * "https://api.twitter.com/account/verify_credentials.json";
			 * 
			 * DefaultHttpClient httpclient = new DefaultHttpClient(); HttpGet
			 * request = new HttpGet(url); Log.i(TAG, "Requesting URL : " +
			 * url); consumer.sign(request); HttpResponse response =
			 * httpclient.execute(request); Log.i(TAG, "Statusline : " +
			 * response.getStatusLine()); InputStream data =
			 * response.getEntity().getContent(); BufferedReader bufferedReader
			 * = new BufferedReader( new InputStreamReader(data)); String
			 * responeLine; StringBuilder responseBuilder = new StringBuilder();
			 * while ((responeLine = bufferedReader.readLine()) != null) {
			 * responseBuilder.append(responeLine); }
			 * 
			 * Log.i(TAG, "Request");
			 * 
			 * for(Header header : request.getAllHeaders()) { Log.i(TAG,
			 * header.getName()+" : " + header.getValue()); }
			 * 
			 * Log.i(TAG, "Response");
			 * 
			 * for(Header header : response.getAllHeaders()) { Log.i(TAG,
			 * header.getName()+" : " + header.getValue()); }
			 * 
			 * Log.i(TAG, "Response : " + responseBuilder.toString());
			 */

		} catch (Exception e) {
			Log.e("xRelOAuth", "Error while retrieving access token", e);
		}

		return null;
	}
}
