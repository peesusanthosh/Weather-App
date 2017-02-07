package com.example.hw05;

import android.app.Activity;
import android.os.AsyncTask;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Santhosh Reddy Peesu on 10/4/2016.
 */
public class GetWeatherDataAsync extends AsyncTask<String,Void,ArrayList<WeatherItems>>{
    BufferedReader reader;
    CityWeatherActivity activity1;

    public GetWeatherDataAsync(CityWeatherActivity activity) {
        this.activity1 = activity;
    }

    @Override
    protected ArrayList<WeatherItems> doInBackground(String... strings) {
        for (int i=0; i<100; i++) {
            for (int j = 0; j < 10000000; j++) {
            }

        }
        try {

            URL url = new URL(strings[0]);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.connect();
            int statuscode = con.getResponseCode();
            if (statuscode == HttpURLConnection.HTTP_OK) {
                reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line = reader.readLine();
                while (line != null) {
                    sb.append(line);
                    line = reader.readLine();
                }
                return JsonParser.WeatherItemParser.doJsonParsing(sb.toString());
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }



    @Override
    protected void onPreExecute() {
        activity1.pd.show();
    }


    @Override
    protected void onPostExecute(ArrayList<WeatherItems> weatherItemses) {
        super.onPostExecute(weatherItemses);
        activity1.pd.dismiss();
        activity1.maintainWeatherItemsList(weatherItemses);
    }


    public static interface  IData{
        public void maintainWeatherItemsList(ArrayList<WeatherItems> weatherItemses);

    }
}

