package de.kollode.tweettle;

import java.util.ArrayList;

import android.accounts.Account;
import android.app.Activity;
import android.os.Bundle;

public class TweettleActivity extends Activity {
	
	//IF the user has already some twitter accounts we will use them
	private ArrayList<Account> availableTwitterAccounts = null;
	private String accountType = "de.kollode.tweettle.auth.login";
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.twitter_authenticator);
        /*availableTwitterAccounts = new ArrayList<Account>(Arrays.asList(AccountManager.get(getApplicationContext()).getAccountsByType("accountType")));
        
        if(!availableTwitterAccounts.isEmpty()) {
        
	        setListAdapter(new ArrayAdapter<Account>(this, R.layout.account_item, availableTwitterAccounts));
	        
	        ListView lv = getListView();
	        lv.setTextFilterEnabled(true);
        }else {        	
        	Toast.makeText(getApplicationContext(), "No Accounts Available", 800).show();
        }*/
    }
}