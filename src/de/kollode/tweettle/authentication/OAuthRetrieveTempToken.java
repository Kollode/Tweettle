package de.kollode.tweettle.authentication;

import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthProvider;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.webkit.WebView;
import de.kollode.tweettle.R;


public class OAuthRetrieveTempToken extends AsyncTask<Void, Void, Void>  {
	
	private TwitterAuthenticatorActivity	context;
	private CommonsHttpOAuthProvider provider;
	private CommonsHttpOAuthConsumer consumer;

	public OAuthRetrieveTempToken(TwitterAuthenticatorActivity context, CommonsHttpOAuthProvider provider, CommonsHttpOAuthConsumer consumer) {
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
			context.startActivity(intent);
			/*WebView webview = (WebView)context.findViewById(R.id.webview);
			webview.loadUrl(result.trim());*/
		} catch (Exception e) {
			Log.e("OAuth", "Can't request the RequestToken", e);
		}

		return null;
	}
}
