package com.example.hw05;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Santhosh Reddy Peesu on 10/4/2016.
 */
public class GetImagesAsync extends AsyncTask<String,Void,Bitmap> {
    ImageView mImageView;
    public GetImagesAsync(ImageView iv) {
        this.mImageView = iv;
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }
    @Override
    protected Bitmap doInBackground(String... strings) {

        URL url = null;
        try {
            url = new URL(strings[0]);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            Bitmap img = BitmapFactory.decodeStream(con.getInputStream());
            return img;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

        }


        return null;
    }

    @Override
    protected void onPostExecute (Bitmap s){
        this.mImageView.setImageBitmap(s);
    }

}



