package com.example.hw05;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

/**
 * Created by Santhosh Reddy Peesu on 10/4/2016.
 */
public class JsonParser {

    static class WeatherItemParser {

        static ArrayList<WeatherItems> doJsonParsing(String inputstring) throws JSONException {

            ArrayList<WeatherItems> WeatherItems = new ArrayList<WeatherItems>();
            JSONObject root = new JSONObject(inputstring);
            if (root.has("hourly_forecast")) {
                JSONArray hourlyforecastarray = root.getJSONArray("hourly_forecast");
                for (int i = 0; i < hourlyforecastarray.length(); i++) {
                    JSONObject hourlyforecastobject = hourlyforecastarray.getJSONObject(i);
                    WeatherItems WeatherItem = new WeatherItems();
                    WeatherItem.setClimateType(hourlyforecastobject.getString("wx"));
                    WeatherItem.setHumidity(hourlyforecastobject.getString("humidity"));
                    WeatherItem.setClouds(hourlyforecastobject.getString("condition"));
                    if (hourlyforecastobject.has("icon_url")) {
                        WeatherItem.setIconUrl(hourlyforecastobject.getString("icon_url"));
                    } else {
                        WeatherItem.setIconUrl("");
                    }
                    JSONObject wspdobject = (hourlyforecastobject.getJSONObject("wspd"));
                    WeatherItem.setWindSpeed(wspdobject.getString("english"));
                    JSONObject dewpointobject = (hourlyforecastobject.getJSONObject("dewpoint"));
                    WeatherItem.setDewpoint(dewpointobject.getString("english"));
                    JSONObject pressureobject = (hourlyforecastobject.getJSONObject("mslp"));
                    WeatherItem.setPressure(pressureobject.getString("metric"));
                    JSONObject feelslikeobject = (hourlyforecastobject.getJSONObject("mslp"));
                    WeatherItem.setFeelsLike(feelslikeobject.getString("english"));
                    JSONObject tempobject = (hourlyforecastobject.getJSONObject("temp"));
                    WeatherItem.setTemperature(tempobject.getString("english"));
                    JSONObject wdirobject = (hourlyforecastobject.getJSONObject("wdir"));
                    WeatherItem.setWindDirection(wdirobject.getString("degrees") + "° " + wdirobject.getString("dir"));
                    JSONObject timeobject = (hourlyforecastobject.getJSONObject("FCTTIME"));
                    WeatherItem.setTime(timeobject.getString("civil"));
                    WeatherItems.add(WeatherItem);
                }
            }
            else
            {
                WeatherItems=null;
            }

                return WeatherItems;
            }

    }
}



