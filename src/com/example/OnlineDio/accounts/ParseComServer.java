package com.example.OnlineDio.accounts;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.util.Log;
import com.example.OnlineDio.model.ProfileUpdate;
import com.example.OnlineDio.provider.OnlineDioContract;
import com.example.OnlineDio.provider.dao.HomeShows;
import com.example.OnlineDio.util.StreamUtils;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles the comminication with Parse.com
 * <p/>
 * User: udinic
 * Date: 3/27/13
 * Time: 3:30 AM
 */
public class ParseComServer implements ServerAuthenticate
{

    private final static String APP_ID = "iRnc8I1X0du5q6HrJtZW0a5DlB0JcpOQbjA6chha";
    private final static String REST_API_KEY = "tv1xCdYKTwI3p205KHCn1yWpbVj2OHldV9cPZuNZ";
//    private static String TAG;
    private static String TAG = "ParseComServer";

    /**
     * Return the basic headers to connect our app's parse.com details
     *
     * @return
     */
    public static List<Header> getAppParseComHeaders()
    {
        List<Header> ret = new ArrayList<Header>();
        ret.add(new BasicHeader("X-Parse-Application-Id", APP_ID));
        ret.add(new BasicHeader("X-Parse-REST-API-Key", REST_API_KEY));
        return ret;
    }

    @Override
    public User userSignUp(String name, String email, String pass, String authType) throws Exception
    {

        String url = "https://api.parse.com/1/users";

        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url);

        for (Header header : getAppParseComHeaders())
        {
            httpPost.addHeader(header);
        }
        httpPost.addHeader("Content-Type", "application/json");

        String user = "{\"username\":\"" + email + "\",\"password\":\"" + pass + "\",\"phone\":\"415-392-0202\"}";
        HttpEntity entity = new StringEntity(user);
        httpPost.setEntity(entity);

