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
import com.apporio.johnlaundry.settergetter.Terms_and_Conditions_Settergetter;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import com.apporio.johnlaundry.utils.NetworkChecker;
import com.apporio.johnlaundry.utils.URLS;
import com.apporio.johnlaundry.utils.VolleySingleton;

public class TermsAndConditions extends Activity {

    TextView t_c;
    StringRequest sr;
    RequestQueue queue;
    private ProgressDialog pDialog;
    Terms_and_Conditions_Settergetter tandc_settergetter = new Terms_and_Conditions_Settergetter();
    String t_c_text;
    TextView activityname ;
    @Bind(R.id.back_button_on_action_bar)LinearLayout backbtn ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_and_conditions);
        ButterKnife.bind(this);

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);

        queue = VolleySingleton.getInstance(TermsAndConditions.this).getRequestQueue();
        checkInternetConnectivity();

        t_c = (TextView)findViewById(R.id.t_c);
        activityname = (TextView) findViewById(R.id.activity_name_on_Action_bar);
        activityname.setText("Terms And Conditions");

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    public boolean checkInternetConnectivity() {

        if (new NetworkChecker().isNetworkConnected(TermsAndConditions.this)) {
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

       // String faqurl = "http://keshavgoyal.com/laundry_app1/api/t_and_c.php?merchant_id=0";

        String TandCURL= URLS.tandc_url;
        TandCURL=TandCURL.replace(" ","%20");

        sr = new StringRequest(Request.Method.POST,TandCURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Sucess", "" + response);

                GsonBuilder gsonBuilder = new GsonBuilder();
                final Gson gson = gsonBuilder.create();


                tandc_settergetter = gson.fromJson(response, Terms_and_Conditions_Settergetter.class);
                if (tandc_settergetter.result.equals("1")){
                    t_c_text = tandc_settergetter.t_c_innerdata.t_and_c;
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
                    Toast.makeText(TermsAndConditions.this, "No Internet !!", Toast.LENGTH_SHORT).show();
                }else if (error instanceof NoConnectionError){
                    Toast.makeText(TermsAndConditions.this, "No Internet", Toast.LENGTH_SHORT).show();
                }else if (error instanceof TimeoutError){
                    Toast.makeText(TermsAndConditions.this, "Plz Try Again !!", Toast.LENGTH_SHORT).show();
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
