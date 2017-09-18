package com.apporio.johnlaundry.AccountModule;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.apporio.johnlaundry.R;

import butterknife.Bind;
import butterknife.ButterKnife;


public class AccountActivity extends Activity {
    @Bind(R.id.back_button_in_account_activity)LinearLayout backbtn;
    @Bind(R.id.create_account_btn_layout_my_account_activity)LinearLayout createbtn;
    @Bind(R.id.sign_in_btn_layout_my_account_activity)LinearLayout signbtn;
//    @Bind(R.id.contact_us_btn_layout_my_account_activity)LinearLayout contactusbtn;
//    @Bind(R.id.tips_btn_layout_my_account_activity)LinearLayout tipsbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        ButterKnife.bind(this);

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        createbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        signbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

//        contactusbtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
//
//        tipsbtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
    }
}