package com.apporio.johnlaundry.IntroModule;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.apporio.johnlaundry.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class HowItWorks extends Activity {

    @Bind(R.id.back_button_on_action_bar)LinearLayout backbtn;
    public static Activity howitworksactivity ;
    TextView ACTIVITYNAME ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_how_it_works);
        ButterKnife.bind(this);
        howitworksactivity = this ;
        ACTIVITYNAME  = (TextView) findViewById(R.id.activity_name_on_Action_bar);
        ACTIVITYNAME.setText("How it works");

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
