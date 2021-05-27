package com.example.weldingconsultancy;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

public class Custom_View_rating extends BaseAdapter {
    String[] uname1,rating1,date1,review1;
    private Context context;


    public Custom_View_rating(Context applicationContext, String[] uname, String[] rating, String[] date,String[]  review) {
        this.context=applicationContext;
        this.uname1=uname;
        this.rating1=rating;
        this.date1=date;
        this.review1=review;

    }
    @Override
    public int getCount() {
        return uname1.length;
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
            //gridView=inflator.inflate(R.layout.view_feed, null);
            gridView=inflator.inflate(R.layout.custom_view_rating,null);//same class name

        }
        else
        {
            gridView=(View)view;

        }
        TextView tv1=(TextView)gridView.findViewById(R.id.textView40);
        RatingBar r=(RatingBar) gridView.findViewById(R.id.ratingBar2);
        TextView tv3=(TextView)gridView.findViewById(R.id.textView46);
        TextView review=(TextView)gridView.findViewById(R.id.r);



        tv1.setTextColor(Color.BLACK);//color setting
        tv3.setTextColor(Color.BLACK);
        review.setTextColor(Color.BLACK);

//
        tv1.setText(uname1[i]);
        r.setRating(Float.parseFloat(rating1[i]));
        tv3.setText(date1[i]);
        review.setText(review1[i]);


        return gridView;

    }



}
