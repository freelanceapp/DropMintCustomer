package com.apporio.johnlaundry.activity;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
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
import com.apporio.johnlaundry.Parsing_Files.Parsing_Add_Device_Id;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.apporio.johnlaundry.BasketModule.BasketActivity;
import com.apporio.johnlaundry.MenuModule.MenuActivity;
import com.apporio.johnlaundry.R;
import com.apporio.johnlaundry.settergetter.CATEGORY_SETTER_GETTER;
import com.apporio.johnlaundry.settergetter.Category_InnerData;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import com.apporio.johnlaundry.database.DBManager;
import de.greenrobot.event.EventBus;
import com.apporio.johnlaundry.events.CartEvent;
import com.apporio.johnlaundry.fragment.FragmentList;
import com.apporio.johnlaundry.utils.ActivityDetector;
import com.apporio.johnlaundry.utils.SessionManager;
import com.apporio.johnlaundry.utils.URLS;
import com.apporio.johnlaundry.utils.VolleySingleton;

public class MainActivity extends FragmentActivity {

//    public static ImageView myaccount;
//    @Bind(R.id.pricelist_activity_btn) ImageView pricelist;
    @Bind(R.id.setting_btn_in_main_Activity_with_icon) ImageView settings;
    @Bind(R.id.setting_btn_in_main_Activity_with_icon2) ImageView settings2;
    @Bind(R.id.total_item) TextView total_item;
 //   public static ViewPager pager;
    @Bind(R.id.gross_total) TextView gross_total;

    public static View cartlayot , instantlayout ;
    @Bind(R.id.cartimage)ImageView cartimage;

//    public static TextView logoutuser;
    public String[] tittles; //= {"Offers" , "Wash & Press" ,"Press"};
    public String[] categories_idarray;
    public String[] image ;
    public static MainActivity activity;

    List<Category_InnerData> category_innerData_list = new ArrayList<>();
    ArrayList<String> categorynamelist = new ArrayList<>();
    public static ArrayList<String> categoryidlist = new ArrayList<>();
    ArrayList<String> imagelist = new ArrayList<>();

    static DBManager dbm;
    String userid,deviceid;
    public static String cat;
    StringRequest sr;
    RequestQueue queue;

    SessionManager sm;
    int status;
    public static Activity mainActivitywithicon ;
    private EventBus  bus = EventBus.getDefault();

    CATEGORY_SETTER_GETTER category_setter_getter = new CATEGORY_SETTER_GETTER();

    private TabLayout tabLayout;
    private ViewPager viewPager;
    public String[] cat_titles;
    LinearLayout instant_click ;
    public static ProgressDialog pDialog;
    DecimalFormat df;
    public static Animation animShow, animHide;
    ActivityDetector activityDetector = new ActivityDetector();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_icons);
        df = new DecimalFormat("#0.00");
        ButterKnife.bind(this);
        bus.register(this);
        mainActivitywithicon = this ;
        dbm = new DBManager(MainActivity.this );
        dbm.clearCartTable();

        activity=MainActivity.this;
        ActivityDetector.open_MainActivity=true;

        cartlayot = (View)findViewById(R.id.cartlayot);
        instantlayout = (View)findViewById(R.id.instantlayot);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        cartlayot.setVisibility(View.GONE);//*************
        sm = new SessionManager(MainActivity.this);
        instantlayout.setVisibility(View.VISIBLE);
        pDialog = new ProgressDialog(MainActivity.this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);

        animShow = AnimationUtils.loadAnimation( this, R.anim.view_show);
        animHide = AnimationUtils.loadAnimation( this, R.anim.view_hide);

        queue = VolleySingleton.getInstance(MainActivity.this).getRequestQueue();
        if(!sm.checkLogin()) {

            HashMap<String, String> user = sm.getUserDetails();
            HashMap<String, String> DeviceID = sm.getDeviceId();
            // get name
            userid = user.get(SessionManager.KEY_UserId);
           deviceid= DeviceID.get(SessionManager.KEY_DeviceID);
        }

        Parsing_Add_Device_Id.Add_Device_ID(MainActivity.this,userid,"2",deviceid);

        status=0;
       // doanimationoflogo();

        viewcategory();
  //      myaccount = (ImageView)findViewById(R.id.my_account_activity_btn);
//        logoutuser = (TextView)findViewById(R.id.logout_usr);
//        logoutuser.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                if (AccessToken.getCurrentAccessToken() != null){
//                    LoginManager.getInstance().logOut();
//                    sm.logoutUser();
//                    startActivity(new Intent(MainActivity.this,HelloFacebookSampleActivity.class));
//                    finish();
//                }else{
//                    sm.logoutUser();
//                    startActivity(new Intent(MainActivity.this, HelloFacebookSampleActivity.class));
//                    finish();
//
//                }
//            }
//        });

