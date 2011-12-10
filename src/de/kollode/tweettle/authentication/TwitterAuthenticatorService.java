package de.kollode.tweettle.authentication;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class TwitterAuthenticatorService extends Service {

	private static final String TAG = "TwitterAuthenticatorService" ;
	private static TwitterAuthenticator sTwitterAuthenticator = null ;
	 
	@Override
	public IBinder onBind(Intent intent) {
		IBinder ret = null;
		if (intent.getAction().equals(android.accounts.AccountManager.ACTION_AUTHENTICATOR_INTENT)) {
			ret = getAuthenticator().getIBinder();
		}
		return ret;
	}

	/**
	 * Get the TwitterAuthenticator implementation.
	 * Will create the object if not available.
	 * @return TwitterAutheticator
	 * 
	 */
	private TwitterAuthenticator getAuthenticator() {
		if (sTwitterAuthenticator == null)
			sTwitterAuthenticator = new TwitterAuthenticator(this);
		return sTwitterAuthenticator;
	}

}
