package com.apporio.johnlaundry.MenuModule;

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
import com.apporio.johnlaundry.settergetter.parsingforoffers;
import com.apporio.johnlaundry.settergetter.ridedestsettergetter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.apporio.johnlaundry.R;
import com.apporio.johnlaundry.settergetter.Edit_profile_setter_getter;
import com.apporio.johnlaundry.settergetter.parcingOfferssettiings;
import com.apporio.johnlaundry.activity.MainActivity;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.Bind;
import butterknife.ButterKnife;
import com.apporio.johnlaundry.utils.NetworkChecker;
import com.apporio.johnlaundry.utils.SessionManager;
import com.apporio.johnlaundry.utils.URLS;
import com.apporio.johnlaundry.utils.VolleySingleton;

public class SettingsActivity extends Activity {

    @Bind(R.id.mobile_no_settings)EditText change_mobile_edt;
    public static AutoCompleteTextView HomeAddress_text;
    @Bind(R.id.password_settings)EditText change_password_edt;
    @Bind(R.id.confirm_Password_settings)EditText confirm_password_edt;
    @Bind(R.id.appartment_no_settings)EditText appart_number_edt;


    @Bind(R.id.back_button_on_action_bar)LinearLayout backbtn;
    @Bind(R.id.save_button_in_setting_activity)Button savebtn;

    TextView activityname ;
    String mobileno, homeaddress, password, confirmpassword , appartString;

    SessionManager sm;
    StringRequest sr;
    RequestQueue queue;

    String userid,address,phone_number ;
    Pattern letter = Pattern.compile("[a-zA-Z]");
    Pattern digit = Pattern.compile("[0-9]");
    Pattern special = Pattern.compile ("[!@#$%&*()_+=|<>?{}\\[\\]~-]");
    Pattern eight = Pattern.compile (".{8}");
    public static ProgressDialog pDialog;
    public static String lat;
    public static String lng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);

        activityname = (TextView)findViewById(R.id.activity_name_on_Action_bar);
        activityname.setText("Settings");

        sm = new SessionManager(SettingsActivity.this);
        queue = VolleySingleton.getInstance(SettingsActivity.this).getRequestQueue();

        HomeAddress_text = (AutoCompleteTextView)findViewById(R.id.address_settings);

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);

        HashMap<String, String> user = sm.getUserDetails();

        // get name
        userid = user.get(SessionManager.KEY_UserId);
        address = user.get(SessionManager.KEY_Address);
        appart_number_edt.setText(user.get(SessionManager.KEY_Appartment));
        phone_number =user.get(SessionManager.KEY_Phone);

        HomeAddress_text.setText(address);
        change_mobile_edt.setText(phone_number);
