package com.apporio.johnlaundry.MenuModule;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Html;
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
import com.apporio.johnlaundry.settergetter.Privacypolicy;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import com.apporio.johnlaundry.utils.NetworkChecker;
import com.apporio.johnlaundry.utils.URLS;
import com.apporio.johnlaundry.utils.VolleySingleton;

public class Privacy_policy extends Activity {

    TextView t_c;
    StringRequest sr;
    RequestQueue queue;
    private ProgressDialog pDialog;
    Privacypolicy privacypolicy_settergetter = new Privacypolicy();
    String t_c_text;
    TextView activityname ;
    @Bind(R.id.back_button_on_action_bar)LinearLayout backbtn ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_policy);

        ButterKnife.bind(this);
        activityname = (TextView) findViewById(R.id.activity_name_on_Action_bar);
        activityname.setText("Privacy Policy");

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);

        queue = VolleySingleton.getInstance(Privacy_policy.this).getRequestQueue();
        checkInternetConnectivity();

        t_c = (TextView)findViewById(R.id.t_c);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    public boolean checkInternetConnectivity() {

        if (new NetworkChecker().isNetworkConnected(Privacy_policy.this)) {
            try {
                pDialog.show();
                privacypolicy();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        } else
            return false;
    }
    private void privacypolicy() {

      //  String faqurl = "http://keshavgoyal.com/laundry_app1/api/privacy_policy.php?merchant_id=0";

        String PrivacyPolicyURl= URLS.privacy_policy_url;
        PrivacyPolicyURl=PrivacyPolicyURl.replace(" ","%20");

        sr = new StringRequest(Request.Method.POST,PrivacyPolicyURl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Sucess", "" + response);

                GsonBuilder gsonBuilder = new GsonBuilder();
                final Gson gson = gsonBuilder.create();


                privacypolicy_settergetter = gson.fromJson(response, Privacypolicy.class);
                if (privacypolicy_settergetter.result==1){

                    t_c_text = privacypolicy_settergetter.privacy_policy_inner_settergetter.t_and_c;
                    t_c.setText(Html.fromHtml(t_c_text));

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
                    Toast.makeText(Privacy_policy.this, "No Internet !!", Toast.LENGTH_SHORT).show();
                }else if (error instanceof NoConnectionError){
                    Toast.makeText(Privacy_policy.this, "No Internet", Toast.LENGTH_SHORT).show();
                }else if (error instanceof TimeoutError){
                    Toast.makeText(Privacy_policy.this, "Plz Try Again !!", Toast.LENGTH_SHORT).show();
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