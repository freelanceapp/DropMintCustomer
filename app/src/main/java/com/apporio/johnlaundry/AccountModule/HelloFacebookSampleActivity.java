
package com.apporio.johnlaundry.AccountModule;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.apporio.johnlaundry.settergetter.ridedestsettergetter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.apporio.johnlaundry.R;
import com.apporio.johnlaundry.settergetter.LOGIN_SETTER_GETTER;
import com.apporio.johnlaundry.activity.MainActivity;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;

import com.apporio.johnlaundry.utils.ActivityDetector;
import com.apporio.johnlaundry.utils.EmailChecker;
import com.apporio.johnlaundry.utils.NetworkChecker;
import com.apporio.johnlaundry.utils.SessionManager;
import com.apporio.johnlaundry.utils.URLS;
import com.apporio.johnlaundry.utils.VolleySingleton;

public class HelloFacebookSampleActivity extends FragmentActivity {


    TextView  forgotpasswordtext ;

    Button login;
    Button Signuptn ;
    public static String lat;
    public static String lng;
    //private final String PENDING_ACTION_BUNDLE_KEY = "com.spinno.laundryapp";
    //private PendingAction pendingAction = PendingAction.NONE;

    String get_id, get_name, get_gender, get_email, get_birthday;

   // private enum PendingAction {NONE,POST_PHOTO,POST_STATUS_UPDATE}
    //AccessToken  accessToken ;
    public static Activity HelloFacebookActiviy ;

    ////////////////////////////// googleplusintegration variables  ////////////////////////
   /* private static final int RC_SIGN_IN = 0;
    private boolean mSignInClicked;
    private ConnectionResult mConnectionResult;
    private GoogleApiClient mGoogleApiClient;
    private boolean mIntentInProgress;

    SignInButton googlesigninbutton ;*/

    EditText Email_text,Password_text;
    SessionManager sm;
    String email_values, password_values;

    String UserId, FirstName, LastName, Email,PhoneNumber,HomeAddress,Password,Status;
    StringRequest sr;
    RequestQueue queue;
    SignUpActivity signup = new SignUpActivity();

    LOGIN_SETTER_GETTER loginsettergetter = new LOGIN_SETTER_GETTER();

    int status;

//    TextView fbloginbutton,googleloginbutton;
//    private CallbackManager callbackManager;
    private TextView textView;

  //  private AccessTokenTracker accessTokenTracker;
    //private ProfileTracker profileTracker;
    public static ProgressDialog pDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main);
        HelloFacebookActiviy = HelloFacebookSampleActivity.this ;
        ActivityDetector.open_LoginActivity=true;

        sm = new SessionManager(HelloFacebookSampleActivity.this);
        queue = VolleySingleton.getInstance(HelloFacebookSampleActivity.this).getRequestQueue();
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);

//        fbloginbutton = (TextView)findViewById(R.id.facebooklogin);
//        fbloginbutton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Intent intent = new Intent(HelloFacebookSampleActivity.this,FbgoogleLogin.class);
//                startActivity(intent);
//            }
//        });
        login = (Button)findViewById(R.id.login_button_loginactivity);
        Email_text = (EditText)findViewById(R.id.email_text);
        Password_text = (EditText)findViewById(R.id.password_text);

        forgotpasswordtext = (TextView)findViewById(R.id.forgotpassword);
        forgotpasswordtext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(HelloFacebookSampleActivity.this,ForgotPassword.class));
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(HelloFacebookSampleActivity.this, "chala", Toast.LENGTH_SHORT).show();
  //              Intent intent = new Intent(HelloFacebookSampleActivity.this,MainActivity.class);
    //            startActivity(intent);
                blanktextboxes();

            }
        });

        Signuptn = (Button) findViewById(R.id.sign_up_buttn_in_login_activity);
           Signuptn.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   startActivity(new Intent(HelloFacebookSampleActivity.this, RegisterByPostCode.class));
                   finish();
               }
           });


