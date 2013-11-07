package com.example.OnlineDio.activity;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import com.example.OnlineDio.R;
import com.example.OnlineDio.accounts.AccountGeneral;
import com.example.OnlineDio.accounts.User;
import com.example.OnlineDio.provider.OnlineDioContract;
import com.example.OnlineDio.util.ListNavigationAdapter;
import com.example.OnlineDio.util.StreamUtils;

/**
 * Created with IntelliJ IDEA.
 * User: Trung
 * Date: 14/10/2013
 * Time: 14:00
 * To change this template use File | Settings | File Templates.
 */
public class NavigationActivity extends FragmentActivity
{
    private AccountManager mAccountManager;
    final String[] data = {"Home", "Favorite", "Following", "Audience", "Genres", "Setting", "Help Center", "Sign Out"};
    final String[] fragments =
            {
                    "com.example.OnlineDio.fragment.HomeFragment",
                    "com.example.OnlineDio.fragment.ContentFragment",

            };
    final String profilefragment = "com.example.OnlineDio.activity.ProfileActivity";
    private LinearLayout layoutDrawer;
    private LinearLayout llProfile;
    public static String authenProfile;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation);
        mAccountManager = AccountManager.get(getApplicationContext());
        layoutDrawer = (LinearLayout) findViewById(R.id.left_drawer);
        Log.e(this.getPackageName().toString(), "Yes");
        llProfile = (LinearLayout) findViewById(R.id.navigation_station_layout);
        ArrayAdapter<String> adapter = new ListNavigationAdapter(this, data);


        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.navigation_drawer_layout);
        final ListView navList = (ListView) findViewById(R.id.navigation_lvDrawer);

        navList.setAdapter(adapter);
        navList.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int pos, long id)
            {
                drawer.setDrawerListener(new DrawerLayout.SimpleDrawerListener()
                {
                    @Override
                    public void onDrawerClosed(View drawerView)
                    {
                        super.onDrawerClosed(drawerView);
                        final FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
                        tx.replace(R.id.navigation_main_FrameLayout, Fragment.instantiate(NavigationActivity.this, fragments[pos]));
                        tx.addToBackStack(null);
                        tx.commit();
                    }
                });
                drawer.closeDrawer(layoutDrawer);
            }
        });
        FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
        tx.replace(R.id.navigation_main_FrameLayout, Fragment.instantiate(NavigationActivity.this, fragments[0]));
        tx.addToBackStack(null);
        tx.commit();
        llProfile.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(getApplicationContext(), ProfileActivity.class);

                startActivity(i);
            }
        });
        refreshAuthenTokenAndLoadProfile();
//        Cursor c = managedQuery(OnlineDioContract.Profile.CONTENT_URI, null, "586", null, null);
    }

    private void refreshAuthenTokenAndLoadProfile()
    {
        final Account account = mAccountManager.getAccountsByType(OnlineDioContract.ACCOUNT_TYPE)[0];
        final String userName = account.name;
        final String userPass = mAccountManager.getPassword(account);
        Log.i("OLD Authentication", "" + mAccountManager.peekAuthToken(account, AccountGeneral.AUTHTOKEN_TYPE_FULL_ACCESS));
        new AsyncTask<String, Void, Intent>()
        {

            @Override
            protected Intent doInBackground(String... strings)
            {
                Bundle data = new Bundle();
                try
                {
                    User user = AccountGeneral.sServerAuthenticate.userSignIn(userName, StreamUtils.convertToMd5(userPass), AccountGeneral.AUTHTOKEN_TYPE_FULL_ACCESS);
//                    data.putString(AccountManager.KEY_ACCOUNT_NAME, userName);
                    data.putString(AccountManager.KEY_ACCOUNT_TYPE, OnlineDioContract.ACCOUNT_TYPE);
                    data.putString(AccountManager.KEY_AUTHTOKEN, user.getAccess_token());

                    // We keep the user's object id as an extra data on the account.
                    // It's used later for determine ACL for the data we send to the Parse.com service
                    Bundle userData = new Bundle();
//                    userData.putString(AccountGeneral.USERDATA_USER_OBJ_ID, user.getUser_id());
//                    data.putBundle(AccountManager.KEY_USERDATA, userData);
                    authenProfile = user.getAccess_token();
                    Log.i("New Authentication", "" + authenProfile + "\n" + user.getAccess_token());
//                    data.putString(PARAM_USER_PASS, userPass);
                    data.putString("USER_ID", user.getUser_id());

                    mAccountManager.addAccountExplicitly(account, userPass, data);
                    mAccountManager.setAuthToken(account, AccountGeneral.AUTHTOKEN_TYPE_FULL_ACCESS, user.getAccess_token());


                }
                catch (Exception e)
                {
//                    data.putString("KEY_ERROR_MESSAGE", e.getMessage());
                    System.out.println("ba dao");
                }
                final Intent res = new Intent();
                res.putExtras(data);
                return res;

            }

            @Override
            protected void onPostExecute(Intent intent)
            {
                super.onPostExecute(intent);
                String token = intent.getStringExtra(AccountManager.KEY_AUTHTOKEN);
                String userID = intent.getStringExtra("USER_ID");
                Cursor c = managedQuery(OnlineDioContract.Profile.CONTENT_URI, null, userID, null, null);
            }
        }.execute();
    }


}
