package com.apporio.johnlaundry.AccountModule;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.apporio.johnlaundry.R;
import com.apporio.johnlaundry.settergetter.CHANGEFORGOTPASSWORDSETTERGETTER;

import java.util.regex.Pattern;

public class Edit_password_from_ForgotPassword extends Activity {

    EditText CODE,NEWPASSWORD,CONFIRMPASSWORD;
    String code,newpassword,confirmpassword;
    Button requestchangepaswrd;

    Pattern letter = Pattern.compile("[a-zA-z]");
    Pattern digit = Pattern.compile("[0-9]");
    Pattern special = Pattern.compile ("[!@#$%&*()_+=|<>?{}\\[\\]~-]");
    Pattern eight = Pattern.compile(".{8}");

    public static CHANGEFORGOTPASSWORDSETTERGETTER changeforgotpasswordsettergetter= new CHANGEFORGOTPASSWORDSETTERGETTER();
    RequestQueue queue;
    StringRequest sr;
    public static String state;
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_password_from__forgot_password);


    }

}
