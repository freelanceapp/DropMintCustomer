package com.apporio.johnlaundry.AccountModule;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.apporio.johnlaundry.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import com.apporio.johnlaundry.utils.Toaster;

public class AccountConfirmationActivity extends Activity {

    @Bind(R.id.back_button_on_action_bar) LinearLayout backbtn;
    @Bind(R.id.create_account_btn_in_accountConfirmationActivity)Button createaccountbtn;
    @Bind(R.id.password_in_confirmation_activity)TextView passedt;
    @Bind(R.id.confirmation_password_in_confirmation_activity)TextView confirmpassword;


    Activity AccountConfirmationActivity ;
    TextView activityname ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_confirmation);
        ButterKnife.bind(this);
        AccountConfirmationActivity = this ;

        activityname = (TextView) findViewById(R.id.activity_name_on_Action_bar);
        activityname.setText("Confirm your email");

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        createaccountbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(checkpassword()){
                    HelloFacebookSampleActivity.HelloFacebookActiviy.finish();
                    SignUpActivity.SignUpActivity.finish();
                    finish();
                }else {
                    Toaster.generatemessage(AccountConfirmationActivity.this , "Password doesn't match");
                }


            }
        });
    }

    private boolean checkpassword() {
        boolean check ;
        if(passedt.getText().toString().equals(confirmpassword.getText().toString())){
            check = true ;
        }else {
            check = false;
        }
        return check ;
    }

}
