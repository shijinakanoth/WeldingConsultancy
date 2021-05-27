package com.example.weldingconsultancy;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class custom_services extends BaseAdapter  {

    private Context context;
    ArrayList<String> service;
    ArrayList<String> desc;
    Button b1;
//    public custom_services(Context applicationContext, String[] service, String[] desc) {




//    }

    public custom_services(Context applicationContext, ArrayList<String> serviceName, ArrayList<String> desc) {
        this.context=applicationContext;
        this.service=serviceName;
        this.desc=desc;
    }

    @Override
    public int getCount() {
        return service.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflator=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;
        if(view==null)
        {
            gridView=new View(context);
            //gridView=inflator.inflate(R.layout.customview, null);
            gridView=inflator.inflate(R.layout.activity_custom_services,null);//same class name

        }
        else
        {
            gridView=(View)view;

        }
        TextView tv1=(TextView)gridView.findViewById(R.id.textView12);
        TextView tv2=(TextView)gridView.findViewById(R.id.textView15);
        ;



        tv1.setTextColor(Color.RED);//color setting
        tv2.setTextColor(Color.BLACK);

//        b1.setTag(tid[i]);

        tv1.setText(service.get(i));
        tv2.setText(desc.get(i));


        return gridView;

    }


}

