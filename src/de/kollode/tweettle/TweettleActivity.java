package de.kollode.tweettle;

import java.util.ArrayList;
import java.util.Arrays;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;

public class TweettleActivity extends SherlockActivity {
	
	private SharedPreferences settings;
	private String accountType = "de.kollode.tweettle";
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        settings = getSharedPreferences("tweetle", MODE_PRIVATE);
        String defaultAccount = settings.getString("defaultAccount", "none");
             
        if(defaultAccount.equals("none")) {       
	    	Intent intent = new Intent(this, AccountListActivity.class);
	    	startActivityForResult(intent, 0);
        }else {
        	AccountManager acMgr = AccountManager.get(getApplicationContext());
        	ArrayList<Account> availableTwitterAccounts = new ArrayList<Account>(Arrays.asList(acMgr.getAccountsByType(this.accountType)));
        	
        	boolean usableAccount = false;
        	
        	for (Account account : availableTwitterAccounts) {
				if(account.name.equals(defaultAccount)) {
					usableAccount = true;
					break;
				}
			}
        	
        	if(!usableAccount) {
        		Intent intent = new Intent(this, AccountListActivity.class);
    	    	startActivityForResult(intent, 0);
        	}
        }

     	//Toast.makeText(getApplicationContext(), acMgr.getUserData(availableTwitterAccounts.get(0), "token") + " - " + acMgr.getUserData(availableTwitterAccounts.get(0), "tokenSecret"), 800).show();
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	super.onActivityResult(requestCode, resultCode, data);
    	
    	SharedPreferences.Editor editor = settings.edit();
    	
    	if(resultCode > 0 || data != null) {
	    	editor.putString("defaultAccount", data.getExtras().getString("defaultAccount"));		
	    	Toast.makeText(getApplicationContext(), settings.getString("defaultAccount", "none") , Toast.LENGTH_SHORT).show();
    	}else {
    		editor.putString("defaultAccount", "none");
    		Toast.makeText(getApplicationContext(), "No Accounts Available", Toast.LENGTH_LONG).show();
    	}
    	
    	editor.commit();
    }
    
    public void onStart() {
    	super.onStart();    	
    }
}