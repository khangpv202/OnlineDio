package com.example.OnlineDio.syncadapter;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.content.*;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import com.example.OnlineDio.accounts.AccountGeneral;
import com.example.OnlineDio.accounts.ParseComServer;
import com.example.OnlineDio.provider.OnlineDioContract;
import com.example.OnlineDio.provider.dao.HomeShows;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: khangpv
 * Date: 11/1/13
 * Time: 2:42 PM
 * To change this template use File | Settings | File Templates.
 */
public class OnlineSyncAdapter extends AbstractThreadedSyncAdapter
{
    private final String TAG = "OnlineSyncAdapter";

    private final AccountManager mAccountManager;

    public OnlineSyncAdapter(Context context, boolean autoInitialize)
    {
        super(context, autoInitialize);
        mAccountManager = AccountManager.get(context);
    }

    @Override
    public void onPerformSync(Account account, Bundle extras, String authority, ContentProviderClient provider, SyncResult syncResult)
    {
       try{
           String authToken = mAccountManager.blockingGetAuthToken(account,
                   AccountGeneral.AUTHTOKEN_TYPE_FULL_ACCESS, true);
           String userObjectId = mAccountManager.getUserData(account,
                   AccountGeneral.USERDATA_USER_OBJ_ID);

           ParseComServer parseComServer = new ParseComServer();
           List<HomeShows.HomeShow> remoteHomeShowList = parseComServer.userFeedContent(authToken,mAccountManager);
           Log.i(TAG+"listHomeLoaded",remoteHomeShowList+ "");
           System.out.println(remoteHomeShowList);

           // Get shows from local
           ArrayList<HomeShows.HomeShow> localTvShows = new ArrayList<HomeShows.HomeShow>();
           Cursor curTvShows = provider.query(OnlineDioContract.Home.CONTENT_URI, null, null, null, null);
           if (curTvShows != null) {
               while (curTvShows.moveToNext()) {
                   localTvShows.add(new HomeShows().fromCursor(curTvShows));
               }
               curTvShows.close();
           }

           // See what Remote shows are missing on Local
           ArrayList<HomeShows.HomeShow> showsToLocal = new ArrayList<HomeShows.HomeShow>();
           for ( HomeShows.HomeShow remoteTvShow : remoteHomeShowList) {
               if (!localTvShows.contains(remoteTvShow)) // TODO REMOVE THIS
                   showsToLocal.add(remoteTvShow);
           }
           if (showsToLocal.size() == 0) {
               Log.d("udinic", TAG + "> No server changes to update local database");
           } else {
               Log.d("udinic", TAG + "> Updating local database with remote changes");

               // Updating local tv shows
               int i = 0;
               ContentValues showsToLocalValues[] = new ContentValues[showsToLocal.size()];
               for (HomeShows.HomeShow localTvShow : showsToLocal) {
                   Log.d("udinic", TAG + "> Remote -> Local [" + localTvShow.display_name + "]");
                   showsToLocalValues[i++] = localTvShow.getContentValues();
               }
               provider.bulkInsert(OnlineDioContract.Home.CONTENT_URI, showsToLocalValues);
           }
       }catch (OperationCanceledException e) {
           e.printStackTrace();
       } catch (IOException e) {
           syncResult.stats.numIoExceptions++;
           e.printStackTrace();
       } catch (AuthenticatorException e) {
           syncResult.stats.numAuthExceptions++;
           e.printStackTrace();
       } catch (Exception e) {
           e.printStackTrace();
       }
    }
}
