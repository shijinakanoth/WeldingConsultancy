package com.example.weldingconsultancy;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

public class uploadWork extends AppCompatActivity implements View.OnClickListener {
    EditText e1,e2,e3;
    TextView t1;
    Button b1;
    String url,ip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_work);
        t1=(TextView)findViewById(R.id.upload_work);
        e1=(EditText)findViewById(R.id.worksite);
        e2=(EditText)findViewById(R.id.latitude);
        e3=(EditText)findViewById(R.id.longitude);
        b1=(Button)findViewById(R.id.submit);
        SharedPreferences sh=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        ip=sh.getString("ip","");
        url="http://"+ip+":5000/uploadwork";
        b1.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        final String worksite=e1.getText().toString();
        final String latitude=e2.getText().toString();
        final String longitude=e3.getText().toString();

        if (worksite.equalsIgnoreCase("")){
            e1.setError("Enter the location");
        }else if (latitude.equalsIgnoreCase("")){
            e2.setError("Enter latitude");
        }else if (longitude.equalsIgnoreCase("")){
            e3.setError("Enter longitude");
        }
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();

                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            if (jsonObj.getString("status").equalsIgnoreCase("ok")) {
                                Toast.makeText(uploadWork.this, " Successfully", Toast.LENGTH_SHORT).show();
                            } else {
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

                params.put("worksite",worksite );//passing to python
                params.put("latitude",latitude );
                params.put("longitude",longitude);
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
