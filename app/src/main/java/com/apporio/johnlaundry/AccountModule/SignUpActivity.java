package com.apporio.johnlaundry.AccountModule;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

//import com.afollestad.materialdialogs.MaterialDialog;
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
import com.apporio.johnlaundry.activity.MainActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.apporio.johnlaundry.R;
import com.apporio.johnlaundry.settergetter.SignupSettergetter;
import com.apporio.johnlaundry.settergetter.parsingforoffers;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.Bind;
import butterknife.ButterKnife;
import com.apporio.johnlaundry.utils.EmailChecker;
import com.apporio.johnlaundry.utils.NetworkChecker;
import com.apporio.johnlaundry.utils.SessionManager;
import com.apporio.johnlaundry.utils.Toaster;
import com.apporio.johnlaundry.utils.URLS;
import com.apporio.johnlaundry.utils.VolleySingleton;

public class SignUpActivity extends Activity {

    @Bind(R.id.back_button_on_action_bar)
    LinearLayout backbtn;
    @Bind(R.id.continue_button_in_sign_up_activity)
    Button continuebtn;

    @Bind(R.id.firstname_registration)
    EditText Firstname_text;
    @Bind(R.id.lastname_registration)
    EditText Lastname_text;
    @Bind(R.id.email_registration)
    EditText Email_text;
    @Bind(R.id.mobile_no_registration)
    EditText MobileNo_text;
    @Bind(R.id.appartment)
    EditText apparment_text;
    public static AutoCompleteTextView HomeAddress_text;
    @Bind(R.id.password_registration)
    EditText Password_text;
    @Bind(R.id.confirm_Password_Registration)
    EditText ConfirmPassword_text;

    URLS urls = new URLS();
    public static SignUpActivity Signupactivity;

    String firstname, lastname, email, mobileno, homeaddress, password, confirmpassword , appartmentString;

    String UserId, FirstName, LastName, Email,PhoneNumber,HomeAddress,Password,Status , appartresponse;

    SignupSettergetter signupSettergetter = new SignupSettergetter();

    SessionManager sm;
    StringRequest sr;
    RequestQueue queue;

