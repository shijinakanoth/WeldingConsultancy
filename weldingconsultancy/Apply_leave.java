package com.example.weldingconsultancy;



import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Apply_leave extends AppCompatActivity implements View.OnClickListener {
    EditText l,r,days;
    Button b1;
    SharedPreferences sh;
    String url, ip, lid;
    String leave_date,reason,days1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_leave);
        l = (EditText) findViewById(R.id.editText5);
        r = (EditText) findViewById(R.id.editText3);
        days = (EditText) findViewById(R.id.days);

        b1 = (Button) findViewById(R.id.button3);
        b1.setOnClickListener(this);
        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        url = sh.getString("url", "")+"send_leave_req";
        lid = sh.getString("lid", "");


    }

    @Override
    public void onClick(View view) {
        leave_date = l.getText().toString();
        reason = r.getText().toString();
        days1 =days.getText().toString();

        if (leave_date.equals("")) {
            l.setError("*");
            l.requestFocus();
        }
        if (reason.equals("")) {
            r.setError("*");
            r.requestFocus();
        }
        if (days1.equals("")) {
            days.setError("*");
            days.requestFocus();
        }

        else {

            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
//                        Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();

                            try {
                                JSONObject jsonObj = new JSONObject(response);
                                if (jsonObj.getString("status").equalsIgnoreCase("ok")) {
                                    Toast.makeText(Apply_leave.this, "Leave Request sended Successfully", Toast.LENGTH_SHORT).show();
                                    r.setText("");
                                    l.setText("");
                                    days.setText("");

                                } else {
                                    Toast.makeText(getApplicationContext(), "Something Went wrong", Toast.LENGTH_LONG).show();
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

                    //passing to python
                    params.put("lid", lid);
                    params.put("reason", reason);
                    params.put("date", leave_date);
                    params.put("days", days1);

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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity( new Intent( Apply_leave.this, View_leave_status.class ) );
        finish();
    }
}
