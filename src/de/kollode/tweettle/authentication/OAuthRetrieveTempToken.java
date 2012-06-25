package de.kollode.tweettle.authentication;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;


public class OAuthRetrieveTempToken extends AsyncTask<Void, Void, Void>  {
	
	private TwitterAuthenticatorActivity	context;

	public OAuthRetrieveTempToken(TwitterAuthenticatorActivity context) {
		this.context = context;
	}
	
	@Override
	protected Void doInBackground(Void... params) {		
		try {
			String result = TwitterAuthenticatorActivity.getProvider().retrieveRequestToken(TwitterAuthenticatorActivity.getConsumer(), TwitterAuthenticatorActivity.CALLBACK_URL);			
			
			Log.i("OAuth", "Create intent for the URL: "+result);
			Intent intent = new Intent(Intent.ACTION_VIEW);
			intent.setData(Uri.parse(result.trim()));
			context.startActivity(intent);
			/*WebView webview = (WebView)context.findViewById(R.id.webview);
			webview.loadUrl(result.trim());*/
		} catch (Exception e) {
			Log.e("OAuth", "Can't request the RequestToken", e);
		}

		return null;
	}
}
