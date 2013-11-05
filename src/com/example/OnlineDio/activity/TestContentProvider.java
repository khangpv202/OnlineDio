package com.example.OnlineDio.activity;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;
import com.example.OnlineDio.R;
import com.example.OnlineDio.accounts.AccountGeneral;
import com.example.OnlineDio.accounts.User;
import com.example.OnlineDio.provider.OnlineDioContract;
import com.example.OnlineDio.provider.dao.HomeShows;
import com.example.OnlineDio.util.DownloadImageTask;
import com.example.OnlineDio.util.StreamUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: khangpv
 * Date: 11/1/13
 * Time: 8:32 AM
 * To change this template use File | Settings | File Templates.
 */
public class TestContentProvider extends Activity
{

    public final static String PARAM_USER_PASS = "USER_PASS";
    public static String authenTest= null;
    SimpleCursorAdapter mAdapter;
    private AccountManager mAccountManager;
    private String authToken = null;
    Cursor homeCursor;
    private boolean checkSync = false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.layout_test);
        mAccountManager = AccountManager.get(getBaseContext());

        homeCursor = managedQuery(OnlineDioContract.Home.CONTENT_URI, null,
                null, null, null);
        mAdapter = new SimpleCursorAdapter(this, R.layout.home_row_of_listview2,
                homeCursor, new String[]{OnlineDioContract.Home.Comments,
                OnlineDioContract.Home.Likes,
                OnlineDioContract.Home.DisplayName,
                OnlineDioContract.Home.Title,
                OnlineDioContract.Home.Avatar}, new int[]{R.id.tvNumberOfComment, R.id.tvNumberOfLiked,
                R.id.tvNameOfDirector, R.id.tvTitleOfSong,
                R.id.ivAvatars});