//        if (sm.isLoggedIn(false)){
//
//            logoutuser.setVisibility(View.VISIBLE);
//
//
//        }
//        else if (AccessToken.getCurrentAccessToken() != null){
//
//            logoutuser.setVisibility(View.VISIBLE);
//
//            LoginManager.getInstance().logOut();
//
//        }
//        else{
//
//
//        }

//        pricelist.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(MainActivity.this, PriceList.class));
//            }
//        });
//        myaccount.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                if (!ConstansApplication.ISLOGINWITH.equals("")) {
//                    startActivity(new Intent(MainActivity.this, MainActivity.class));
//                } else {
//                    startActivity(new Intent(MainActivity.this, HelloFacebookSampleActivity.class));
//                }
//            }
//        });

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, MenuActivity.class));
            }
        });

        settings2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, MenuActivity.class));
            }
        });


        cartimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sm.checkLogin()) {
                    Toast.makeText(MainActivity.this, "Please Login to check cart", Toast.LENGTH_SHORT).show();
                } else {
                    if (total_item.getText().equals("0")) {
                        Toast.makeText(MainActivity.this, "Please select an item", Toast.LENGTH_SHORT).show();
                    } else {
                        Intent i = new Intent(MainActivity.this , BasketActivity.class);
                        i.putExtra("FORM" , "1");
                        startActivity(i);
                    }
                }
            }
        });

//        findViewById(R.id.logo_on_action_bar).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//               // doanimationoflogo();
//            }
//        });

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setTabTextColors(Color.WHITE,Color.LTGRAY);


//        tabs.setBackgroundColor(Color.parseColor("#28000000"));
//        tabs.setIndicatorHeight(5);
//        tabs.setIndicatorColor(Color.WHITE);
//        tabs.setTextColor(Color.WHITE);
//        tabs.setDividerColor(Color.WHITE);


        instant_click = (LinearLayout)findViewById(R.id.instant_order);
        instant_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              showInstantDialogue();
            }
        });




    }

    public class MyPagerAdapter extends FragmentPagerAdapter {

        String[] TITLES; //= {"Offers", "Laundry","Ironing","Suits","Shirts","Trousers","Dresses/Skirts","Outerwear",
        //                "Accessories","Household"};
        String [] categoryid;

        public MyPagerAdapter(FragmentManager fm,String[] tittles, String [] categoryid) {
            super(fm);

            this.TITLES =tittles;

            this.categoryid =categoryid;
        }


        @Override
        public CharSequence getPageTitle(int position) {
            return TITLES[position];
        }

        @Override
        public int getCount() {
            return TITLES.length;
        }

        @Override
        public Fragment getItem(int num) {

            cat = categoryid[num];
//            switch (position) {
//
//                case 0:
//                    return TICKETS_PURCHASED.newInstance("SecondFragment, Instance 1");
//                case 1:
//                    return SOCIALITE_MEMBERS.newInstance("FirstFragment, Instance 1");
//
//                default:
//                    return TICKETS_PURCHASED.newInstance("SecondFragment, Instance 1");
//
//            }
            return FragmentList.newInstance(MainActivity.this,categoryid[num]);
        }

    }

    //////////////////////////  BS EVENT ////////////////////////////////
    public void onEvent(CartEvent  event){
        if(Integer.parseInt(event.getDatatotalitems()) == 0 ){
            cartlayot.setVisibility(View.VISIBLE);
        }else{
            cartlayot.setVisibility(View.VISIBLE);
        }
        double dd= Double.parseDouble(event.getDatagrosstotal());
        total_item.setText("" + event.getDatatotalitems());
        gross_total.setText(""+df.format(dd));

    }

    @Override
    protected void onResume() {
        super.onResume();
      //  AppEventsLogger.activateApp(this);
    }


    @Override
    protected void onDestroy() {
        bus.unregister(this);
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
     //   AppEventsLogger.deactivateApp(this);
    }



//    private void doanimationoflogo() {
//        YoYo.with(Techniques.Tada)
//                .duration(3000)
//                .playOn(findViewById(R.id.logo_on_action_bar));
//
//    }

