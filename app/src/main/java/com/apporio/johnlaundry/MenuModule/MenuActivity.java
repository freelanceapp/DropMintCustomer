package com.apporio.johnlaundry.MenuModule;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.apporio.johnlaundry.startUpScreen.SplashActivity;
//import com.facebook.AccessToken;
//import com.facebook.login.LoginManager;
import com.apporio.johnlaundry.IntroModule.HowItWorks;
import com.apporio.johnlaundry.R;
import com.apporio.johnlaundry.ordersHistory.PreviousOrderActivity;
import com.apporio.johnlaundry.activity.MainActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import com.apporio.johnlaundry.utils.ActivityDetector;
import com.apporio.johnlaundry.utils.SessionManager;

public class MenuActivity extends Activity {

    @Bind(R.id.orderhistory_in_menu_activity)LinearLayout profilebtn;
    @Bind(R.id.settings_in_menu_activity)LinearLayout settingbtn;
    @Bind(R.id.back_button_on_action_bar)LinearLayout backtn ;
    LinearLayout signout;
    @Bind(R.id.orderhistorytext)TextView orderhistorytext ;
    @Bind(R.id.settingsview)View settingsview ;
    @Bind(R.id.pricetext) TextView pricelist;

    @Bind(R.id.privacy_policy)LinearLayout privacypolicy ;

    @Bind(R.id.how_it_works_in_menu_activity)LinearLayout howitworksbutton ;
    @Bind(R.id.faq_in_menu_activity)LinearLayout faqbutton ;
    @Bind(R.id.termsconditions)LinearLayout termsconditionsbutton ;
    @Bind(R.id.keepintouch)LinearLayout keepintouchbutton ;

    TextView ACTIVITYNAME ;
    public static Activity menuactivity ;
    SessionManager sm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        ButterKnife.bind(this);
        setListenersonbutton();

        ActivityDetector.open_SettingActivity=true;

        signout = (LinearLayout)findViewById(R.id.signout_from_menu);
        sm = new SessionManager(MenuActivity.this);

        menuactivity = this ;
        ACTIVITYNAME  = (TextView) findViewById(R.id.activity_name_on_Action_bar);
         ACTIVITYNAME.setText("Settings");

        if (sm.checkLogin()){
            settingbtn.setVisibility(View.GONE);
            orderhistorytext.setTextColor(Color.parseColor("#38000000"));
            profilebtn.setClickable(false);
            settingsview.setVisibility(View.GONE);
//            settingbtn.setClickable(false);
  //          settingstext.setTextColor(Color.parseColor("#38000000"));
            signout.setVisibility(View.GONE);

        }else
        {
            profilebtn.setClickable(true);
            signout.setVisibility(View.VISIBLE);
            settingsview.setVisibility(View.VISIBLE);
            settingbtn.setVisibility(View.VISIBLE);
            signout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    settingbtn.setVisibility(View.GONE);
                    orderhistorytext.setTextColor(Color.parseColor("#38000000"));
                    profilebtn.setClickable(false);
                    settingsview.setVisibility(View.GONE);
                    signout.setVisibility(View.GONE);

                  MainActivity.activity.finish();


//                    if (AccessToken.getCurrentAccessToken() != null){
//                        LoginManager.getInstance().logOut();
//                        sm.logoutUser();
//                        Intent i=new Intent(getApplicationContext(),SplashActivity.class);
//                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                        startActivity(i);
//                        finish();
//                    }else{
                        sm.logoutUser();
                        Intent i=new Intent(getApplicationContext(),SplashActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);

                        finish();

                    }

                //}
            });

        }
    }

    private void setListenersonbutton() {

        profilebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuActivity.this  , PreviousOrderActivity.class));
            }
        });


        settingbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuActivity.this, SettingsActivity.class));
            }
        });

        pricelist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuActivity.this, PriceList.class));
            }
        });

        backtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        faqbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuActivity.this, FAQ.class));
            }
        });
        termsconditionsbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuActivity.this,TermsAndConditions.class));
            }
        });
        howitworksbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuActivity.this, HowItWorks.class));
            }
        });

        keepintouchbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuActivity.this,KeepInTouch.class));
            }
        });

        privacypolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuActivity.this,Privacy_policy.class));
            }
        });
    }

}