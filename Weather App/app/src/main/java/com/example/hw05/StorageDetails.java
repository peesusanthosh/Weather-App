package com.example.hw05;

import java.io.Serializable;

/**
 * Created by Santhosh Reddy Peesu on 10/10/2016.
 */
public class StorageDetails implements Serializable {
    String city,state,temperature,date;

    public StorageDetails(String city, String state, String temperature,String date) {
        this.city = city;
        this.state = state;
        this.temperature = temperature;
        this.date = date;
    }


    public String getCity() {
        return city;
    }

    public String getTemperature() {
        return temperature;
    }

    public String getState() {
        return state;
    }

    public String getDate() {
        return date;
    }
}
