package com.apporio.johnlaundry.startUpScreen;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.Toast;

import com.apporio.johnlaundry.AccountModule.HelloFacebookSampleActivity;
import com.apporio.johnlaundry.GCMclasses.GCMConstants;
import com.apporio.johnlaundry.R;
import com.apporio.johnlaundry.logger.Logger;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.io.IOException;
import java.util.HashMap;

import com.apporio.johnlaundry.utils.SessionManager;


public class SplashActivity extends Activity {
       private static int SPLASH_TIME_OUT = 3000;
    Animation animationSlideOutRight;

    ImageView textimage;
    ImageView laundry,app;
    SessionManager sm;
    String userid;
    ImageView first,second,third,forth;

    GoogleCloudMessaging gcmObj;
    public static String regId = "";
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

       // textimage = (ImageView)findViewById(R.id.splashimage);
        laundry = (ImageView)findViewById(R.id.laundrytxt);

//        animationSlideOutRight= AnimationUtils.loadAnimation(this, R.anim.abc_grow_fade_in_from_bottom);
  //      animationSlideOutRight.setDuration(1000);
    //    textimage.startAnimation(animationSlideOutRight);

        YoYo.with(Techniques.ZoomInUp)
                .duration(2500)
                .playOn(findViewById(R.id.laundrytxt));

//        YoYo.with(Techniques.DropOut)
//                .duration(2500)
//                .playOn(findViewById(R.id.first_image));
//
//        YoYo.with(Techniques.Landing)
//                .duration(2500)
//                .playOn(findViewById(R.id.second_image));
//
//        YoYo.with(Techniques.Tada)
//                .duration(2500)
//                .playOn(findViewById(R.id.third_image));
//
//        YoYo.with(Techniques.BounceIn)
//                .duration(2500)
//                .playOn(findViewById(R.id.forth_image));


// Load the animation like this
        Animation animSlide = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.slide_left);
        Animation animSlide1 = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.slide_right);
// Start the animation like this
       //laundry.startAnimation(animSlide1);
      //  app.startAnimation(animSlide);



//        RotateAnimation anim = new RotateAnimation(0f, 350f, 15f, 15f);
//        anim.setInterpolator(new LinearInterpolator());
//        anim.setRepeatCount(Animation.INFINITE);
//        anim.setDuration(2500);
//
//        laundry.startAnimation(anim);

        
        sm= new SessionManager(SplashActivity.this);
        // get user data from session
        HashMap<String, String> user = sm.getUserDetails();

        // get name
        userid = user.get(SessionManager.KEY_UserId);

        checkPlayServices();

        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                if (!userid.equals("")){
                    Intent i = new Intent(SplashActivity.this, MainActivityWithicon.class);
                    startActivity(i);

                }else {
                    Intent i = new Intent(SplashActivity.this, HelloFacebookSampleActivity.class);
                    startActivity(i);
                }
                // close this activity
                finish();
            }

        }, SPLASH_TIME_OUT);

    }
    //////////////////////////////////////////////////gcm code


    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil
                .isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Toast.makeText(SplashActivity.this, "This device doesn't support Play services, App will not get notifications", Toast.LENGTH_LONG).show();
                finish();
            }
            return false;
        } else {
            registerInBackground();
            //  Toast.makeText(SplashActivity.this, "This device supports Play services, App will work normally", Toast.LENGTH_LONG).show();
        }
        return true;
    }

    private void registerInBackground() {
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                String msg = "";
                try {
                    if (gcmObj == null) {
                        gcmObj = GoogleCloudMessaging.getInstance(SplashActivity.this);
                    }
                    regId = gcmObj.register(GCMConstants.GOOGLE_PROJ_ID);
                    msg = "Registration ID :" + regId;

                } catch (IOException ex) {
                    msg = "Error :" + ex.getMessage();
                }
                return msg;
            }

            @Override
            protected void onPostExecute(String msg) {
                if (!TextUtils.isEmpty(regId)) {
                    //       storeRegIdinSharedPref(applicationContext, regId, emailID);
                   Logger.e("Registration id on SPLASH SCREEN " + regId, "");

                    HashMap<String, String> DeviceID = sm.getDeviceId();

                    Log.e("id in sf", "" + sm.getDeviceId());

                     if (DeviceID.get(SessionManager.KEY_DeviceID).equals(regId)){

                        }else {
                            sm.saveDeviceId(regId);

                            Log.e("",""+sm.getDeviceId());
                        }





                } else {
                    Toast.makeText(SplashActivity.this, "Reg ID Creation Failed.Either you haven't enabled Internet or GCM server is busy right now. Make sure you enabled Internet and try registering again after some time." + msg, Toast.LENGTH_LONG).show();
                }
            }
        }.execute(null, null, null);
    }



}
