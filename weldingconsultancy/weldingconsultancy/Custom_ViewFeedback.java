package com.example.weldingconsultancy;


import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class Custom_ViewFeedback extends BaseAdapter {


    private Context context;
    String[] complaint,cdate,reply,rdate,user1;

    public Custom_ViewFeedback(Context applicationContext, String[] complaint, String[] cdate, String[] reply, String[] rdate, String[] user) {

        this.context=applicationContext;
        this.complaint=complaint;
        this.cdate=cdate;
        this.reply=reply;
        this.rdate=rdate;
        this.user1=user;



    }

    @Override
    public int getCount() {
        return complaint.length;
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
            gridView=inflator.inflate(R.layout.custom_view_feedback,null);//same class name

        }
        else
        {
            gridView=(View)view;

        }


        TextView cdate1=(TextView)gridView.findViewById(R.id.textView17);
        TextView complaint1=(TextView)gridView.findViewById(R.id.textView19);
        TextView rdate1=(TextView)gridView.findViewById(R.id.textView21);
        TextView reply1=(TextView)gridView.findViewById(R.id.textView23);
        TextView user2=(TextView)gridView.findViewById(R.id.textView91);




        cdate1.setTextColor(Color.BLACK);
        complaint1.setTextColor(Color.BLACK);
        rdate1.setTextColor(Color.BLACK);
        reply1.setTextColor(Color.BLACK);
        user2.setTextColor(Color.BLACK);




        cdate1.setText(cdate[i]);
        complaint1.setText(complaint[i]);
        rdate1.setText(rdate[i]);
        reply1.setText(reply[i]);
        user2.setText(user1[i]);


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
