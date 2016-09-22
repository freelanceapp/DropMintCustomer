package com.apporio.johnlaundry.MenuModule;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.apporio.johnlaundry.R;
import com.apporio.johnlaundry.settergetter.Edit_profile_setter_getter;
import com.apporio.johnlaundry.settergetter.parcingOfferssettiings;
import com.apporio.johnlaundry.startUpScreen.MainActivityWithicon;

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

    @Bind(R.id.back_button_on_action_bar)LinearLayout backbtn;
    @Bind(R.id.save_button_in_setting_activity)Button savebtn;

    TextView activityname ;
    String mobileno, homeaddress, password, confirmpassword;

    SessionManager sm;
    StringRequest sr;
    RequestQueue queue;

    String userid,address,phone_number;
    Pattern letter = Pattern.compile("[a-zA-Z]");
    Pattern digit = Pattern.compile("[0-9]");
    Pattern special = Pattern.compile ("[!@#$%&*()_+=|<>?{}\\[\\]~-]");
    Pattern eight = Pattern.compile (".{8}");
    public static ProgressDialog pDialog;

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

        phone_number =user.get(SessionManager.KEY_Phone);

        HomeAddress_text.setText(address);
        change_mobile_edt.setText(phone_number);
//        Toast.makeText(getApplicationContext(), "" + userid, Toast.LENGTH_SHORT).show();



        setlistenersonviews();

        HomeAddress_text.setThreshold(1);
        HomeAddress_text.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                parcingOfferssettiings.parsing(SettingsActivity.this, HomeAddress_text.getText().toString().trim());

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
                    pDialog.show();
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
                .concat(URLS.signupurl3).concat(password);
        signupurl=signupurl.replace(" ","%20");

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
                if (edit_profile_setter_getter.result==1){


                    startActivity(new Intent(SettingsActivity.this, MainActivityWithicon.class));
                    Toast.makeText(SettingsActivity.this, "Changed Successfully", Toast.LENGTH_SHORT).show();

                    sm.changefirstname(edit_profile_setter_getter.edit_profile_inner_setter_getter.home_address,mobileno);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("addusererror", "" + error);
                Toast.makeText(SettingsActivity.this, ""+error, Toast.LENGTH_SHORT).show();
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
}