    Pattern letter = Pattern.compile("[a-zA-Z]");
    Pattern digit = Pattern.compile("[0-9]");
    Pattern special = Pattern.compile ("[!@#$%&*()_+=|<>?{}\\[\\]~-]");
    Pattern eight = Pattern.compile (".{8}");
    public static ProgressDialog pDialog;
    String pincode;
    String placeid = "" ;
    public static String lat;
    public static String lng;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);

        sm = new SessionManager(SignUpActivity.this);
        queue = VolleySingleton.getInstance(SignUpActivity.this).getRequestQueue();
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(true);

        Intent intent = getIntent();
        pincode = intent.getStringExtra("PINCODE");

        HomeAddress_text = (AutoCompleteTextView)findViewById(R.id.address_registration);
        Signupactivity = SignUpActivity.this;
        settinglistenersonbutton();
        continuebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getvaluesfromedittext();
            }
        });

        HomeAddress_text.setThreshold(1);
        HomeAddress_text.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                parsingforoffers.parsing(SignUpActivity.this, HomeAddress_text.getText().toString().trim(), "Signup");

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

        HomeAddress_text.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                placeid = parsingforoffers.placeid.get(position);
                GetLatLong(parsingforoffers.placeid.get(position));

            }
        });




    }

    private void generatesecreenmessage(String s) {
        Toast.makeText(SignUpActivity.this, "" + s, Toast.LENGTH_SHORT).show();
    }

    public void getvaluesfromedittext() {
        firstname = Firstname_text.getText().toString();
        lastname = Lastname_text.getText().toString();
        email = Email_text.getText().toString();
        mobileno = MobileNo_text.getText().toString();
        homeaddress = HomeAddress_text.getText().toString();
        password = Password_text.getText().toString();
        confirmpassword = ConfirmPassword_text.getText().toString();
        appartmentString = apparment_text.getText().toString();

        Log.e("place iddd",""+placeid);

        if (firstname.equals("") || lastname.equals("") || email.equals("") || mobileno.equals("") || password.equals("") ||
                homeaddress.equals("")) {

            Toaster.generatemessage(SignUpActivity.this,"Please fill in all details");

        }else if (placeid.equals("")){

            Toaster.generatemessage(SignUpActivity.this,"Please Select Address");
        }


        else{
            checkNetworkState();
        }

}
    private final TextWatcher suburbWatcher = new TextWatcher() {

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            //Toast.makeText(getApplicationContext(),"beforetextchange chala",Toast.LENGTH_SHORT).show();

        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // suburblistview.setVisibility(View.VISIBLE);
            // Toast.makeText(getApplicationContext(),"ontextchange chala",Toast.LENGTH_SHORT).show();


        }

        public void afterTextChanged(Editable s) {
            //   Toast.makeText(getApplicationContext(),"aftertextchange chala",Toast.LENGTH_SHORT).show();
        }
    };

    private boolean checkNetworkState() {
        if(new NetworkChecker().isNetworkConnected(SignUpActivity.this)){
            checkEmailiscorrect(email);

            return true;
        }else
            generatesecreenmessage("your internet is not connected");
        //pDialog.dismiss();

        return false;
    }

    private void checkEmailiscorrect(String email) {
        if (new EmailChecker().isEmailIsCorrect(email)) {

            checkPasswordiscorrect(password, confirmpassword);
        } else {
            generatesecreenmessage("Please Enter Valid Email");

        }
    }
    private void checkPasswordiscorrect(String pass , String conpass) {
        if(!pass.equals(conpass)){
            generatesecreenmessage("Password Does Not Match");
        }else {
            passwordvalidation(password);

        }
    }
    public void passwordvalidation(String password) {

        Matcher hasLetter = letter.matcher(password);
        Matcher hasDigit = digit.matcher(password);

        if (!hasLetter.find()){
            generatesecreenmessage("Password must be a least 6 characters with a combination of letters and numbers");
        }
        else if (!hasDigit.find()){
            generatesecreenmessage("Password must be a least 6 characters with a combination of letters and numbers");
        }
        else if(password.length() < 6) {
            generatesecreenmessage("Password must be a least 6 characters with a combination of letters and numbers");
        }
        else if (password.length() > 15){
            generatesecreenmessage("your password is valid for maximum fifteen characters");
        }
        else {

            try {
                pDialog.show();
                signup();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public void signup(){

//    String signupurl =  urls.signupurl + firstname+"&lname="+lastname+"&email="+email+"&phone_number="+mobileno+
//        "&home_address="+homeaddress+"&password="+password+"&pincode="+pincode;

        String signupurl=URLS.Register_Url.concat(firstname).concat(URLS.Register_Url1).concat(lastname).concat(URLS.Register_Url2)
                .concat(email).concat(URLS.Register_Url3).concat(mobileno).concat(URLS.Register_Url4).concat(homeaddress).concat(URLS.Register_Url5)
                .concat(password).concat(URLS.Register_Url6).concat(pincode).concat(URLS.Register_Url7).concat(lat).concat(URLS.Register_Url8).concat(lng)
                .concat(URLS.Register_Url9).concat(appartmentString);


        signupurl = signupurl.replaceAll(" ", "%20");
        signupurl = signupurl.replaceAll(",","");
        Log.e("signupurl",""+signupurl);
        StringRequest sr = new StringRequest(Request.Method.POST,signupurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("adduserSucess","" + response);
//                Toast.makeText(SignUpActivity.this , ""+response ,Toast.LENGTH_SHORT).show();
                GsonBuilder gsonBuilder = new GsonBuilder();
                final Gson gson = gsonBuilder.create();

                pDialog.dismiss();
                signupSettergetter= gson.fromJson(response, SignupSettergetter.class);
                if (signupSettergetter.result==1){

                    UserId = signupSettergetter.logininnerdata.user_id;
                    FirstName= signupSettergetter.logininnerdata.fname;
                    Email= signupSettergetter.logininnerdata.email;
                    HomeAddress= signupSettergetter.logininnerdata.home_address;
                    LastName= signupSettergetter.logininnerdata.lname;
                    PhoneNumber= signupSettergetter.logininnerdata.phone_number;
                    Password= signupSettergetter.logininnerdata.password;
                    Status= signupSettergetter.logininnerdata.status;
                    appartresponse = signupSettergetter.logininnerdata.appartment ;
                    Toast.makeText(SignUpActivity.this,"You have registered sucessfully",Toast.LENGTH_SHORT).show();

                    sm.createLoginSession(UserId, FirstName, LastName, Email, PhoneNumber, homeaddress, Password);
                    sm.SaveLatlong(signupSettergetter.logininnerdata.latitude , signupSettergetter.logininnerdata.longitude ,appartresponse);
                    Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }

                else {
                    Toast.makeText(SignUpActivity.this,""+signupSettergetter.msg ,Toast.LENGTH_SHORT).show();

                   }
                }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("addusererror", "" + error);
                if (error instanceof NetworkError){
                    Toast.makeText(Signupactivity, "No Internet !!", Toast.LENGTH_SHORT).show();
                }else if (error instanceof NoConnectionError){
                    Toast.makeText(Signupactivity, "No Internet", Toast.LENGTH_SHORT).show();
                }else if (error instanceof TimeoutError){
                    Toast.makeText(Signupactivity, "Plz Try Again !!", Toast.LENGTH_SHORT).show();
                }
            pDialog.dismiss();
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
        queue.add(sr);
    }


    private void settinglistenersonbutton() {

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        continuebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpActivity.this, AccountConfirmationActivity.class));
            }
        });
    }
//    private void showAddressSelectorDialoge() {
//        MaterialDialog dialog = new MaterialDialog.Builder(this)
//                .title("Your Address")
//                .customView(R.layout.address_dialoge_with_map, true)
//                .positiveText("Save")
//                .negativeText(android.R.string.cancel)
//                .callback(new MaterialDialog.ButtonCallback() {
//                    @Override
//                    public void onPositive(MaterialDialog dialog) {
//                        Toaster.generatemessage(SignUpActivity.this , "Address saved");
//                    }
//                    @Override
//                    public void onNegative(MaterialDialog dialog) {
//                    }
//                }).build();
//        dialog.show();
//
//    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        pDialog.dismiss();
    }


    public void GetLatLong(String placeid){


        String locationurl2 = "https://maps.googleapis.com/maps/api/place/details/json?placeid=" + placeid + "&key=AIzaSyDA4Yw4dv5X-OJWph49AZsvjT3cIFvBg9k";
        locationurl2 = locationurl2.replace(" ", "%20");
        Log.e("url", "" + locationurl2);

        pDialog = new ProgressDialog(Signupactivity);
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