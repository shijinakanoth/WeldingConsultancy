package com.example.weldingconsultancy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    EditText e1;
    Button b1;
    String ip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        e1=(EditText)findViewById(R.id.txt_ip);
        SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        e1.setText(sh.getString("ip",""));
        b1=(Button)findViewById(R.id.ip_button);
        b1.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        String ipadd=e1.getText().toString();
        String url="http://"+ipadd+":5000/";
        if(ipadd.equalsIgnoreCase("")){
            e1.setError("Enter IP");
        }
        SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor e=sh.edit();
        e.putString("ip",ipadd);
        e.putString("url",url);
        e.commit();
        Intent i=new Intent(getApplicationContext(),login.class);
        startActivity(i);

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent homeIntent = new Intent(Intent.ACTION_MAIN);
        homeIntent.addCategory( Intent.CATEGORY_HOME );
        homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(homeIntent);
    }
}
