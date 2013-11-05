package com.example.OnlineDio.provider;

import android.net.Uri;
import android.util.Log;
import com.example.OnlineDio.OnlineDioFramework.rest.RESTfulContentProvider;
import com.example.OnlineDio.OnlineDioFramework.rest.ResponseHandler;
import com.example.OnlineDio.activity.TestContentProvider;
import com.example.OnlineDio.model.UserProfile;
import com.google.gson.Gson;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: kpv
 * Date: 11/4/13
 * Time: 10:50 PM
 * To change this template use File | Settings | File Templates.
 */
public class ProfileHander implements ResponseHandler
{
    private final String TAG = "ProfileHander";

    private RESTfulContentProvider mFinchVideoProvider;


    private String mQueryText;

    public ProfileHander(RESTfulContentProvider mFinchVideoProvider, String mQueryText)
    {
        this.mQueryText = mQueryText;
        this.mFinchVideoProvider = mFinchVideoProvider;
    }


    @Override
    public void handleResponse(HttpResponse response, Uri uri) throws IOException
    {
        try
        {
            Log.i(TAG, " parseProfileEntity");
            UserProfile.Profile profile = null;
            DefaultHttpClient httpClient = new DefaultHttpClient();
            //String url = "http://113.160.50.84:1009/testing/ica467/trunk/public/user-rest/" + 586;
//            URL url1 = new URL(uri)
            HttpGet httpGet = new HttpGet(uri.toString());
            try
            {
                if (TestContentProvider.authenTest != null)
                {
                    httpGet.setHeader("Authorization", "Bearer " + TestContentProvider.authenTest);


                    HttpResponse httpResponse = httpClient.execute(httpGet);
                    String responseString = EntityUtils.toString(httpResponse.getEntity());
                    profile = new Gson().fromJson(responseString, UserProfile.class).getData();
                    if (profile != null)
                    {
                        mFinchVideoProvider.update(OnlineDioContract.Profile.CONTENT_URI,profile.getContentValues(),null,null);
                        Log.i(TAG, profile + "");
                    }
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