//    private class ViewPagerAdapter extends FragmentStatePagerAdapter {
//
//        String [] tittles ;
//        String [] categoryid;
//
//
//        public ViewPagerAdapter(FragmentManager fm , String [] tittles, String [] categoryid) {
//            super(fm);
//            this.tittles =tittles ;
//
//            this.categoryid =categoryid;
//        }
//
//        public Fragment getItem(int num) {
//
//            cat = categoryid[num];
//            //   Toast.makeText(MainActivity.this,"cat"+cat,Toast.LENGTH_SHORT).show();
//            return FragmentList.newInstance(MainActivity.this,categoryid[num]);
//        }
//
//        @Override
//        public int getCount() {
//            return tittles.length;
//        }
//
//        @Override
//        public CharSequence getPageTitle(int position) {
//            return tittles[position];
//        }
//
//    }



    public void viewcategory() {

      //  String viewcategoryurl  = "http://keshavgoyal.com/laundry_app1/api/view_category.php";

        String viewcategoryurl  = URLS.viewcategoryurl;
        viewcategoryurl=viewcategoryurl.replace(" ","%20");

        sr = new StringRequest(Request.Method.POST, viewcategoryurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                pDialog.dismiss();

                Log.e("Sucess", "" + response);
                //  Toast.makeText(LoginCleanline.this , ""+response ,Toast.LENGTH_SHORT).show();

                GsonBuilder gsonBuilder = new GsonBuilder();
                final Gson gson = gsonBuilder.create();

                categoryidlist.clear();
                categorynamelist.clear();
                imagelist.clear();

                category_setter_getter = gson.fromJson(response, CATEGORY_SETTER_GETTER.class);
                if (category_setter_getter.result.equals("1")) {
                    category_innerData_list = category_setter_getter.category_innerDatas;

                    for (int i = 0; i < category_innerData_list.size(); i++) {
                        categoryidlist.add(category_innerData_list.get(i).category_id);
                        imagelist.add(category_innerData_list.get(i).category_icon);
                        String cname =  category_innerData_list.get(i).category_name;
                        cname = cname.replaceAll("&amp;", "&");
                        categorynamelist.add(cname);



                        //     Toast.makeText(getApplicationContext(), "" + UserId, Toast.LENGTH_SHORT).show();
                        //     Toast.makeText(MainActivity.this, ""+categoryidlist.get(i), Toast.LENGTH_SHORT).show();
                    }

                    tittles = new String[category_innerData_list.size()];
                    categories_idarray = new String[category_innerData_list.size()];
                    image = new String[category_innerData_list.size()];

                    categories_idarray = categoryidlist.toArray(categories_idarray);
                    tittles = categorynamelist.toArray(tittles);
                    image = imagelist.toArray(image);


//                    Toast.makeText(MainActivity.this, ""+ tittles[i], Toast.LENGTH_SHORT).show();

                    //   sm.createproductRecord(categories_idarray[i]);

//                    pager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(), tittles, categories_idarray));
//                    tabs.setViewPager(pager);
                    viewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager(), tittles, categories_idarray));
                    tabLayout.setupWithViewPager(viewPager);
                  //  setupTabIcons();


//                    pager.setAdapter(new ViewPagerAdapterimage(getSupportFragmentManager(),categories_idarray,image));


                } else {
                    Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                    //pDialog.dismiss();


                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pDialog.dismiss();
                Log.e("Sucess", "" + error.toString());
                if (error instanceof NetworkError){
                    Toast.makeText(MainActivity.this, "No Internet !!", Toast.LENGTH_SHORT).show();
                }else if (error instanceof NoConnectionError){
                    Toast.makeText(MainActivity.this, "No Internet", Toast.LENGTH_SHORT).show();
                }else if (error instanceof TimeoutError){
                    Toast.makeText(MainActivity.this, "Plz Try Again !!", Toast.LENGTH_SHORT).show();
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                //params.put("email", gettingemail);
                //params.put("password", gettingpassword);
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }
        };
        sr.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
          pDialog.show();
        queue.add(sr);
    }
//    private void showDialogForGPS() {
//        MaterialDialog dialog = new MaterialDialog.Builder(this)
//                .title("Location service if off ")
//                .content("Please activate location services to use this feature")
//                .positiveText("Settings")
//                .negativeText(android.R.string.cancel)
//                .callback(new MaterialDialog.ButtonCallback() {
//                    @Override
//                    public void onPositive(MaterialDialog dialog) {
//                        Intent intent= new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
//                        startActivity(intent);
//                    }
//
//                    @Override
//                    public void onNegative(MaterialDialog dialog) {
//                        dialog.dismiss();
//                    }
//                }).build();
//        dialog.show();
//    }



    public void showInstantDialogue(){

        final Dialog dialog = new Dialog(MainActivity.this,android.R.style.Theme_Translucent_NoTitleBar);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Window window=dialog.getWindow();
        dialog.setCancelable(false);
        window.setGravity(Gravity.CENTER);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialogue_instant_order);
        TextView ok = (TextView) dialog.findViewById(R.id.ok_txt);
        TextView cncl = (TextView)dialog.findViewById(R.id.cancel_txt);
        ok.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent i = new Intent(MainActivity.this , BasketActivity.class);
                i.putExtra("FORM" , "2");
                startActivity(i);
                dialog.dismiss();

            }
        });
        cncl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }


}