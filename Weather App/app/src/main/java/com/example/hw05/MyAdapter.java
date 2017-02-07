package com.example.hw05;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Santhosh Reddy Peesu on 10/4/2016.
 */
public class MyAdapter extends ArrayAdapter<WeatherItems> {
    Context mycontext;
    int myresource;
    List<WeatherItems> WeatherItemsList;

    public MyAdapter(Context context, int resource, List<WeatherItems> objects) {
        super(context, resource, objects);
        this.mycontext=context;
        this.WeatherItemsList=objects;
        this.myresource=resource;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) throws NullPointerException {

        if (convertView==null){
            LayoutInflater inflater=(LayoutInflater) mycontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(myresource,parent,false);
        }

        WeatherItems weatheritem=WeatherItemsList.get(position);
        TextView time=(TextView) convertView.findViewById(R.id.txttime);
        TextView temperature=(TextView) convertView.findViewById(R.id.txttemperature);
        TextView weather=(TextView) convertView.findViewById(R.id.txtweather);
         time.setText(weatheritem.getTime().toString());
        temperature.setText(weatheritem.getTemperature().toString()+"\u00b0 F");
        weather.setText(weatheritem.getClimateType().toString());
        ImageView imgsmall=(ImageView) convertView.findViewById(R.id.imgsmall);
        new GetImagesAsync(imgsmall).execute(weatheritem.getIconUrl());
        return convertView;
    }

}
