package com.example.weldingconsultancy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

public class Registration extends AppCompatActivity implements View.OnClickListener {
    EditText e1, e2, e3, e4, e5, e6;
    Button btsave;
    String url, ip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        e1 = (EditText) findViewById(R.id.editText4);
        e2 = (EditText) findViewById(R.id.editText6);
        e3 = (EditText) findViewById(R.id.editText2);
        e4 = (EditText) findViewById(R.id.editText10);
        e5 = (EditText) findViewById(R.id.editText11);
        e6 = (EditText) findViewById(R.id.editText12);
        btsave = (Button) findViewById(R.id.bt_save);
        btsave.setOnClickListener(this);
        SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        ip = sh.getString("ip", "");
        url = "http://" + ip + ":5000/registration";
    }

    @Override
    public void onClick(View view) {
        final String name = e1.getText().toString();
        final String email = e2.getText().toString();
        final String contact = e3.getText().toString();
        final String address = e4.getText().toString();
        final String password = e5.getText().toString();
        final String confirm = e6.getText().toString();

        if (name.equalsIgnoreCase("")) {
            e1.setError("Required field");
        } else if (email.equalsIgnoreCase("")) {
            e2.setError("Required field");
        } else if (contact.equalsIgnoreCase("")) {
            e3.setError("Required field");
        } else if (address.equalsIgnoreCase("")) {
            e4.setError("required field");
        } else if (password.equalsIgnoreCase("")) {
            e5.setError("Create a password");
        } else if (confirm.equalsIgnoreCase("")) {
            e6.setError("Confirm the password");
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
                                    Toast.makeText(Registration.this, " Successfully", Toast.LENGTH_SHORT).show();
                                    Intent i = new Intent(getApplicationContext(), Registration.class);
                                    startActivity(i);
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

                    params.put("editText4", name);//passing to python
                    params.put("editText6", email);
                    params.put("edittext2", contact);
                    params.put("editText10", address);
                    params.put("editText11", password);


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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity( new Intent( this, login.class ) );
        finish();
    }
    }






