package com.example.hw05;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class CityWeatherActivity extends AppCompatActivity implements GetWeatherDataAsync.IData{
String state,city;

    ArrayList<StorageDetails> favouriteslist=new ArrayList<StorageDetails>();
    ArrayList<StorageDetails> tfavouriteslist=new ArrayList<StorageDetails>();
    int minimumtemperature,maximumtemperature;
    ArrayList<WeatherItems> WeatherItems= new ArrayList<WeatherItems>();
    ProgressDialog pd;
    ListView mylistview;
    TextView currentlocation;
GetWeatherDataAsync gwda=new GetWeatherDataAsync(CityWeatherActivity.this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_weather);
        mylistview=(ListView) findViewById(R.id.mylistview);
        if (getIntent().getExtras() != null) {
            tfavouriteslist = (ArrayList<StorageDetails>) getIntent().getExtras().getSerializable(MainActivity.FAVOURITES_ARRAY);
            if(tfavouriteslist!=null && tfavouriteslist.size()!=0){
                favouriteslist=tfavouriteslist;
            }
            pd=new ProgressDialog(this);
            pd.setCancelable(false);
            pd.setMessage("Loading Hourly Data");
            currentlocation=(TextView) findViewById(R.id.currentlocation);
            state=getIntent().getExtras().getString("State");
            city=getIntent().getExtras().getString("City");

            if (city.contains(" ")){
                city=city.replace(" ","_");
            }
            String jsonurl="http://api.wunderground.com/api/53641d79875235dc/hourly/q/" + state + "/" + city + ".json";
            if (city.contains("_")){
                city=city.replace("_"," ");
            }
            gwda.execute(jsonurl);
        }

        mylistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent in = new Intent(CityWeatherActivity.this, DetailsActivity.class);
                    in.putExtra("Items", WeatherItems.get(i));
                in.putExtra("State", state);
                in.putExtra("City", city);
                in.putExtra("MaxTemp", maximumtemperature);
                in.putExtra("MinTemp", minimumtemperature);
                        startActivity(in);
            }
        });

    }


    @Override
    public void maintainWeatherItemsList(ArrayList<WeatherItems> weatherItemses) {
        if(weatherItemses!=null) {
            WeatherItems = weatherItemses;
            MyAdapter adapter = new MyAdapter(this, R.layout.row_layout, WeatherItems);
            mylistview.setAdapter(adapter);
            adapter.setNotifyOnChange(true);
            currentlocation.setText("Current Location: " + city + ", " + state + "");
            calculateMaxandMin();
        }
        else {
            WeatherItems=null;
            Toast.makeText(CityWeatherActivity.this,"No cities match your query",Toast.LENGTH_LONG).show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    final Intent mainIntent = new Intent(CityWeatherActivity.this, MainActivity.class);
                    CityWeatherActivity.this.startActivity(mainIntent);
                    CityWeatherActivity.this.finish();
                }
            }, 5000);



        }

    }

    private void calculateMaxandMin() {
        minimumtemperature=Integer.parseInt(WeatherItems.get(0).getTemperature());
        maximumtemperature=Integer.parseInt(WeatherItems.get(0).getTemperature());
        for (int i=0;i<WeatherItems.size();i++){
            if(Integer.parseInt(WeatherItems.get(i).getTemperature())< minimumtemperature){
                minimumtemperature=Integer.parseInt(WeatherItems.get(i).getTemperature());
            }
            if(Integer.parseInt(WeatherItems.get(i).getTemperature())> maximumtemperature){
                maximumtemperature=Integer.parseInt(WeatherItems.get(i).getTemperature());
            }
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
        String date= sdf.format(cal.getTime()).toString();
        StorageDetails sd=new StorageDetails(city,state,WeatherItems.get(0).getTemperature().toString(),date);
        if(favouriteslist!=null && favouriteslist.size()!=0) {
            for (int i = 0; i < favouriteslist.size(); i++) {
                if (favouriteslist.get(i).city.toString().equals(sd.city.toString()) && favouriteslist.get(i).state.toString().equals(sd.state.toString())) {
                    favouriteslist.remove(i);
                    favouriteslist.add(i, sd);
                    Toast.makeText(CityWeatherActivity.this, "Updated Favourites Record.", Toast.LENGTH_LONG).show();
                }

            }
        }
        if(!favouriteslist.contains(sd) || favouriteslist.size()==0 || favouriteslist==null){
            favouriteslist.add(sd);
            Toast.makeText(CityWeatherActivity.this, "Added to Favourites", Toast.LENGTH_LONG).show();
        }

        int id = item.getItemId();
        if (id == R.id.add_f) {
            if(WeatherItems!=null) {
                SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                SharedPreferences.Editor prefsEditor = mPrefs.edit();
                Gson gson = new Gson();
                String json = gson.toJson(favouriteslist);
                prefsEditor.putString("MyWeaherObject", json);
                prefsEditor.commit();

            }
else{
                Toast.makeText(CityWeatherActivity.this, "Sorry! The list is empty and cannot be added to favourites.", Toast.LENGTH_LONG).show();

            }
        }

        return true;
    }


    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, MainActivity.class));

    }

}
