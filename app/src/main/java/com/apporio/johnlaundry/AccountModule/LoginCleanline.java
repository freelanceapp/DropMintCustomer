package com.apporio.johnlaundry.AccountModule;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.facebook.FacebookSdk;
import com.apporio.johnlaundry.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LoginCleanline extends Activity {

    @Bind(R.id.sign_up_buttn_in_login_activity) Button signupbtn;
    @Bind(R.id.back_button_on_action_bar) LinearLayout backbtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        FacebookSdk.sdkInitialize(getApplicationContext());

        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginCleanline.this, SignUpActivity.class));
            }
        });
          backbtn.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  finish();
              }
          });
    }


}
