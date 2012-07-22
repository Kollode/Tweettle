package de.kollode.tweettle;

import java.util.ArrayList;
import java.util.Arrays;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockListActivity;

import de.kollode.tweettle.authentication.TwitterAuthenticatorActivity;

public class AccountListActivity extends SherlockListActivity {

	private SharedPreferences settings;
	//IF the user has already some twitter accounts we will use them
	private ArrayList<Account> availableTwitterAccounts = null;
	private String accountType = "de.kollode.tweettle";
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		settings = getSharedPreferences("tweetle", MODE_PRIVATE);
		
		AccountManager acMgr = AccountManager.get(getApplicationContext());
	        
        availableTwitterAccounts = new ArrayList<Account>(Arrays.asList(acMgr.getAccountsByType(this.accountType)));

        if(availableTwitterAccounts.isEmpty()) {
        	setResult(RESULT_CANCELED);
        	finish();
        }
        
        setListAdapter(new ArrayAdapter<Account>(this, R.layout.account_item, availableTwitterAccounts));
        
        ListView lv = getListView();
        lv.setTextFilterEnabled(true);
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {		
		Intent result = new Intent();
		result.putExtra("defaultAccount", availableTwitterAccounts.get(position).name);
		
		setResult(1, result);
		finish();
    }
	
}
