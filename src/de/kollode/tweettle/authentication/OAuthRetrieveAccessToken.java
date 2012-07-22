package de.kollode.tweettle.authentication;

import oauth.signpost.OAuth;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthProvider;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;
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

	public OAuthRetrieveAccessToken(AccountAuthenticatorActivity context) {
		this.context = context;
	}

	@Override
	protected Void doInBackground(Uri... params) {
		Uri uri = params[0];

		String oauth_verifier = uri.getQueryParameter(OAuth.OAUTH_VERIFIER);

		try {
			TwitterAuthenticatorActivity.getProvider().retrieveAccessToken(TwitterAuthenticatorActivity.getConsumer(), oauth_verifier);

			Log.i("access_token", TwitterAuthenticatorActivity.getConsumer().getToken());
			Log.i("access_token_secret", TwitterAuthenticatorActivity.getConsumer().getTokenSecret());

			String accountType = this.context.getIntent().getStringExtra("de.kollode.tweettle");
			if (accountType == null) {
				accountType = "de.kollode.tweettle";
			}
			
			Configuration settings = new ConfigurationBuilder()
				.setOAuthConsumerKey( TwitterAuthenticatorActivity.CONSUMER_KEY )
				.setOAuthConsumerSecret( TwitterAuthenticatorActivity.CONSUMER_SECRET )
				.setOAuthAccessToken(TwitterAuthenticatorActivity.getConsumer().getToken())
				.setOAuthAccessTokenSecret(TwitterAuthenticatorActivity.getConsumer().getTokenSecret())
				.build();
			
			Twitter mTwitter = new TwitterFactory(settings).getInstance();
			String username = mTwitter.getScreenName();
			
			AccountManager accMgr = AccountManager.get(this.context);

			// This is the magic that adds the account to the Android
			// Account Manager
			final Account account = new Account(username, TwitterAuthenticatorActivity.ACCOUNT_TYPE);
			accMgr.addAccountExplicitly(account, null, null);
			accMgr.setUserData(account, "token", TwitterAuthenticatorActivity.getConsumer().getToken());
			accMgr.setUserData(account, "tokenSecret", TwitterAuthenticatorActivity.getConsumer().getTokenSecret());
			
			// Now we tell our caller, could be the Android Account Manager
			// or even our own application
			// that the process was successful

			final Intent intent = new Intent();
			intent.putExtra(AccountManager.KEY_ACCOUNT_NAME, username);
			intent.putExtra(AccountManager.KEY_ACCOUNT_TYPE, TwitterAuthenticatorActivity.ACCOUNT_TYPE);
			intent.putExtra(AccountManager.KEY_AUTHTOKEN, TwitterAuthenticatorActivity.ACCOUNT_TYPE);
			this.context.setAccountAuthenticatorResult(intent.getExtras());
			this.context.setResult(Activity.RESULT_OK, intent);

		} catch (Exception e) {
			Log.e("Tweettle AccountCReation", "Error while creating the account", e);
		}

		return null;
	}
	
	@Override
	protected void onPostExecute(Void result) {
		super.onPostExecute(result);
		this.context.finish();
	}
}
