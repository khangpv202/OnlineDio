package com.example.OnlineDio.activity;

import android.accounts.AccountManager;
import android.content.Intent;
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
import com.example.OnlineDio.util.ListNavigationAdapter;

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
               /* Thread t = new Thread(){
                    @Override
                    public void run()
                    {
                        super.run();

                        Account account = mAccountManager.getAccountsByType(OnlineDioContract.ACCOUNT_TYPE)[0];

                        try
                        {
                            User user = AccountGeneral.sServerAuthenticate.userSignIn(account.name, StreamUtils.convertToMd5(mAccountManager.getPassword(account)), AccountGeneral.AUTHTOKEN_TYPE_FULL_ACCESS);
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                        }
                    }
                };*/

                Intent i = new Intent(getApplicationContext(), ProfileActivity.class);
                startActivity(i);
            }
        });

    }

}
