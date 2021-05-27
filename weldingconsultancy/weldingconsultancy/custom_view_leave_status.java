package com.example.weldingconsultancy;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class custom_view_leave_status extends BaseAdapter {
    private Context context;
    String[] adate,ldate,reason1,status1,reg1;
    public custom_view_leave_status(Context applicationContext, String[] applydate, String[] leavedate, String[] reason, String[] status, String[] reg) {
        this.context=applicationContext;
        this.adate=applydate;
        this.ldate=leavedate;
        this.reason1=reason;
        this.status1=status;
        this.reg1=reg;



    }

    @Override
    public int getCount() {
        return reason1.length;
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
            gridView=inflator.inflate(R.layout.custom_view_leave_status,null);//same class name

        }
        else
        {
            gridView=(View)view;

        }


        TextView adate1=(TextView)gridView.findViewById(R.id.textView4);
        TextView ldate1=(TextView)gridView.findViewById(R.id.textView6);
        TextView reason2=(TextView)gridView.findViewById(R.id.textView9);
        TextView status2=(TextView)gridView.findViewById(R.id.textView15);
        TextView reg2=(TextView)gridView.findViewById(R.id.textView13);





        adate1.setTextColor(Color.BLACK);
        ldate1.setTextColor(Color.BLACK);
        reason2.setTextColor(Color.BLACK);
        status2.setTextColor(Color.BLACK);
        reg2.setTextColor(Color.BLACK);



        adate1.setText(adate[i]);
        ldate1.setText(ldate[i]);
        reason2.setText(reason1[i]);
        status2.setText(status1[i]);
        reg2.setText(reg1[i]);

//
//        SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(context);
//        String url=sh.getString("url","")+photo[i];
////        Toast.makeText(context,url,Toast.LENGTH_LONG).show();
//        Picasso.with(context).load(url).transform(new CircleTransform()). into(img);//circle
//
//



        return gridView;

    }
}


