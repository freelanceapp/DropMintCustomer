package com.apporio.johnlaundry.AccountModule;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.login.widget.ProfilePictureView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.apporio.johnlaundry.R;
import com.apporio.johnlaundry.settergetter.FBLOGININNERSETTERGETTER;
import com.apporio.johnlaundry.settergetter.FBSETTERGETTER;
import com.apporio.johnlaundry.settergetter.ParcingOfFblogin;
import com.apporio.johnlaundry.startUpScreen.MainActivityWithicon;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import com.apporio.johnlaundry.utils.SessionManager;
import com.apporio.johnlaundry.utils.VolleySingleton;

public class FbgoogleLogin extends FragmentActivity {

    EditText phone_number_edt;
    public static AutoCompleteTextView HomeAddress_text;

    StringRequest sr;
    RequestQueue queue;
    SessionManager sm;
    String UserId, FirstName, LastName, Email,PhoneNumber,HomeAddress,Password,Status;

    Button nextbutton;
    String str_firstname,str_lastname,str_id,mobile_no,home_address,str_email;
    public static CallbackManager callbackManager;
    Button share,details;
    public static LoginButton login;
    ProfilePictureView profile;

    FBLOGININNERSETTERGETTER fblogininnersettergetter = new FBLOGININNERSETTERGETTER();

    FBSETTERGETTER fbsettergetter = new FBSETTERGETTER();
    public static ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_fbgoogle_login);
        login = (LoginButton) findViewById(R.id.login_button);

        nextbutton = (Button)findViewById(R.id.nextbutton);
        queue = VolleySingleton.getInstance(FbgoogleLogin.this).getRequestQueue();
        sm = new SessionManager(FbgoogleLogin.this);
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);

        phone_number_edt = (EditText)findViewById(R.id.mobile_no_fblogin);
        HomeAddress_text = (AutoCompleteTextView)findViewById(R.id.address_fblogin);

        nextbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                blanktextboxes();
            }
        });
        login.setReadPermissions("public_profile email");

        callbackManager = CallbackManager.Factory.create();
        if (AccessToken.getCurrentAccessToken() != null) {
            RequestData();
        }


        login.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                if (AccessToken.getCurrentAccessToken() != null) {
                    Log.e("",AccessToken.getCurrentAccessToken().toString());

                    RequestData();
                }
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException exception) {
            }
        });


        HomeAddress_text.setThreshold(1);
        HomeAddress_text.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ParcingOfFblogin.parsing(FbgoogleLogin.this, HomeAddress_text.getText().toString().trim());

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }
        });

    }
    public void RequestData(){
        GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object,GraphResponse response) {
                Log.e("response",""+response);

                JSONObject json = response.getJSONObject();
                try {

                    if(json != null){

                        str_id = json.getString("id");
                        str_email = json.getString("email");

                        Profile profile1 = Profile.getCurrentProfile();

                        str_firstname = profile1.getFirstName();
                        str_lastname = profile1.getLastName();
                        Log.e("najn",""+str_firstname);

                    }
                    pDialog.show();
                    loginwithfb();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        Bundle parameters = new Bundle();
        parameters.putString("fields","id,name,link,email,picture");
        request.setParameters(parameters);
        request.executeAsync();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);


    }

    public void blanktextboxes() {

        home_address = HomeAddress_text.getText().toString().trim();
        mobile_no = phone_number_edt.getText().toString().trim();

        if (mobile_no.equals("") || home_address.equals("")) {
            Toast.makeText(FbgoogleLogin.this, "Please fill the details", Toast.LENGTH_SHORT).show();

        } else {
            login.setVisibility(View.VISIBLE);

            nextbutton.setVisibility(View.GONE);

        }
    }

    public void loginwithfb() {

//        Toast.makeText(FbgoogleLogin.this,""+str_firstname +str_lastname+"\n"+str_id+"\n"+str_email+"\n"+home_address,Toast.LENGTH_SHORT).show();

String loginurl  = "http://keshavgoyal.com/laundry_app1/api/view_order.php?user_id="+str_firstname +"&lname=" +str_lastname +"&email="+str_email+
                "&facebook_id="+str_id+"&home_address=" +home_address +"&phone_number="+mobile_no;
        loginurl = loginurl.replace(" ","%20");

        Log.e("",""+loginurl);
        sr = new StringRequest(Request.Method.POST, loginurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Sucess", "" + response);
                //  Toast.makeText(LoginCleanline.this , ""+response ,Toast.LENGTH_SHORT).show();
                pDialog.dismiss();

                GsonBuilder gsonBuilder = new GsonBuilder();
                final Gson gson = gsonBuilder.create();

                fbsettergetter = gson.fromJson(response, FBSETTERGETTER.class);
                if (fbsettergetter.result==1){

                    fblogininnersettergetter = fbsettergetter.fbinnerdata;

                    UserId = fblogininnersettergetter.user_id;
                    FirstName= fblogininnersettergetter.fname;
                    LastName= fblogininnersettergetter.lname;
                    Email= fblogininnersettergetter.email;
                    PhoneNumber= fblogininnersettergetter.phone_number;
                    HomeAddress= fblogininnersettergetter.home_address;
                    Password = fblogininnersettergetter.password;

                    sm.createLoginSession(UserId, FirstName, LastName, Email, PhoneNumber, HomeAddress, Password);

                    Intent intent = new Intent(FbgoogleLogin.this, MainActivityWithicon.class);
                    startActivity(intent);
//                    MainActivityWithicon.myaccount.setVisibility(View.GONE);
//                    MainActivityWithicon.logoutuser.setVisibility(View.VISIBLE);

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
                Toast.makeText(FbgoogleLogin.this, "Please enter the email and password", Toast.LENGTH_SHORT).show();

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

}