//        refreshDataOfListView();
        SimpleCursorAdapter.ViewBinder savb =
                new SimpleCursorAdapter.ViewBinder()
                {
                    @Override
                    public boolean setViewValue(View view, Cursor cursor, int i)
                    {
                        switch (i)
                        {
                            case 15:
                                if (cursor.getString(15) != null)
                                {
                                    setThumbResource(view, cursor);
                                }
                                break;
                            case 10:
                                TextView numberOfComment = (TextView)
                                        view.findViewById(R.id.tvNumberOfComment);
                                String comment = cursor.getString(i);
                                numberOfComment.setText("Comment: " + comment);

                                break;
                            case 8:
                                TextView numberOfLiked = (TextView)
                                        view.findViewById(R.id.tvNumberOfLiked);
                                String liked = cursor.getString(i);
                                numberOfLiked.setText("Like: " + liked);
                                break;
                            case 14:
                                TextView nameDisplay = (TextView)
                                        view.findViewById(R.id.tvNameOfDirector);
                                nameDisplay.setText(cursor.getString(14));
                                break;
                            case 2:
                                TextView title = (TextView)
                                        view.findViewById(R.id.tvTitleOfSong);
                                title.setText(cursor.getString(2));
                                break;
                        }
                        return true;
                    }
                };

        mAdapter.setViewBinder(savb);
        Button done = (Button) findViewById(R.id.addNew);

        Button delete = (Button) findViewById(R.id.delete);
        delete.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                getContentResolver().delete(OnlineDioContract.Home.CONTENT_URI, OnlineDioContract.Home._ID + "=" + 1, null);
            }
        });

        Button profileShow = (Button) findViewById(R.id.tbnProfileShow);
        profileShow.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Cursor profile = getContentResolver().query(OnlineDioContract.Profile.CONTENT_URI, null,
                        null, null, null);
                System.out.println(profile.getCount());
            }
        });
        //mAdapter = new SimpleCursorAdapter(this,null,OnlineDioContract.Home.DisplayName,null);
        done.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ContentValues content = new ContentValues();
                content.put(OnlineDioContract.Home.UserID, 10);
                content.put(OnlineDioContract.Home.DisplayName, "nothing");
                getContentResolver().insert(OnlineDioContract.Home.CONTENT_URI, content);
                refreshDataOfListView();
            }
        });

        Button query = (Button) findViewById(R.id.query);
        final String[] mProjection =
                {
                        OnlineDioContract.Home._ID,
                        OnlineDioContract.Home.DisplayName
                };
        final String mSelectionClause = null;
        final String[] mSelectionArgs = {""};
        query.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Cursor cursor = getContentResolver().query(OnlineDioContract.Home.CONTENT_URI, null, null, null, null);
                if (cursor != null)
                {
                    try
                    {
                        if (cursor.moveToFirst())
                        {
                            Log.i("ID", cursor.getString(0));
                            Log.i("name display", cursor.getString(14));

                            while (cursor.moveToNext())
                            {

                                Log.i("ID", cursor.getString(0));
                                Log.i("name display", cursor.getString(14));
                            }
                        }
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                    cursor.close();

                }
            }
        });

        Button token = (Button) findViewById(R.id.getToken);
        token.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                try
                {
                    Account[] accounts = mAccountManager.getAccountsByType(OnlineDioContract.ACCOUNT_TYPE);
                    String au = mAccountManager.peekAuthToken(accounts[0], AccountGeneral.AUTHTOKEN_TYPE_FULL_ACCESS);
                    Log.i("Account getted", au);
                    Toast.makeText(getApplicationContext(), au, Toast.LENGTH_SHORT).show();
                    //sServerAuthenticate.userFeedContent(au);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

            }
        });

        Button sync = (Button) findViewById(R.id.syncData);
        sync.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                Account[] accounts = mAccountManager.getAccountsByType(OnlineDioContract.ACCOUNT_TYPE);
                String au = mAccountManager.peekAuthToken(accounts[0], AccountGeneral.AUTHTOKEN_TYPE_FULL_ACCESS);
                Log.i("Account getted", au + "");
                checkSync = true;
                refreshAuthenTokenAndSync();
                refreshDataOfListView();
                checkSync = false;
            }
        });

        Button btnListView = (Button) findViewById(R.id.btn_getListview);
        btnListView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                List<HomeShows.HomeShow> homeShows = readFromContentProvider();
                System.out.println(homeShows);
            }
        });

        ListView listView = (ListView) findViewById(R.id.showListView);
        listView.setAdapter(mAdapter);

        Button profile = (Button) findViewById(R.id.btn_Profile);
        profile.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                /*Account[] accounts = mAccountManager.getAccountsByType(OnlineDioContract.ACCOUNT_TYPE);
                String au = mAccountManager.peekAuthToken(accounts[0], AccountGeneral.AUTHTOKEN_TYPE_FULL_ACCESS);*/
                String userID = "586";
                refreshAuthenTokenAndLoadProfile(userID);
