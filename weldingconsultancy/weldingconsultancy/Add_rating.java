package com.example.weldingconsultancy;



import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.net.FileNameMap;
import java.util.HashMap;
import java.util.Map;

public class Add_rating extends AppCompatActivity implements View.OnClickListener{
    RatingBar r;
    Button b;
    SharedPreferences sh;
    String url,bid,lid;
    EditText e;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_rating);

        r=(RatingBar) findViewById(R.id.ratingBar);
        e=(EditText) findViewById(R.id.e);
        b=(Button)findViewById(R.id.button6);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        lid=sh.getString("lid","");
        url=sh.getString("url","")+"add_rating";

        b.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        float rating = r.getRating();
        if (rating > 0) {
            final String s = r.getRating() + "";
            final String review = e.getText().toString();
            if (review.isEmpty()) {
                e.setError("please enter something");
            } else {
                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
//                            Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();

                                // response
//                            single row(selectOne)

                                try {
                                    JSONObject jsonObj = new JSONObject(response);
                                    if (jsonObj.getString("status").equalsIgnoreCase("ok")) {


                                        Toast.makeText(Add_rating.this, "Rating sended successfully", Toast.LENGTH_SHORT).show();
                                        Intent i=new Intent(getApplicationContext(),Add_rating.class);
                                        startActivity(i);

                                    }


                                    // }
                                    else {
                                        Toast.makeText(getApplicationContext(), "Not found", Toast.LENGTH_LONG).show();
                                    }

                                } catch (Exception e) {
                                    Toast.makeText(getApplicationContext(), "Error" + e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // error
                                Toast.makeText(getApplicationContext(), "eeeee" + error.toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                ) {

                    //                value Passing android to python
                    @Override
                    protected Map<String, String> getParams() {
                        SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        Map<String, String> params = new HashMap<String, String>();

                        params.put("lid", lid);//passing to python

                        params.put("rating", s);//passing to python
                        params.put("review", review);//passing to python
//                params.put("sid", sid);//passing to python
//                params.put("lid", uid);//passing to python

                        return params;
                    }

                };
                int MY_SOCKET_TIMEOUT_MS = 100000;

                postRequest.setRetryPolicy(new DefaultRetryPolicy(
                        MY_SOCKET_TIMEOUT_MS,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                requestQueue.add(postRequest);

            }
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity( new Intent( this,View_rating.class ) );
        finish();
    }
}
