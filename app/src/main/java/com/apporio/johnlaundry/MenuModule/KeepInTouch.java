package com.apporio.johnlaundry.MenuModule;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.apporio.johnlaundry.R;
import com.apporio.johnlaundry.settergetter.KeepInTouchSettergetter;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import com.apporio.johnlaundry.utils.NetworkChecker;
import com.apporio.johnlaundry.utils.URLS;
import com.apporio.johnlaundry.utils.VolleySingleton;

public class KeepInTouch extends Activity {


    TextView phone,email,home;
    StringRequest sr;
    RequestQueue queue;
    private ProgressDialog pDialog;
    String phone_text,email_text,home_text;
    TextView activityname ;
    @Bind(R.id.back_button_on_action_bar)LinearLayout backbtn ;

    KeepInTouchSettergetter keepInTouchSettergetter = new KeepInTouchSettergetter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keep_in_touch);
        ButterKnife.bind(this);
        activityname = (TextView) findViewById(R.id.activity_name_on_Action_bar);
        activityname.setText("Keep In Touch");
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);

        queue = VolleySingleton.getInstance(KeepInTouch.this).getRequestQueue();
        checkInternetConnectivity();

        phone = (TextView)findViewById(R.id.phone_no);
        email = (TextView)findViewById(R.id.email_address);
        home = (TextView)findViewById(R.id.home_address);

    }
    public boolean checkInternetConnectivity() {

        if (new NetworkChecker().isNetworkConnected(KeepInTouch.this)) {
            try {
                pDialog.show();
                termsconditions();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        } else
            return false;
    }
    private void termsconditions() {

      //  String faqurl = "http://keshavgoyal.com/laundry_app1/api/contact_us.php";

        String KeepInTouchURL=URLS.contact_us_url;
        KeepInTouchURL=KeepInTouchURL.replace(" ","%20");

        sr = new StringRequest(Request.Method.POST,KeepInTouchURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Sucess", "" + response);

                GsonBuilder gsonBuilder = new GsonBuilder();
                final Gson gson = gsonBuilder.create();


                keepInTouchSettergetter = gson.fromJson(response, KeepInTouchSettergetter.class);
                if (keepInTouchSettergetter.result.equals("1")){
                    phone_text = keepInTouchSettergetter.keepInTouchInnerdata.phone_number;
                    email_text = keepInTouchSettergetter.keepInTouchInnerdata.email;
                    home_text = keepInTouchSettergetter.keepInTouchInnerdata.address;

                    phone.setText(phone_text);
                    email.setText(email_text);
                    home.setText(home_text);

                    pDialog.dismiss();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                    pDialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Sucess", "" + error.toString());
          pDialog.dismiss();
                if (error instanceof NetworkError){
                    Toast.makeText(KeepInTouch.this, "No Internet !!", Toast.LENGTH_SHORT).show();
                }else if (error instanceof NoConnectionError){
                    Toast.makeText(KeepInTouch.this, "No Internet", Toast.LENGTH_SHORT).show();
                }else if (error instanceof TimeoutError){
                    Toast.makeText(KeepInTouch.this, "Plz Try Again !!", Toast.LENGTH_SHORT).show();
                }
            }
        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();

                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("Content-Type","application/x-www-form-urlencoded");
                return params;
            }
        };
        sr.setRetryPolicy(new DefaultRetryPolicy(30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


        queue.add(sr);
    }

}
