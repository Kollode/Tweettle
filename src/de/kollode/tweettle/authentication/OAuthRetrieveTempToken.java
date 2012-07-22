package de.kollode.tweettle.authentication;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;
import de.kollode.tweettle.R;


public class OAuthRetrieveTempToken extends AsyncTask<Void, Void, Void>  {
	
	
	private TwitterAuthenticatorActivity context;

	public OAuthRetrieveTempToken(TwitterAuthenticatorActivity context) {
		this.context = context;
	}
	
	@Override
	protected Void doInBackground(Void... params) {		
		try {
			String result = TwitterAuthenticatorActivity.getProvider().retrieveRequestToken(TwitterAuthenticatorActivity.getConsumer(), TwitterAuthenticatorActivity.CALLBACK_URL);			
			
			Log.i("OAuth", "Create intent for the URL: "+result);
			
			
			WebView webview = (WebView)context.findViewById(R.id.webview);
			//webview.getSettings().setSavePassword(false);
			webview.setWebViewClient(new WebViewClient() {
	            public void onPageFinished(WebView view, String url) {
	                Log.i("OAuth", "Finished loading URL: " +url);
	                if (context.progressBar != null && context.progressBar.isShowing()) {
	                	context.progressBar.dismiss();
	                }
	            }
	            
				@Override
				public boolean shouldOverrideUrlLoading(WebView view, String url) {
					if (url.contains(TwitterAuthenticatorActivity.CALLBACK_URL)) {
						Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
						context.startActivity(intent);
						return true;
					}
					return false;
				}
	        });
			
			webview.loadUrl(result.trim());
		} catch (Exception e) {
			Log.e("OAuth", "Can't request the RequestToken", e);
			Toast.makeText(this.context, "Can't request the RequestToken", Toast.LENGTH_SHORT).show();
		}

		return null;
	}
}
