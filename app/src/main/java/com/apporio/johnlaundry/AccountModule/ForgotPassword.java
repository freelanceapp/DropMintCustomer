package com.apporio.johnlaundry.AccountModule;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
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
import com.apporio.johnlaundry.settergetter.Forgotpasswordsettergetter;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import com.apporio.johnlaundry.utils.EmailChecker;
import com.apporio.johnlaundry.utils.NetworkChecker;
import com.apporio.johnlaundry.utils.SessionManager;
import com.apporio.johnlaundry.utils.URLS;
import com.apporio.johnlaundry.utils.VolleySingleton;

public class ForgotPassword extends Activity {


    @Bind(R.id.back_button_on_action_bar)
    LinearLayout backbtn;
    @Bind(R.id.activity_name_on_Action_bar)
    TextView activityname;
    public static Activity SignUpActivity;

    @Bind(R.id.forgotpassemail)EditText emailedt;
    @Bind(R.id.request_button_in_sign_up_activity)Button request;

    SessionManager sm;

    StringRequest sr;
    RequestQueue queue;

    String email;
    private ProgressDialog pDialog;

    Forgotpasswordsettergetter forgotsettergetter= new Forgotpasswordsettergetter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        ButterKnife.bind(this);
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);

        sm = new SessionManager(ForgotPassword.this);
        queue = VolleySingleton.getInstance(ForgotPassword.this).getRequestQueue();

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });

        request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 email= emailedt.getText().toString();

                if (email.equals("")) {
                    Toast.makeText(ForgotPassword.this, "Please Enter your Registered email", Toast.LENGTH_LONG).show();
                } else if (!email.equals("")) {
                    if (new EmailChecker().isEmailIsCorrect(email)) {
                        try {
                            docallAPIforforgotpassword(email);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(ForgotPassword.this, "Please Enter your Registered email", Toast.LENGTH_LONG).show();

                    }
                }

            }

        });


    }

    public boolean checkInternetConnectivity() {

        if (new NetworkChecker().isNetworkConnected(ForgotPassword.this)) {
            return true;
        } else
            return false;
    }

    public void docallAPIforforgotpassword(String email) {
        if (checkInternetConnectivity()) {
            try {
              //  doForgotAPI();
            } catch (Exception e) {
                e.printStackTrace();
            }
            pDialog.show();
        }

    }

//    private void doForgotAPI() {
//
//       // String forgotpasswordurl = "http://keshavgoyal.com/laundry_app1/api/forgot_pass.php?email=" + email;
//
//        String forgotpasswordurl = URLS.forgotpasswordurl.concat(email);
//        forgotpasswordurl=forgotpasswordurl.replace(" ","%20");
//
//        sr = new StringRequest(Request.Method.POST,forgotpasswordurl, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                Log.e("Sucess", "" + response);
////                Toast.makeText(Forgot_password.this , ""+response ,Toast.LENGTH_SHORT).show();
//
//                GsonBuilder gsonBuilder = new GsonBuilder();
//                final Gson gson = gsonBuilder.create();
//
//                forgotsettergetter = gson.fromJson(response, Forgotpasswordsettergetter.class);
//                if (forgotsettergetter.result.equals("1")){
//
//                    Intent intent = new Intent(ForgotPassword.this,HelloFacebookSampleActivity.class);
//                    startActivity(intent);
//                    pDialog.dismiss();
//
//                }
//                else {
//                    Toast.makeText(getApplicationContext(), "Please enter correct details", Toast.LENGTH_SHORT).show();
//                    pDialog.dismiss();
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Log.e("Sucess", "" + error.toString());
//            }
//        }){
//            @Override
//            protected Map<String,String> getParams(){
//                Map<String,String> params = new HashMap<String, String>();
//
//                return params;
//            }
//
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                Map<String,String> params = new HashMap<String, String>();
//                params.put("Content-Type","application/x-www-form-urlencoded");
//                return params;
//            }
//        };
//        sr.setRetryPolicy(new DefaultRetryPolicy(30000,
//                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//
//
//        queue.add(sr);
//    }

}