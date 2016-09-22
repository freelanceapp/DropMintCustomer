package com.apporio.johnlaundry.AccountModule;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.apporio.johnlaundry.R;
import com.apporio.johnlaundry.settergetter.Pincode_inner_settergetter;
import com.apporio.johnlaundry.settergetter.Pincodes_setter_getter;
import com.apporio.johnlaundry.settergetter.PostcodeSetterGetter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.apporio.johnlaundry.utils.URLS;
import com.apporio.johnlaundry.utils.VolleySingleton;

public class RegisterByPostCode extends Activity {

    TextView registerbypostcode;

    StringRequest sr;
    RequestQueue queue;
    public static ProgressDialog pDialog;

    EditText pincode_text;
    PostcodeSetterGetter postcodeSetterGetter = new PostcodeSetterGetter();

    String item;
    Pincodes_setter_getter pincodes_setter_getter = new Pincodes_setter_getter();
    public ArrayList<Pincode_inner_settergetter> pincode_inner_settergetters = new ArrayList<>();
    ArrayList<String> pincodes = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_by_post_code);

        queue = VolleySingleton.getInstance(RegisterByPostCode.this).getRequestQueue();

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(true);

        pincode_text = (EditText)findViewById(R.id.pincode_text);

//        pincode_text.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//
//                item = pincode_text.getSelectedItem().toString();
//                Log.e("selected item ", "" + item);
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//        pincodes();
        registerbypostcode = (TextView)findViewById(R.id.registerpostcode);
        registerbypostcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                item = pincode_text.getText().toString();
                if (item.equals("")){

                    Toast.makeText(RegisterByPostCode.this, "Please Enter pincode", Toast.LENGTH_SHORT).show();
                }else {
                    login(item);
                }

            }
        });
    }

    public void login(final String pincode) {
       // String pincodeurl = "http://keshavgoyal.com/laundry_app1/api/chk_pincode.php?pincode="+pincode;

        String pincodeurl= URLS.pincodeurl+pincode;
        pincodeurl=pincodeurl.replace(" ","%20");

        sr = new StringRequest(Request.Method.POST, pincodeurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                pDialog.dismiss();
                Log.e("Success", "" + response);

                GsonBuilder gsonBuilder = new GsonBuilder();
                final Gson gson = gsonBuilder.create();

                postcodeSetterGetter = gson.fromJson(response, PostcodeSetterGetter.class);
                if (postcodeSetterGetter.result==1){

                    Intent intent = new Intent(RegisterByPostCode.this,SignUpActivity.class);
                    intent.putExtra("PINCODE", pincode);
                    startActivity(intent);
                    pDialog.dismiss();

                } else {

                    Intent intent = new Intent(RegisterByPostCode.this,No_Service_at_this_moment.class);
                    intent.putExtra("PINCODE",pincode);
                    startActivity(intent);
                    pDialog.dismiss();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pDialog.dismiss();
                Toast.makeText(RegisterByPostCode.this, "Error", Toast.LENGTH_SHORT).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                //params.put("email", gettingemail);
                //params.put("password", gettingpassword);
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }
        };
        sr.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
          pDialog.show();

        queue.add(sr);
    }


//    public void pincodes() {
//        String pincodeurl = "http://keshavgoyal.com/laundry_app1/api/view_pincode.php";
//        sr = new StringRequest(Request.Method.POST, pincodeurl, new RevisedResponse.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                Log.e("Sucess", "" + response);
//
//                GsonBuilder gsonBuilder = new GsonBuilder();
//                final Gson gson = gsonBuilder.create();
//
//                pincodes.clear();
//                pincodes_setter_getter = gson.fromJson(response, Pincodes_setter_getter.class);
//                if (pincodes_setter_getter.result==1){
//
//                    pincode_inner_settergetters = pincodes_setter_getter.pincode_inner_settergetters;
//
//                    for (int i =0;i<pincode_inner_settergetters.size();i++){
//
//                        pincodes.add(pincode_inner_settergetters.get(i).pincode);
//                    }
//
//                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(RegisterByPostCode.this, android.R.layout.simple_spinner_item, pincodes);
//
//                    // Drop down layout style - list view with radio button
//                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//
//                    // attaching data adapter to spinner
//                    pincode_text.setAdapter(dataAdapter);
//                }
//
//            }
//        }, new RevisedResponse.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                pDialog.dismiss();
//                Toast.makeText(RegisterByPostCode.this, "Error", Toast.LENGTH_SHORT).show();
//
//            }
//        }) {
//            @Override
//            protected Map<String, String> getParams() {
//                Map<String, String> params = new HashMap<String, String>();
//                //params.put("email", gettingemail);
//                //params.put("password", gettingpassword);
//                return params;
//            }
//
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("Content-Type", "application/x-www-form-urlencoded");
//                return params;
//            }
//        };
//        sr.setRetryPolicy(new DefaultRetryPolicy(
//                30000,
//                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//
//
//        queue.add(sr);
//    }
}
