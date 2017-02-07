package com.example.hw05;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
EditText state,city;
ArrayList<StorageDetails> favouriteslist=new ArrayList<StorageDetails>();
    final static String FAVOURITES_ARRAY="questions";
    TextView txtnofavourites,header;
    ListView mylistview;
    SharedPreferences  mPrefs;
String enteredstate,enteredcity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewByIds();
        mPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        Gson gson = new Gson();
        String json = mPrefs.getString("MyWeaherObject", "");
        Type type = new TypeToken<ArrayList<StorageDetails>>() {}.getType();
        favouriteslist  = gson.fromJson(json, type);
        if(favouriteslist==null || favouriteslist.size()==0){
            mylistview.setVisibility(View.GONE);
            header.setVisibility(View.GONE);
            txtnofavourites.setText("There is no city in your favourites");
        }
        else
        {

            header.setVisibility(View.VISIBLE);
            mylistview.setVisibility(View.VISIBLE);
            txtnofavourites.setText(" ");
            MyMainActivityAdapter adapter = new MyMainActivityAdapter(MainActivity.this, R.layout.row_storage_layout, favouriteslist);
            mylistview.setAdapter(adapter);
        }

        mylistview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                favouriteslist.remove(i);
                MyMainActivityAdapter adapter = new MyMainActivityAdapter(MainActivity.this, R.layout.row_storage_layout, favouriteslist);
                mylistview.setAdapter(adapter);
                if(favouriteslist.isEmpty()) {
                    mylistview.setVisibility(View.GONE);
                    header.setVisibility(View.GONE);
                    txtnofavourites.setText("There is no city in your favourites");
                    Toast.makeText(MainActivity.this, "City deleted.", Toast.LENGTH_LONG).show();

                }
                SharedPreferences.Editor editor = mPrefs.edit();
                editor.remove("MyWeaherObject");
                editor.apply();
                Gson gson=new Gson();
                String json = gson.toJson(favouriteslist);
                editor.putString("MyWeaherObject", json);
                editor.commit();

                return false;
            }
        });
        mylistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(isConnected()) {
                    Intent in = new Intent(MainActivity.this, CityWeatherActivity.class);
                    in.putExtra("State", favouriteslist.get(i).state.toString());
                    in.putExtra("City", favouriteslist.get(i).city.toString());
                    in.putExtra(FAVOURITES_ARRAY, favouriteslist);
                    startActivity(in);
                }
                else{
                    Toast.makeText(MainActivity.this,"Please check your internet connection to proceed further.",Toast.LENGTH_LONG).show();
                }

            }
        });

    }

    private void findViewByIds() {
        city= (EditText) findViewById(R.id.txtboxcity);
        state= (EditText) findViewById(R.id.txtboxstate);
        txtnofavourites=(TextView) findViewById(R.id.txtnofavourites);
        mylistview=(ListView) findViewById(R.id.listView);
        header=(TextView) findViewById(R.id.headingfav);
    }

    public void goToCityWeatherActivity(View view) {
        if(isConnected()) {
            Intent i = new Intent(MainActivity.this, CityWeatherActivity.class);
            i.putExtra("State", state.getText().toString());
            i.putExtra("City", city.getText().toString());
            i.putExtra(FAVOURITES_ARRAY, favouriteslist);
            startActivity(i);
        }
        else{
            Toast.makeText(this,"Please check your internet connection to proceed further.",Toast.LENGTH_LONG).show();
        }


    }
    private boolean isConnected() {
        ConnectivityManager cm= (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo ni= cm.getActiveNetworkInfo();
        if(ni!=null)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}
