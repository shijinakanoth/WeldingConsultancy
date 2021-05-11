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

public class Custom_work_status extends BaseAdapter implements View.OnClickListener {

private Context context;
String[]service,worksite,desp,workdate,status,id;
    Button b1;
    public Custom_work_status(Context applicationContext, String[] service, String[] worksite, String[] desp, String[] workdate, String[] status, String[] id) {
        this.context=applicationContext;
        this.service=service;
        this.worksite=worksite;
        this.desp=desp;
        this.workdate=workdate;
        this.status=status;
        this.id=id;

//        service.service,worktable.worksite,worktable.description,worktable.work_date,worktable.status,worktable.workid
    }

    @Override
    public int getCount() {
        return id.length;
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
            gridView=inflator.inflate(R.layout.activity_custom_work_status,null);//same class name

        }
        else
        {
            gridView=(View)view;

        }
        TextView tv1=(TextView)gridView.findViewById(R.id.textView4);
        TextView tv2=(TextView)gridView.findViewById(R.id.textView6);
        TextView tv3=(TextView)gridView.findViewById(R.id.textView9);
        TextView tv4=(TextView)gridView.findViewById(R.id.textView10);
        TextView tv5=(TextView)gridView.findViewById(R.id.textView13);


        b1=(Button)gridView.findViewById(R.id.button);
        b1.setOnClickListener(this);


        tv1.setTextColor(Color.RED);//color setting
        tv2.setTextColor(Color.BLACK);
        tv3.setTextColor(Color.BLACK);
        tv4.setTextColor(Color.BLACK);
        tv5.setTextColor(Color.BLACK);

        b1.setTag(id[i]);
        tv1.setText(service[i]);
        tv2.setText(worksite[i]);
        tv3.setText(desp[i]);
        tv4.setText(workdate[i]);
        tv5.setText(status[i]);
//        tv6.setText(id[i]);

        return gridView;

    }

    @Override
    public void onClick(View v) {



        String tid= v.getTag().toString();
        SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor e1=sh.edit();
        e1.putString("workid", tid);
        e1.commit();

        Intent i=new Intent(context,scheduledWorkers.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);


    }
}

