package com.example.OnlineDio.activity;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.example.OnlineDio.R;
import com.example.OnlineDio.accounts.AccountGeneral;

/**
 * Created with IntelliJ IDEA.
 * User: Trung
 * Date: 15/10/2013
 * Time: 08:56
 * To change this template use File | Settings | File Templates.
 */
public class LauchActivity extends Activity
{
    private Button lauch_btloginNormal;
    private String authToken = null;
    private Account mConnectedAccount;
    private AccountManager mAccountManager;

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lauch);
        mAccountManager = AccountManager.get(getBaseContext());
        lauch_btloginNormal = (Button) findViewById(R.id.lauch_btloginNormal);
        lauch_btloginNormal.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                /*Intent i = new Intent(LauchActivity.this, LoginActivity.class);
                startActivity(i);*/
                getTokenForAccountCreateIfNeeded(AccountGeneral.ACCOUNT_TYPE, AccountGeneral.AUTHTOKEN_TYPE_FULL_ACCESS);
            }
        });
    }

    private void getTokenForAccountCreateIfNeeded(String accountType, String authTokenType) {
        mAccountManager.getAuthTokenByFeatures(accountType, authTokenType, null, this, null, null,
                new AccountManagerCallback<Bundle>() {
                    @Override
                    public void run(AccountManagerFuture<Bundle> future) {
                        Bundle bnd = null;
                        try {
                            bnd = future.getResult();
                            authToken = bnd.getString(AccountManager.KEY_AUTHTOKEN);
                            if (authToken != null) {
                                String accountName = bnd.getString(AccountManager.KEY_ACCOUNT_NAME);
                                mConnectedAccount = new Account(accountName, AccountGeneral.ACCOUNT_TYPE);
                                //initButtonsAfterConnect();
                                Intent i = new Intent(LauchActivity.this, NavigationActivity.class);
                                startActivity(i);
                            }
                            showMessage(((authToken != null) ? "SUCCESS!\ntoken: " + authToken : "FAIL"));
                            Log.d("OnlineDio", "GetTokenForAccount Bundle is " + bnd);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                , null);
    }
    private void showMessage(final String msg) {
        if (msg == null || msg.trim().equals(""))
            return;

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getBaseContext(), msg, Toast.LENGTH_SHORT).show();
            }
        });
    }

}