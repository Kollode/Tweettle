package de.kollode.tweettle.authentication;

import android.accounts.Account;
import android.accounts.AccountAuthenticatorActivity;
import android.accounts.AccountManager;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import de.kollode.tweettle.R;

public class TwitterAuthenticatorActivity extends AccountAuthenticatorActivity implements OnClickListener  {
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		Log.i("de.kollode.tweettle", "load layout");
		this.setContentView(R.layout.twitter_authenticator); 
	}
	
	public void onCancelClick(View v) {
		this.finish();
	}

	public void onSaveClick(View v) {
		TextView tvUsername;
		TextView tvPassword;
		String username;
		String password;
		boolean hasErrors = false;

		tvUsername = (TextView) this.findViewById(R.id.username);
		tvPassword = (TextView) this.findViewById(R.id.password);

		tvUsername.setBackgroundColor(Color.WHITE);
		tvPassword.setBackgroundColor(Color.WHITE);

		username = tvUsername.getText().toString();
		password = tvPassword.getText().toString();

		if (username.length() < 3) {
			hasErrors = true;
			tvUsername.setBackgroundColor(Color.MAGENTA);
		}
		if (password.length() < 3) {
			hasErrors = true;
			tvPassword.setBackgroundColor(Color.MAGENTA);
		}

		if (hasErrors) {
			return;
		}

		// Now that we have done some simple "client side" validation it
		// is time to check with the server

		// ... perform some network activity here

		// finished

		String accountType = this.getIntent().getStringExtra("AuthTokenType");
		if (accountType == null) {
			accountType = "de.kollode.tweettle";
		}

		AccountManager accMgr = AccountManager.get(this);

		if (hasErrors) {

			// handel errors

		} else {

			// This is the magic that addes the account to the Android Account
			// Manager
			final Account account = new Account(username, accountType);
			accMgr.addAccountExplicitly(account, password, null);

			// Now we tell our caller, could be the Andreoid Account Manager or
			// even our own application
			// that the process was successful

			final Intent intent = new Intent();
			intent.putExtra(AccountManager.KEY_ACCOUNT_NAME, username);
			intent.putExtra(AccountManager.KEY_ACCOUNT_TYPE, accountType);
			intent.putExtra(AccountManager.KEY_AUTHTOKEN, accountType);
			this.setAccountAuthenticatorResult(intent.getExtras());
			this.setResult(RESULT_OK, intent);
			this.finish();

		}
	}

	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
}