        try
        {
            HttpResponse response = httpClient.execute(httpPost);
            String responseString = EntityUtils.toString(response.getEntity());

            if (response.getStatusLine().getStatusCode() != 201)
            {
                ParseComError error = new Gson().fromJson(responseString, ParseComError.class);
                throw new Exception("Error creating user[" + error.code + "] - " + error.error);
            }

            User createdUser = new Gson().fromJson(responseString, User.class);

            return createdUser;

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public User userSignIn(String user, String pass, String authType) throws Exception
    {
        DefaultHttpClient httpClient = new DefaultHttpClient();
//        String url = "http://192.168.1.222/testing/ica467/trunk/public/auth-rest";
        String url = "http://113.160.50.84:1009/testing/ica467/trunk/public/auth-rest";

        JSONObject jo = new JSONObject();
        jo.put("username", user);
        jo.put("password", pass);
        jo.put("grant_type", "password");
        jo.put("client_id", "123456789");

        URL urlReal = new URL(url);
        HttpPost httpPost = new HttpPost(urlReal.toURI());

        httpPost.setHeader("Content-Type", "application/json");
        httpPost.setEntity(new StringEntity(jo.toString(), "UTF-8"));

// Execute POST
        String responseString = EntityUtils.toString(httpClient.execute(httpPost).getEntity());

        Log.d(TAG, responseString);
        User loggedUser = new Gson().fromJson(responseString, User.class);
        if(!loggedUser.getUser_id().isEmpty())
            return loggedUser;
        Log.d(TAG, loggedUser + "");
        return null;
    }

    public List<HomeShows.HomeShow> userFeedContent(final String token, AccountManager accountManager)
    {
        List<HomeShows.HomeShow> homeShowList = null;
        DefaultHttpClient httpClient = new DefaultHttpClient();
//        String url = "http://192.168.1.222/testing/ica467/trunk/public/home-rest?limit=20&offset=&time_from=&time_to=";
        String url = "http://113.160.50.84:1009/testing/ica467/trunk/public/home-rest?limit=20&offset=&time_from=&time_to=";
        HttpGet httpGet = new HttpGet(url);
        for (Header header : getAppParseComHeaders())
        {
            httpGet.addHeader(header);
        }
        httpGet.setHeader("Authorization", "Bearer " + token);
        try
        {
            Log.d("responseString", "responseString");
            HttpResponse httpResponse = httpClient.execute(httpGet);
            //String responseString=  EntityUtils.toString(httpClient.execute(httpGet).getEntity());
            String responseString = EntityUtils.toString(httpResponse.getEntity());
            if(responseString.equals("cannot access my apis")){
                Account account = accountManager.getAccountsByType(OnlineDioContract.ACCOUNT_TYPE)[0];
                User user = AccountGeneral.sServerAuthenticate.userSignIn(account.name, StreamUtils.convertToMd5(accountManager.getPassword(account)), AccountGeneral.AUTHTOKEN_TYPE_FULL_ACCESS);
                accountManager.setAuthToken(account, AccountGeneral.AUTHTOKEN_TYPE_FULL_ACCESS, user.getAccess_token());
                httpGet.removeHeaders("Authorization");
                httpGet.setHeader("Authorization",user.getAccess_token());
                httpResponse = httpClient.execute(httpGet);
                responseString = EntityUtils.toString(httpResponse.getEntity());
            }
            homeShowList = new ArrayList<HomeShows.HomeShow>();
            homeShowList = new Gson().fromJson(responseString, HomeShows.class).getData();
            JsonObject jsonElements = new Gson().fromJson(responseString, JsonObject.class);
            JsonArray jsonArray = new Gson().fromJson(jsonElements.getAsJsonArray("data"), JsonArray.class);

            for (JsonElement i : jsonArray)
            {
                homeShowList.add(new Gson().fromJson(i, HomeShows.HomeShow.class));
            }
            Log.d("responseString", responseString);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return homeShowList;
    }

   /* public UserProfile.Profile getUserProfile(final String userID, final String token)
    {
        Log.i(TAG, userID + "userID");
        Log.i(TAG, "Token " + token);
        UserProfile.Profile profile = null;
        //new DownloadProfile(result,userID,token).start();
        DefaultHttpClient httpClient = new DefaultHttpClient();
        String url = "http://113.160.50.84:1009/testing/ica467/trunk/public/user-rest/" + userID;
        HttpGet httpGet = new HttpGet(url);

        for (Header header : getAppParseComHeaders())
        {
            httpGet.addHeader(header);
        }
        httpGet.setHeader("Authorization", "Bearer " + token);


        try
        {
            HttpResponse httpResponse = httpClient.execute(httpGet);
            String responseString = EntityUtils.toString(httpResponse.getEntity());
            profile = new Gson().fromJson(responseString, UserProfile.class).getData();
            if (profile != null)
            {
                //return profile;
                Log.i(TAG, profile + "");
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return profile;
    }*/

    public static void putUserProfile(String userId, String token, ProfileUpdate profile,AccountManager accountManager)
    {
        String url = "http://192.168.1.222/testing/ica467/trunk/public/user-rest/" + userId;

        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpPut httpPut = new HttpPut(url);

        for (Header header : getAppParseComHeaders())
        {
            httpPut.addHeader(header);
        }
        httpPut.addHeader("Content-Type", "application/json");
        httpPut.addHeader("Authorization", "Bearer " + token);

        //String user = "{\"username\":\"" + email + "\",\"password\":\"" + pass + "\",\"phone\":\"415-392-0202\"}";
        //HttpEntity entity = new StringEntity(user);
        //httpPost.setEntity(entity);
        String value = new Gson().toJson(profile);
        try
        {
            httpPut.setEntity(new StringEntity(value));
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
         token = "123445";
        Log.i(TAG,"putUserProfile "+token);
        try
        {
            HttpResponse response = httpClient.execute(httpPut);
            String responseString = EntityUtils.toString(response.getEntity());

            if(responseString.equals("cannot access my apis")){
                Account account = accountManager.getAccountsByType(OnlineDioContract.ACCOUNT_TYPE)[0];
                User user = AccountGeneral.sServerAuthenticate.userSignIn(account.name, StreamUtils.convertToMd5(accountManager.getPassword(account)), AccountGeneral.AUTHTOKEN_TYPE_FULL_ACCESS);
                Log.i(TAG,"putUserProfile "+user.getAccess_token());
                accountManager.setAuthToken(account, AccountGeneral.AUTHTOKEN_TYPE_FULL_ACCESS, user.getAccess_token());
                httpPut.removeHeaders("Authorization");
                httpPut.setHeader("Authorization",user.getAccess_token());
                response = httpClient.execute(httpPut);
                responseString = EntityUtils.toString(response.getEntity());
            }
            Log.i("UpdateProfile", responseString + "");
            if (response.getStatusLine().getStatusCode() != 201)
            {
                ParseComError error = new Gson().fromJson(responseString, ParseComError.class);
                throw new Exception("Error creating user[" + error.code + "] - " + error.error);
            }


        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        catch (Exception e)
        {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    /* class DownloadProfile extends Thread{
         private UserProfile.Profile profile;
         private String userID;
         private String token;

         DownloadProfile(UserProfile.Profile profile,String userID,String token)
         {
             this.profile = profile;
             this.userID = userID;
             this.token = token;
         }

         @Override
         public void run()
         {
             super.run();    //To change body of overridden methods use File | Settings | File Templates.

             DefaultHttpClient httpClient = new DefaultHttpClient();
             String url = "http://113.160.50.84:1009/testing/ica467/trunk/public/user-rest/" + userID;
             HttpGet httpGet = new HttpGet(url);

             for (Header header : getAppParseComHeaders())
             {
                 httpGet.addHeader(header);
             }
             httpGet.setHeader("Authorization", "Bearer " + token);


             try
             {
                 HttpResponse httpResponse = httpClient.execute(httpGet);
                 String responseString = EntityUtils.toString(httpResponse.getEntity());
                 profile = new Gson().fromJson(responseString, UserProfile.class).getData();
                 if (profile != null)
                 {
                     //return profile;
                     Log.i(TAG, profile + "");
                 }

             }
             catch (Exception e)
             {
                 e.printStackTrace();
             }
         }
     }*/
    public static class ParseComError implements Serializable
    {
        public int code;
        public String error;
    }

}
