//package com.example.weldingconsultancy;
//
//import androidx.annotation.RequiresApi;
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.app.Activity;
//import android.app.AlertDialog;
//import android.content.Context;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.graphics.Color;
//import android.net.ConnectivityManager;
//import android.net.NetworkInfo;
//import android.net.Uri;
//import android.os.Build;
//import android.os.Bundle;
//import android.preference.PreferenceManager;
//import android.text.TextUtils;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.Toast;
//
//import com.android.volley.DefaultRetryPolicy;
//import com.android.volley.Request;
//import com.android.volley.RequestQueue;
//import com.android.volley.Response;
//import com.android.volley.VolleyError;
//import com.android.volley.toolbox.StringRequest;
//import com.android.volley.toolbox.Volley;
//
//import org.json.JSONArray;
//import org.json.JSONObject;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Map;
//
//public class Upi_payment  extends Activity  {
//
//    EditText amount, note, name, upivirtualid;
//    Button send;
//    String TAG = "main";
//    final int UPI_PAYMENT = 0;
//    String ip, lid,mid,url;
//    SharedPreferences sh;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_upipay);
//        send = (Button) findViewById(R.id.send);
//        amount = (EditText) findViewById(R.id.amount_et);
//        note = (EditText) findViewById(R.id.note);
//        name = (EditText) findViewById(R.id.name);
//        upivirtualid = (EditText) findViewById(R.id.upi_id);
//        upivirtualid.setText("hfhfhfh@paytm");
//        upivirtualid.setEnabled(false);
//        amount.setEnabled(false);
//
//        SharedPreferences.Editor ed1 = sh.edit();
//                                ed1.putString("amount",n);
////                                ed1.putString("master_id", String.valueOf(master_id));
//                                ed1.commit();
//
////
////        ip = sh.getString("ip", "");
//////        pid = sh.getString("pid", "");
////        lid = sh.getString("lid", "");
////        String url = "http://" + ip + ":5000/viewbookedroducttotal";
////
////
////        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
////        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
////                new Response.Listener<String>() {
////                    @Override
////                    public void onResponse(String response) {
//////                          Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
////
////                        // response
////                        try {
////                            JSONObject jsonObj = new JSONObject(response);
////                            if (jsonObj.getString("status").equalsIgnoreCase("ok")) {
//////                                JSONArray jj = jsonObj.getJSONArray("data");
////
////                                String n = jsonObj.getString("amount");
//////                                String p = jsonObj.getString("price");
//////                                String t = jsonObj.getString("typ");
////                                SharedPreferences.Editor ed1 = sh.edit();
////                                ed1.putString("amount",n);
//////                                ed1.putString("master_id", String.valueOf(master_id));
////                                ed1.commit();
////
//////                                amount.setText(jj.getString(Integer.parseInt("amount")));
////                                amount.setTextColor(Color.RED);
//////                                type.setTextColor(Color.BLACK);
////
////
////                                amount.setText(n);
//////                                price.setText(p);
//////                                type.setText(t);
////
////
////
////                            }
////
////
////                            // }
////                            else {
////                                Toast.makeText(getApplicationContext(), "Not found", Toast.LENGTH_LONG).show();
////                            }
////
////                        } catch (Exception e) {
////                            Toast.makeText(getApplicationContext(), "Error" + e.getMessage().toString(), Toast.LENGTH_SHORT).show();
////                        }
////                    }
////                },
////                new Response.ErrorListener() {
////                    @Override
////                    public void onErrorResponse(VolleyError error) {
////                        // error
////                        Toast.makeText(getApplicationContext(), "eeeee" + error.toString(), Toast.LENGTH_SHORT).show();
////                    }
////                }
////        ) {
////            @Override
////            protected Map<String, String> getParams() {
////                SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
////                Map<String, String> params = new HashMap<String, String>();
////
//////                String pid = sh.getString("pid", "");
////                params.put("lid", lid);
////
////
////                return params;
////            }
////        };
////
////        int MY_SOCKET_TIMEOUT_MS = 100000;
////
////        postRequest.setRetryPolicy(new DefaultRetryPolicy(
////                MY_SOCKET_TIMEOUT_MS,
////                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
////                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
////        requestQueue.add(postRequest);
//
//
//
//
//
//        send.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                //Getting the values from the EditTexts
//                if (TextUtils.isEmpty(name.getText().toString().trim())){
//                    Toast.makeText(Upi_payment.this," Name is invalid", Toast.LENGTH_SHORT).show();
//                }else if (TextUtils.isEmpty(upivirtualid.getText().toString().trim())){
//                    Toast.makeText(Upi_payment.this," UPI ID is invalid", Toast.LENGTH_SHORT).show();
//                }else if (TextUtils.isEmpty(note.getText().toString().trim())){
//                    Toast.makeText(Upi_payment.this," Note is invalid", Toast.LENGTH_SHORT).show();
//                }else if (TextUtils.isEmpty(amount.getText().toString().trim())){
//                    Toast.makeText(Upi_payment.this," Amount is invalid", Toast.LENGTH_SHORT).show();
//                }else{
//                    payUsingUpi(name.getText().toString(), upivirtualid.getText().toString(),
//                            note.getText().toString(), amount.getText().toString());
//                }
//                String amount=sh.getString("amount","");
//                mid=sh.getString("master_id","");
//                ip=sh.getString("ip","");
//                url = "http://" + ip + ":5000/paymentdone";
//                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
//                StringRequest postRequest = new StringRequest(Request.Method.POST, url,
//                        new Response.Listener<String>() {
//                            @Override
//                            public void onResponse(String response) {
//                                //                        Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
//
//                                try {
//                                    JSONObject jsonObj = new JSONObject(response);
//                                    if (jsonObj.getString("status").equalsIgnoreCase("ok")) {
//                                        Toast.makeText(Upi_payment.this, "  Successfully", Toast.LENGTH_SHORT);
//                                    }
//                                    else {
//                                        Toast.makeText(Upi_payment.this, "  Successfully", Toast.LENGTH_SHORT);
//                                    }
//
//                                } catch (Exception e) {
//                                    Toast.makeText(Upi_payment.this,"Error" + url.toString(), Toast.LENGTH_SHORT).show();
//                                }
//                            }
//                        },
//                        new Response.ErrorListener() {
//                            @Override
//                            public void onErrorResponse(VolleyError error) {
//                                // error
//                                Toast.makeText(Upi_payment.this,"eeeee" + error.toString(), Toast.LENGTH_SHORT).show();
//                                //Toast.makeText(getApplicationContext(), "eror,,,,,", Toast.LENGTH_SHORT).show();
//                            }
//
//                        }
//                ) {
//
//                    //                value Passing android to python
//                    @Override
//                    protected Map<String, String> getParams() {
//                        SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//                        Map<String, String> params = new HashMap<String, String>();
//
//                        params.put("lid", lid);//passing to python
//                        params.put("mid", mid);
//                        params.put("amount", amount);
//
//
//                        return params;
//                    }
//                };
//
//                int MY_SOCKET_TIMEOUT_MS = 100000;
//
//                postRequest.setRetryPolicy(new DefaultRetryPolicy(
//                        MY_SOCKET_TIMEOUT_MS,
//                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//                requestQueue.add(postRequest);
//            }
//        });
//
//
//    }
//    void payUsingUpi(  String name,String upiId, String note, String amount) {
//        Log.e("main ", "name "+name +"--up--"+upiId+"--"+ note+"--"+amount);
//        Uri uri = Uri.parse("upi://pay").buildUpon()
//                .appendQueryParameter("pa", upiId)
//                .appendQueryParameter("pn", name)
//                //.appendQueryParameter("mc", "")
//                //.appendQueryParameter("tid", "02125412")
//                //.appendQueryParameter("tr", "25584584")
//                .appendQueryParameter("tn", note)
//                .appendQueryParameter("am", amount)
//                .appendQueryParameter("cu", "INR")
//                //.appendQueryParameter("refUrl", "blueapp")
//                .build();
//        Intent upiPayIntent = new Intent(Intent.ACTION_VIEW,uri);
////		upiPayIntent.setData(uri);
//        // will always show a dialog to user to choose an app
//        Intent chooser = Intent.createChooser(upiPayIntent, "Pay with");
//        Log.e("classname",chooser.resolveActivity(getPackageManager()).getClassName());
//        // check if intent resolves
//        if(null != chooser.resolveActivity(getPackageManager())) {
//            startActivityForResult(upiPayIntent, UPI_PAYMENT);
//        } else {
//            Toast.makeText(Upi_payment.this,"No UPI app found, please install one to continue",Toast.LENGTH_SHORT).show();
//        }
//    }
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        Log.e("main ", "response "+resultCode );
//        /*
//       E/main: response -1
//       E/UPI: onActivityResult: txnId=AXI4a3428ee58654a938811812c72c0df45&responseCode=00&Status=SUCCESS&txnRef=922118921612
//       E/UPIPAY: upiPaymentDataOperation: txnId=AXI4a3428ee58654a938811812c72c0df45&responseCode=00&Status=SUCCESS&txnRef=922118921612
//       E/UPI: payment successfull: 922118921612
//         */
//        switch (requestCode) {
//            case UPI_PAYMENT:
//                if ((RESULT_OK == resultCode) || (resultCode == 11)) {
//                    if (data != null) {
//                        String trxt = data.getStringExtra("response");
//                        Log.e("UPI", "onActivityResult: " + trxt);
//                        ArrayList<String> dataList = new ArrayList<String>();
//                        dataList.add(trxt);
//                        upiPaymentDataOperation(dataList);
//                    } else {
//                        Log.e("UPI", "onActivityResult: " + "Return data is null");
//                        ArrayList<String> dataList = new ArrayList<String>();
//                        dataList.add("nothing");
//                        upiPaymentDataOperation(dataList);
//                    }
//                } else {
//                    //when user simply back without payment
//                    Log.e("UPI", "onActivityResult: " + "Return data is null");
//                    ArrayList<String> dataList = new ArrayList<String>();
//                    dataList.add("nothing");
//                    upiPaymentDataOperation(dataList);
//                }
//                break;
//        }
//    }
//    private void upiPaymentDataOperation(ArrayList<String> data) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            if (isConnectionAvailable(Upi_payment.this)) {
//                String str = data.get(0);
//                Log.e("UPIPAY", "upiPaymentDataOperation: "+str);
//                String paymentCancel = "";
//                if(str == null) str = "discard";
//                String status = "";
//                String approvalRefNo = "";
//                String response[] = str.split("&");
//                for (int i = 0; i < response.length; i++) {
//                    String equalStr[] = response[i].split("=");
//                    if(equalStr.length >= 2) {
//                        if (equalStr[0].toLowerCase().equals("Status".toLowerCase())) {
//                            status = equalStr[1].toLowerCase();
//                        }
//                        else if (equalStr[0].toLowerCase().equals("ApprovalRefNo".toLowerCase()) || equalStr[0].toLowerCase().equals("txnRef".toLowerCase())) {
//                            approvalRefNo = equalStr[1];
//                        }
//                    }
//                    else {
//                        paymentCancel = "Payment cancelled by user.";
//                    }
//                }
//                if (status.equals("success")) {
//                    //Code to handle successful transaction here.
//                    Toast.makeText(Upi_payment.this, "Transaction successful.", Toast.LENGTH_SHORT).show();
//                    Log.e("UPI", "payment successfull: "+approvalRefNo);
//                }
//                else if("Payment cancelled by user.".equals(paymentCancel)) {
//                    Toast.makeText(Upi_payment.this, "Payment cancelled by user.", Toast.LENGTH_SHORT).show();
//                    Log.e("UPI", "Cancelled by user: "+approvalRefNo);
//                }
//                else {
//                    Toast.makeText(Upi_payment.this, "Transaction failed.Please try again", Toast.LENGTH_SHORT).show();
//                    Log.e("UPI", "failed payment: "+approvalRefNo);
//                }
//            } else {
//                Log.e("UPI", "Internet issue: ");
//                Toast.makeText(Upi_payment.this, "Internet connection is not available. Please check and try again", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }
//    @RequiresApi(api = Build.VERSION_CODES.M)
//    public static boolean isConnectionAvailable(Upi_payment context) {
//        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
//        if (connectivityManager != null) {
//            NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
//            if (netInfo != null && netInfo.isConnected()
//                    && netInfo.isConnectedOrConnecting()
//                    && netInfo.isAvailable()) {
//                return true;
//            }
//        }
//        return false;
//    }
//    public void onBackPressed(){ {
//        AlertDialog.Builder builder=new AlertDialog.Builder(Upi_payment.this);
//        builder.setTitle("CANCEL")
//                .setMessage("Are You Sure?")
//                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        Upi_payment.super.onBackPressed();;
//                    }
//
//                }).setNegativeButton("No", null);
//
//        AlertDialog alertDialog = builder.create();
//        alertDialog.show();
//
//    }
//    }
//
//}