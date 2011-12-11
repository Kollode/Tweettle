package de.kollode.tweettle.authentication;

import de.kollode.tweettle.R;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthProvider;
import android.accounts.AccountAuthenticatorActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

public class TwitterAuthenticatorActivity extends AccountAuthenticatorActivity {
	protected static final String CONSUMER_KEY = "L8AYdK3uALSzo7dEXvP1nw ";
	protected static final String CONSUMER_SECRET = "AxtvyafCfhENHn7cIQdZtYcHUekX3HYT51TLbiVkLZ4";

	protected static final String REQUEST_URL = "https://api.twitter.com/oauth/request_token";
	protected static final String ACCESS_TOKEN_URL = "https://api.twitter.com/oauth/access_token";
	protected static final String AUTH_URL = "	https://api.twitter.com/oauth/authorize";
	protected static final String CALLBACK_SCHEME = "http";
	protected static final String CALLBACK_URL = CALLBACK_SCHEME+"://www.kollode.de/tweettle";

	protected static CommonsHttpOAuthConsumer consumer = new CommonsHttpOAuthConsumer(
			CONSUMER_KEY, CONSUMER_SECRET);
	protected static CommonsHttpOAuthProvider provider = new CommonsHttpOAuthProvider(
			REQUEST_URL, ACCESS_TOKEN_URL, AUTH_URL);

	
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);	
		setContentView(R.layout.twitter_authenticator);
		
		provider.setOAuth10a(true);		
		new OAuthRetrieveTempToken( this, provider, consumer).execute();
	}
	
	@Override
	public void onNewIntent(Intent intent) {
		super.onNewIntent(intent);

		final Uri uri = intent.getData();
		if (uri != null && uri.getScheme().equals(CALLBACK_SCHEME)) {
			new OAuthRetrieveAccessToken(this, provider, consumer).execute(uri);
			finish();	
		}
	}
}
