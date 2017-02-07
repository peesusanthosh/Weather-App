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
 * Created by Santhosh Reddy Peesu on 10/10/2016.
 */
public class MyMainActivityAdapter  extends ArrayAdapter<StorageDetails> {
    Context mycontext;
    int myresource;
    List<StorageDetails> sd;

    public MyMainActivityAdapter(Context context, int resource, List<StorageDetails> objects) {
        super(context, resource, objects);
        this.mycontext=context;
        this.sd=objects;
        this.myresource=resource;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) throws NullPointerException {

        if (convertView==null){
            LayoutInflater inflater=(LayoutInflater) mycontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(myresource,parent,false);
        }
        StorageDetails listitem=(StorageDetails) sd.get(position);
        TextView stateandcity=(TextView) convertView.findViewById(R.id.stateandcity);
        TextView temperature=(TextView) convertView.findViewById(R.id.ftemperature);
       TextView date=(TextView) convertView.findViewById(R.id.fdate);
        stateandcity.setText(listitem.city.toString()+", "+listitem.state.toString());
        temperature.setText(listitem.temperature.toString()+"\u00b0 F");
        date.setText(listitem.date.toString());
        convertView.setBackgroundColor(Color.CYAN);
        return convertView;
    }

}

