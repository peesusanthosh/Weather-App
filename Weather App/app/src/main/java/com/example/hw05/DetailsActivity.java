package com.example.hw05;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailsActivity extends AppCompatActivity {
String state,city;
    WeatherItems item=new WeatherItems();
    TextView tvheader,tvmintemp,tvmaxtemp,tvcurrentlocation,tvfeelslike,tvhumidity,tvclouds,tvwinds,tvpressure,tvdewpoint,temperature,weather;
    ImageView mImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        findViewByIds();
        if (getIntent().getExtras() != null) {
            item= (WeatherItems) getIntent().getExtras().getSerializable("Items");
            state=getIntent().getExtras().getString("State");
            city=getIntent().getExtras().getString("City");
            tvmintemp.setText(getIntent().getExtras().getInt("MinTemp")+" Fahrenheit");
            tvmaxtemp.setText(getIntent().getExtras().getInt("MaxTemp")+" Fahrenheit");
            tvfeelslike.setText(item.getFeelsLike()+" Fahrenheit");
            tvhumidity.setText(item.getHumidity()+"%");
            tvclouds.setText(item.getClouds());
            tvwinds.setText(item.getWindSpeed()+" mph, "+item.getWindDirection());
            tvpressure.setText(item.getPressure()+" hPa");
            tvdewpoint.setText(item.getDewpoint()+" Fahrenheit");
            tvcurrentlocation.setText(city+", "+state+"");
            temperature.setText(item.getTemperature().toString()+"\u00b0 F");
            weather.setText(item.getClimateType().toString());
            new GetImagesAsync(mImageView).execute(item.getIconUrl());

        }
    }
    private void findViewByIds() {
        tvheader = (TextView) findViewById(R.id.tvcurrentlocation);
        tvmintemp = (TextView) findViewById(R.id.textViewmin);
        tvmaxtemp = (TextView) findViewById(R.id.textViewmax);
        mImageView=(ImageView) findViewById(R.id.imageView2);
        tvcurrentlocation=(TextView) findViewById(R.id.tvcurrentlocation);
        tvfeelslike = (TextView) findViewById(R.id.tvfeelslike);
        tvhumidity = (TextView) findViewById(R.id.tvhumidity);
        tvclouds = (TextView) findViewById(R.id.tvclouds);
        tvwinds = (TextView) findViewById(R.id.tvwinds);
        tvpressure = (TextView) findViewById(R.id.tvpressure);
        tvdewpoint = (TextView) findViewById(R.id.tvdewpoint);
        temperature = (TextView) findViewById(R.id.temperature);
        weather = (TextView) findViewById(R.id.weather);

    }
}