//                au = mAccountManager.peekAuthToken(accounts[0], AccountGeneral.AUTHTOKEN_TYPE_FULL_ACCESS);
            }
        });
    }

    private void refreshDataOfListView()
    {

        homeCursor = managedQuery(OnlineDioContract.Home.CONTENT_URI, null,
                null, null, null);
        mAdapter.changeCursor(homeCursor);
    }

    private void setThumbResource(View view, Cursor cursor)
    {
        new DownloadImageTask((ImageView) view.findViewById(R.id.ivAvatars)).execute(cursor.getString(3));
    }
    private void refreshAuthenTokenAndLoadProfile(final String userID){
        final Account account = mAccountManager.getAccountsByType(OnlineDioContract.ACCOUNT_TYPE)[0];
        final String userName = account.name;
        final String userPass = mAccountManager.getPassword(account);
        new AsyncTask<String, Void, Intent>()
        {

            @Override
            protected Intent doInBackground(String... strings)
            {
                Bundle data = new Bundle();
                try
                {
                    User user = AccountGeneral.sServerAuthenticate.userSignIn(userName, StreamUtils.convertToMd5(userPass), AccountGeneral.AUTHTOKEN_TYPE_FULL_ACCESS);
                    data.putString(AccountManager.KEY_ACCOUNT_NAME, userName);
                    data.putString(AccountManager.KEY_ACCOUNT_TYPE, OnlineDioContract.ACCOUNT_TYPE);
                    data.putString(AccountManager.KEY_AUTHTOKEN, user.getAccess_token());

                    // We keep the user's object id as an extra data on the account.
                    // It's used later for determine ACL for the data we send to the Parse.com service
                    Bundle userData = new Bundle();
                    userData.putString(AccountGeneral.USERDATA_USER_OBJ_ID, user.getUser_id());
                    data.putBundle(AccountManager.KEY_USERDATA, userData);

                    data.putString(PARAM_USER_PASS, userPass);

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
                authenTest = token;
                Cursor c = managedQuery(OnlineDioContract.Profile.CONTENT_URI,null,userID,null,null);

//                UserProfile.Profile profile = new ParseComServer().getUserProfile("558", token);
//                System.out.println(profile);
            }
        }.execute();
    }
    private void refreshAuthenTokenAndSync()
    {
        final Account account = mAccountManager.getAccountsByType(OnlineDioContract.ACCOUNT_TYPE)[0];
        final String userName = account.name;
        final String userPass = mAccountManager.getPassword(account);
        new AsyncTask<String, Void, Intent>()
        {

            @Override
            protected Intent doInBackground(String... strings)
            {
                Bundle data = new Bundle();
                try
                {
                    User user = AccountGeneral.sServerAuthenticate.userSignIn(userName, StreamUtils.convertToMd5(userPass), AccountGeneral.AUTHTOKEN_TYPE_FULL_ACCESS);
                    data.putString(AccountManager.KEY_ACCOUNT_NAME, userName);
                    data.putString(AccountManager.KEY_ACCOUNT_TYPE, OnlineDioContract.ACCOUNT_TYPE);
                    data.putString(AccountManager.KEY_AUTHTOKEN, user.getAccess_token());

                    // We keep the user's object id as an extra data on the account.
                    // It's used later for determine ACL for the data we send to the Parse.com service
                    Bundle userData = new Bundle();
                    userData.putString(AccountGeneral.USERDATA_USER_OBJ_ID, user.getUser_id());
                    data.putBundle(AccountManager.KEY_USERDATA, userData);

                    data.putString(PARAM_USER_PASS, userPass);

                    mAccountManager.addAccountExplicitly(account, userPass, data);
                    mAccountManager.setAuthToken(account, AccountGeneral.AUTHTOKEN_TYPE_FULL_ACCESS, user.getAccess_token());


                }
                catch (Exception e)
                {
//                    data.putString("KEY_ERROR_MESSAGE", e.getMessage());
                    System.out.println("ba dao");
                }

                return null;
            }

            @Override
            protected void onPostExecute(Intent intent)
            {
                super.onPostExecute(intent);
                String a = "nothing change ";
                System.out.println(a);
                Account account1 = mAccountManager.getAccountsByType(OnlineDioContract.ACCOUNT_TYPE)[0];
                Bundle bundle = new Bundle();
                bundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true); // Performing a sync no matter if it's off
                bundle.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true); // Performing a sync no matter if it's off
//                bundle.putBoolean(ContentResolver.SYNC_EXTRAS_FORCE, true);
//                ContentResolver.setIsSyncable(account1, OnlineDioContract.AUTHORITY, 1);
                if (checkSync)
                {
                    ContentResolver.requestSync(account1, OnlineDioContract.AUTHORITY, bundle);
                }
            }
        }.execute();
    }

    private List<HomeShows.HomeShow> readFromContentProvider()
    {
        Cursor curTvShows = getContentResolver().query(OnlineDioContract.Home.CONTENT_URI, null, null, null, null);

        ArrayList<HomeShows.HomeShow> shows = new ArrayList<HomeShows.HomeShow>();

        if (curTvShows != null)
        {
            while (curTvShows.moveToNext())
            {
                shows.add(new HomeShows().fromCursor(curTvShows));
            }
            curTvShows.close();
        }
        return shows;
    }
}