//        Toast.makeText(getApplicationContext(), "" + userid, Toast.LENGTH_SHORT).show();

        HashMap<String,String> latlng = sm.getLatLng();
        lat= latlng.get(SessionManager.KEY_Lat);
        lng = latlng.get(SessionManager.KEY_Long);


        setlistenersonviews();

        HomeAddress_text.setThreshold(1);
        HomeAddress_text.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
              //  parcingOfferssettiings.parsing(SettingsActivity.this, HomeAddress_text.getText().toString().trim());
                parsingforoffers.parsing(SettingsActivity.this, HomeAddress_text.getText().toString().trim(), "Settings");

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
               // placeid = parsingforoffers.placeid.get(position);
                GetLatLong(parsingforoffers.placeid.get(position));

            }
        });


    }

    private void setlistenersonviews() {
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getvaluesfromedittext();

            }
        });

    }

    private void generatesecreenmessage(String s) {
        Toast.makeText(SettingsActivity.this, "" + s, Toast.LENGTH_SHORT).show();
    }

    public void getvaluesfromedittext() {

        mobileno = change_mobile_edt.getText().toString();
        homeaddress = HomeAddress_text.getText().toString();
        homeaddress = homeaddress.replaceAll(" ", "%20");
        password = change_password_edt.getText().toString();
        confirmpassword = confirm_password_edt.getText().toString();
        appartString = appart_number_edt.getText().toString() ;
        if (mobileno.equals("") || password.equals("")) {


            Toast.makeText(SettingsActivity.this, "Please fill the details", Toast.LENGTH_SHORT).show();

        }else{
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
        if(new NetworkChecker().isNetworkConnected(SettingsActivity.this)){
            checkPasswordiscorrect(password,confirmpassword);

            return true;
        }else
            generatesecreenmessage("your internet is not connected");
        //pDialog.dismiss();

        return false;
    }
    private void checkPasswordiscorrect(String pass , String conpass) {
        if(!pass.equals(conpass)){
            generatesecreenmessage("Password Not Match");
        }else {
            passwordvalidation(password);

        }
    }
    public void passwordvalidation(String password) {

        Matcher hasLetter = letter.matcher(password);
        Matcher hasDigit = digit.matcher(password);

        if (!hasLetter.find()){
            generatesecreenmessage("your password requires atleast one alphabet");
        }
        else if (!hasDigit.find()){
            generatesecreenmessage("your password requires atleast one number");
        }
        else if(password.length() < 6) {
            generatesecreenmessage("your password requires minimum six characters");
        }
        else if (password.length() > 15){
            generatesecreenmessage("your password is valid for maximum fifteen characters");
        }
        else {

            if (!sm.checkLogin()) {
                try {
                  //  pDialog.show();
                    edit_profile();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void edit_profile(){

//        String signupurl =  "http://keshavgoyal.com/laundry_app1/api/edit_profile.php?user_id="+userid +"&phone_number="+mobileno+
//                "&home_address="+homeaddress+"&password="+password;

        String signupurl= URLS.signupurl.concat(userid).concat(URLS.signupurl1).concat(mobileno).concat(URLS.signupurl2).concat(homeaddress)
                .concat(URLS.signupurl3).concat(password).concat(URLS.signupurl4).concat(lat).concat(URLS.signupurl5).concat(lng)
                .concat(URLS.signupurl6).concat(appartString);

        Log.e("url",""+signupurl);
        sr = new StringRequest(Request.Method.POST,signupurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("adduserSucess", "" + response);

                GsonBuilder gsonBuilder = new GsonBuilder();
                final Gson gson = gsonBuilder.create();
                pDialog.dismiss();

                Edit_profile_setter_getter edit_profile_setter_getter = new Edit_profile_setter_getter();
                edit_profile_setter_getter = gson.fromJson(response, Edit_profile_setter_getter.class);
                if (edit_profile_setter_getter.getResult()==1){

                    Toast.makeText(SettingsActivity.this, "Changed Successfully", Toast.LENGTH_SHORT).show();
                    sm.SaveLatlong(edit_profile_setter_getter.getUser_details().getLatitude() , edit_profile_setter_getter.getUser_details().getLongitude() , appartString);
                    sm.changefirstname(edit_profile_setter_getter.getUser_details().getHome_address(),edit_profile_setter_getter.getUser_details().getPhone_number());

                    Intent ii = new Intent(SettingsActivity.this , MainActivity.class);
                    ii.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(ii);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("addusererror", "" + error);
                Toast.makeText(SettingsActivity.this, ""+error, Toast.LENGTH_SHORT).show();
            pDialog.dismiss();

                if (error instanceof NetworkError){
                    Toast.makeText(SettingsActivity.this, "No Internet !!", Toast.LENGTH_SHORT).show();
                }else if (error instanceof NoConnectionError){
                    Toast.makeText(SettingsActivity.this, "No Internet", Toast.LENGTH_SHORT).show();
                }else if (error instanceof TimeoutError){
                    Toast.makeText(SettingsActivity.this, "Plz Try Again !!", Toast.LENGTH_SHORT).show();
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
        pDialog.show();
        queue.add(sr);
    }


    public void GetLatLong(String placeid){


        String locationurl2 = "https://maps.googleapis.com/maps/api/place/details/json?placeid=" + placeid + "&key=AIzaSyCC3Ci--XByh-o-ukFw0IBOGD1of7hglA4";
        locationurl2 = locationurl2.replace(" ", "%20");
        Log.e("url", "" + locationurl2);

        pDialog = new ProgressDialog(SettingsActivity.this);
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