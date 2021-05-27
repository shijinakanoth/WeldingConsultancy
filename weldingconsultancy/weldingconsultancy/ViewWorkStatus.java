package com.example.weldingconsultancy;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ViewWorkStatus extends AppCompatActivity implements AdapterView.OnItemClickListener {
    ListView lv;
    String url,ip;
    SharedPreferences sh;
static String[] service,worksite,desp,workdate,status,id;
public  static int pos;
@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_work_status);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        lv=(ListView)findViewById(R.id.lkn);
        lv.setOnItemClickListener(this);
        ip=sh.getString("ip","");
        url="http://"+ip+":5000/workstatus";



    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            if (jsonObj.getString("status").equalsIgnoreCase("ok")) {

                                JSONArray js= jsonObj.getJSONArray("data");//from python
                                service=new String[js.length()];
                                worksite=new String[js.length()];
                                desp=new String[js.length()];
                                workdate=new String[js.length()];
                                status=new String[js.length()];
                                id=new String[js.length()];


                                for(int i=0;i<js.length();i++)
                                {
                                    JSONObject u=js.getJSONObject(i);
                                    service[i]=u.getString("service");//dbcolumn name
                                    worksite[i]=u.getString("worksite");
                                    desp[i]=u.getString("description");
                                    workdate[i]=u.getString("work_date");
                                    status[i]=u.getString("work_status");
                                    id[i]=u.getString("workid");



                                }
                                lv.setAdapter(new Custom_work_status(getApplicationContext(),service,worksite,desp,workdate,status,id));//custom_view_service.xml and li is the listview object


                            }

                            else {
                                Toast.makeText(getApplicationContext(), "Not found", Toast.LENGTH_LONG).show();
                            }

                        }    catch (Exception e) {
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
                params.put("lid",sh.getString("lid",""));
                return params;
            }
        };


        int MY_SOCKET_TIMEOUT_MS=100000;

        postRequest.setRetryPolicy(new DefaultRetryPolicy(
                MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(postRequest);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        pos=i;

        final CharSequence[] options = { "Schedule","Complaint","Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(ViewWorkStatus.this);
        builder.setTitle("Choose your option");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (options[item].equals("Schedule"))
                {

                    Intent ij=new Intent(getApplicationContext(),scheduledWorkers.class);
                    startActivity(ij);

                }

                else if (options[item].equals("Complaint"))
                {

                    Intent ij=new Intent(getApplicationContext(),View_feedback.class);
                    startActivity(ij);

                }

                else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();


    }
}

