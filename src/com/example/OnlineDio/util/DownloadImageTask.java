package com.example.OnlineDio.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;

/**
 * Created with IntelliJ IDEA.
 * User: khangpv
 * Date: 11/4/13
 * Time: 10:01 AM
 * To change this template use File | Settings | File Templates.
 */
public class DownloadImageTask extends AsyncTask<String, Void, Bitmap>
{
    ImageView bmImage;

    public DownloadImageTask(ImageView bmImage)
    {
        this.bmImage = bmImage;
    }

    protected Bitmap doInBackground(String... urls)
    {
        String urldisplay = urls[0];
        Bitmap mIcon11 = null;
        try
        {   Log.i("load image ","num");
            InputStream in = new java.net.URL(urldisplay).openStream();
            mIcon11 = BitmapFactory.decodeStream(in);
        }
        catch (Exception e)
        {
            Log.e("Error", ""+e.getMessage());
            e.printStackTrace();
        }
        return mIcon11;
    }

    protected void onPostExecute(Bitmap result)
    {
        if (result != null)
        {
            bmImage.setImageBitmap(result);
        }
    }
}
