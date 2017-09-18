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
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.apporio.johnlaundry.utils.URLS;
import com.apporio.johnlaundry.utils.VolleySingleton;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.apporio.johnlaundry.R;
import com.apporio.johnlaundry.settergetter.PostcodeSetterGetter;

import java.util.HashMap;
import java.util.Map;

import com.apporio.johnlaundry.utils.EmailChecker;
import com.apporio.johnlaundry.utils.NetworkChecker;

public class No_Service_at_this_moment extends Activity {

    TextView continue_button;
    StringRequest sr;
    RequestQueue queue;
    public static ProgressDialog pDialog;

    EditText email_text;
    String pincode;
    String email;
    PostcodeSetterGetter postcodeSetterGetter = new PostcodeSetterGetter();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no__service_at_this_moment);

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(true);

        queue = VolleySingleton.getInstance(No_Service_at_this_moment.this).getRequestQueue();

        email_text =(EditText)findViewById(R.id.email_text);
        Intent intent = getIntent();
        pincode = intent.getStringExtra("PINCODE");
        continue_button = (TextView)findViewById(R.id.continueButton);
        continue_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkvalues();
//                Toast.makeText(No_Service_at_this_moment.this,"Thank You!. We will notify You Soon",Toast.LENGTH_SHORT).show();

  //              Intent intent = new Intent(No_Service_at_this_moment.this,HelloFacebookSampleActivity.class);
    //            startActivity(intent);
            }
        });
    }
    public  void checkvalues(){
        email= email_text.getText().toString();

        if (email.equals("")){
            Toast.makeText(No_Service_at_this_moment.this, "Please enter the email", Toast.LENGTH_SHORT).show();
        }else{
            checkInternetConnectivity();
        }
    }
    public boolean checkInternetConnectivity() {

        if (new NetworkChecker().isNetworkConnected(No_Service_at_this_moment.this)) {
            checkEmailiscorrect(email);

            return true;
        } else
            Toast.makeText(No_Service_at_this_moment.this, "check your internet connection", Toast.LENGTH_SHORT).show();

        return false;
    }

    private void checkEmailiscorrect(String email) {
        if (new EmailChecker().isEmailIsCorrect(email)) {

            try {
               // pDialog.show();
                login(email);
            } catch (Exception e) {


            }
        } else {
            Toast.makeText(No_Service_at_this_moment.this, "Please Enter Valid Email", Toast.LENGTH_SHORT).show();

        }
    }

    public void login(String email) {
      //  String pincodeurl = "http://keshavgoyal.com/laundry_app1/api/new_pincode.php?email="+email+"&pincode="+pincode;

        String pincodeurl = URLS.SendPinEmail.concat(email).concat(URLS.SendPinEmail1).concat(pincode);
        Log.e("new pin code url",""+pincodeurl);
        sr = new StringRequest(Request.Method.POST, pincodeurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Sucess", "" + response);
                //  Toast.makeText(LoginCleanline.this , ""+response ,Toast.LENGTH_SHORT).show();

                pDialog.dismiss();
                GsonBuilder gsonBuilder = new GsonBuilder();
                final Gson gson = gsonBuilder.create();

                postcodeSetterGetter = gson.fromJson(response, PostcodeSetterGetter.class);
                if (postcodeSetterGetter.result==1){

                    startActivity(new Intent(No_Service_at_this_moment.this,HelloFacebookSampleActivity.class));
                    pDialog.dismiss();

                } else {

                    Toast.makeText(No_Service_at_this_moment.this, "" +postcodeSetterGetter.msg, Toast.LENGTH_SHORT).show();

                   pDialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pDialog.dismiss();
                Toast.makeText(No_Service_at_this_moment.this, "Error", Toast.LENGTH_SHORT).show();
                if (error instanceof NetworkError){
                    Toast.makeText(No_Service_at_this_moment.this, "No Internet !!", Toast.LENGTH_SHORT).show();
                }else if (error instanceof NoConnectionError){
                    Toast.makeText(No_Service_at_this_moment.this, "No Internet", Toast.LENGTH_SHORT).show();
                }else if (error instanceof TimeoutError){
                    Toast.makeText(No_Service_at_this_moment.this, "Plz Try Again !!", Toast.LENGTH_SHORT).show();
                }
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
}
