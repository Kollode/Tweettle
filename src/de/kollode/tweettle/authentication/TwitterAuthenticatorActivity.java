package de.kollode.tweettle.authentication;

import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthProvider;
import android.accounts.AccountAuthenticatorActivity;
import android.net.Uri;
import android.os.Bundle;
import de.kollode.tweettle.R;

public class TwitterAuthenticatorActivity extends AccountAuthenticatorActivity {
	protected static final String CONSUMER_KEY = "L8AYdK3uALSzo7dEXvP1nw";
	protected static final String CONSUMER_SECRET = "AxtvyafCfhENHn7cIQdZtYcHUekX3HYT51TLbiVkLZ4";

	protected static final String REQUEST_URL = "https://api.twitter.com/oauth/request_token";
	protected static final String ACCESS_TOKEN_URL = "https://api.twitter.com/oauth/access_token";
	protected static final String AUTH_URL = "	https://api.twitter.com/oauth/authorize";
	protected static final String CALLBACK_SCHEME = "tweettle";
	protected static final String CALLBACK_URL = CALLBACK_SCHEME + "://oauth";

	protected static final String ACCOUNT_TYPE = "de.kollode.tweettle";

	protected static CommonsHttpOAuthConsumer consumer;
	protected static CommonsHttpOAuthProvider provider;

	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.twitter_authenticator);

		final Uri uri = getIntent().getData();
		if (uri != null && uri.getScheme().equals(CALLBACK_SCHEME)) {
			new OAuthRetrieveAccessToken(this).execute(uri);
		} else {
			new OAuthRetrieveTempToken(this).execute();
		}
	}
	
	protected static CommonsHttpOAuthConsumer getConsumer() {
		if(consumer == null) {
			consumer = new CommonsHttpOAuthConsumer(CONSUMER_KEY, CONSUMER_SECRET);
		}
		
		return consumer;
	}
	
	protected static CommonsHttpOAuthProvider getProvider() {
		if(provider == null) {
			provider = new CommonsHttpOAuthProvider(REQUEST_URL, ACCESS_TOKEN_URL, AUTH_URL);
			provider.setOAuth10a(true);
		}
		
		return provider;
	}	
}