//          activityname = (TextView)findViewById(R.id.activity_name_on_Action_bar);
//            activityname.setText("User Profile");
//
//
//        backbtn = (LinearLayout) findViewById(R.id.back_button_on_action_bar);
//                backbtn.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        finish();
//                    }
//                });

    }
    public void blanktextboxes() {

        password_values = Password_text.getText().toString().trim();
        email_values = Email_text.getText().toString().trim();

        if (email_values.equals("") || password_values.equals("")) {
            generatesecreenmessage("Please fill the details");


        } else {
            checkInternetConnectivity();
        }
    }
    public boolean checkInternetConnectivity() {

        if (new NetworkChecker().isNetworkConnected(HelloFacebookSampleActivity.this)) {
            checkEmailiscorrect(email_values);

            return true;
        } else
            generatesecreenmessage("check your internet connection");

        return false;
    }

    private void checkEmailiscorrect(String email) {
        if (new EmailChecker().isEmailIsCorrect(email)) {


            passwordvalidation(password_values);
        } else {
            generatesecreenmessage("Please Enter Valid Email");

        }
    }

    public void generatesecreenmessage(String s) {
        Toast.makeText(HelloFacebookSampleActivity.this, "" + s, Toast.LENGTH_SHORT).show();
    }

    public void passwordvalidation(String password) {

        Matcher hasLetter = signup.letter.matcher(password);
        Matcher hasDigit = signup.digit.matcher(password);

        if (!hasLetter.find()) {

            generatesecreenmessage("wrong password");
        } else if (!hasDigit.find()) {
            generatesecreenmessage("wrong password");

        } else if (password.length() < 6) {
            generatesecreenmessage("your password requires minimum 6 characters");
        } else if (password.length() > 15) {
            generatesecreenmessage("your password is valid for maximum 15 characters");

        } else {
            signIn();
        }
    }
    private void signIn() {
        if (new NetworkChecker().isNetworkConnected(HelloFacebookSampleActivity.this)) {

            try {
                pDialog.show();
                login();

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }
    public void login() {
      //  String loginurl  = "http://keshavgoyal.com/laundry_app1/api/login.php?email="+email_values+"&password=" + password_values;

        String loginurl= URLS.loginurl.concat(email_values).concat(URLS.loginurl1).concat(password_values);
        loginurl=loginurl.replace(" ","%20");
        Log.e("login url",""+loginurl);

        sr = new StringRequest(Request.Method.POST, loginurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Sucess", "" + response);
                //  Toast.makeText(LoginCleanline.this , ""+response ,Toast.LENGTH_SHORT).show();

                pDialog.dismiss();
                GsonBuilder gsonBuilder = new GsonBuilder();
                final Gson gson = gsonBuilder.create();

                loginsettergetter = gson.fromJson(response, LOGIN_SETTER_GETTER.class);
                if (loginsettergetter.result.equals("1")){

                    UserId = loginsettergetter.logininnerdata.user_id;
                    FirstName = loginsettergetter.logininnerdata.fname;
                    LastName = loginsettergetter.logininnerdata.lname;
                    Email = loginsettergetter.logininnerdata.email;
                    PhoneNumber =loginsettergetter.logininnerdata.phone_number;
                    Password=loginsettergetter.logininnerdata.password;
                    HomeAddress=loginsettergetter.logininnerdata.home_address;
                    Status=loginsettergetter.logininnerdata.status;

                    sm.createLoginSession(UserId, FirstName, LastName, Email, PhoneNumber, HomeAddress, Password );

                   // GetLatLong(HomeAddress);

                    sm.SaveLatlong(loginsettergetter.logininnerdata.latitude , loginsettergetter.logininnerdata.longitude,loginsettergetter.logininnerdata.appartment);

                    Intent intent = new Intent(HelloFacebookSampleActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();

                    //     Toast.makeText(getApplicationContext(), "" + UserId, Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(getApplicationContext(), "Please enter correct details", Toast.LENGTH_SHORT).show();
                    pDialog.dismiss();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pDialog.dismiss();
                Log.e("Sucess", "" + error.toString());
                if (error instanceof NetworkError){
                    Toast.makeText(HelloFacebookActiviy, "No Internet !!", Toast.LENGTH_SHORT).show();
                }else if (error instanceof NoConnectionError){
                    Toast.makeText(HelloFacebookActiviy, "No Internet", Toast.LENGTH_SHORT).show();
                }else if (error instanceof TimeoutError){
                    Toast.makeText(HelloFacebookActiviy, "Plz Try Again !!", Toast.LENGTH_SHORT).show();
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


        queue.add(sr);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }


    public void GetLatLong(String placeid){


        String locationurl2 = "https://maps.googleapis.com/maps/api/place/details/json?placeid=" + placeid + "&key=AIzaSyCC3Ci--XByh-o-ukFw0IBOGD1of7hglA4";
        locationurl2 = locationurl2.replace(" ", "%20");
        Log.e("url", "" + locationurl2);

        pDialog = new ProgressDialog(HelloFacebookSampleActivity.this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);


        StringRequest sr2222 = new StringRequest(Request.Method.GET, locationurl2, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                pDialog.dismiss();
                Log.e("", "" + response);
                try {
                    GsonBuilder gsonBuilder = new GsonBuilder();
                    final Gson gson = gsonBuilder.create();

                    ridedestsettergetter received2 = new ridedestsettergetter();
                    received2 = gson.fromJson(response, ridedestsettergetter.class);


                    lat = received2.innerdestination.geometry.location11.lat;
                    lng = received2.innerdestination.geometry.location11.lng;



                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("exception", "" + e);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("exception", "" + error);
                pDialog.dismiss();
            }
        });
        sr2222.setRetryPolicy(new DefaultRetryPolicy(50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        pDialog.show();
        queue.add(sr2222);


    }



}