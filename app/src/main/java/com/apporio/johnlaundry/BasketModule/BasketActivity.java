package com.apporio.johnlaundry.BasketModule;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PersistableBundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Spinner;
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
import com.apporio.johnlaundry.settergetter.PriceOfCategory;
import com.apporio.johnlaundry.settergetter.RushDeliveryCharge;
import com.apporio.johnlaundry.settergetter.ridedestsettergetter;
import com.apporio.johnlaundry.settergetter.time_slot_result;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.apporio.johnlaundry.AccountModule.HelloFacebookSampleActivity;
import com.apporio.johnlaundry.R;
import com.apporio.johnlaundry.database.CartTable;
import com.apporio.johnlaundry.settergetter.ResultofApi;
import com.apporio.johnlaundry.settergetter.Timeslot_SetterGetter;
import com.apporio.johnlaundry.settergetter.Timeslots_InnerData;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.apporio.johnlaundry.adapters.AdapterForListItemForBasket;

import butterknife.Bind;
import butterknife.ButterKnife;

import com.apporio.johnlaundry.database.DBManager;

import de.greenrobot.event.EventBus;

import com.apporio.johnlaundry.events.CartEvent;
import com.apporio.johnlaundry.utils.ConstansApplication;
import com.apporio.johnlaundry.utils.SessionManager;
import com.apporio.johnlaundry.utils.URLS;
import com.apporio.johnlaundry.utils.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class BasketActivity extends FragmentActivity implements ConnectionCallbacks,
        OnConnectionFailedListener, DatePickerDialog.OnDateSetListener {

    String clickeddateby;


    //String RushChages;

    int noofDryclean;
    private static final int DEFAULTZOOM = 15;

    String homeaddress, specialinstructions , fullAddress ;

    @Bind(R.id.list_view_in_item_for_basket)
    ListView itemlistView;
    @Bind(R.id.back_button_on_action_bar)
    LinearLayout backbtn;
    @Bind(R.id.activity_name_on_Action_bar)
    TextView activityName;
    @Bind(R.id.your_item_layout_in_basket_activity)
    LinearLayout addmoreitembtn;

    @Bind(R.id.txt_cart_instant)
    LinearLayout add_more_instant_txt;
    @Bind(R.id.next_button_in_basket_activity)
    Button nextbtn;
    @Bind(R.id.total_no_of_items_in_basket_activiy)
    TextView totalnoOfitemtxt;
    @Bind(R.id.collection_date_in_basket_activity_txt)
    TextView collectionDatetxt;
    @Bind(R.id.collection_time_in_basket_activity_txt)
    Spinner collectionTimetxt;
    @Bind(R.id.delivery_notes_in_basket_activity_edt)
    EditText deliverynotesedt;
    @Bind(R.id.collection_address_edittext_in_basket_activity)
    TextView collectionaddressedt;

////// same layot with different can be sed in case of defferent delivery address

    @Bind(R.id.delivery_date_in_basket_activiy_txt)
    TextView deliveryDatetxt;
    @Bind(R.id.delivery_time_in_basket_activity)
    LinearLayout deliveryTimeBtn;
    @Bind(R.id.delivery_date_in_basket_activity)
    LinearLayout deliveryDateBtn;
    @Bind(R.id.collection_date_iun_basket_activity)
    LinearLayout collectionDateBtn;
    @Bind(R.id.special_instruction_in_your_item_layout)
    EditText specialInstructionedt;
    @Bind(R.id.delivery_time_in_basket_activiy_txt)
    Spinner deliveryTimetxt;

    @Bind(R.id.gross_total)
    TextView totalpricetxt;

    @Bind(R.id.collection_time_in_basket_activity_text)
    TextView collectiontimetext;
    @Bind(R.id.delivery_time_in_basket_activiy_text)
    TextView deliverytimetext;

    ////////////////////////////////////////////////////

    @Bind(R.id.scentedLayout)
    LinearLayout ScentedLayout;
    @Bind(R.id.UnScentedLayout)
    LinearLayout unScentedLayout;
    @Bind(R.id.scentedTxt)
    TextView ScentedText;
    @Bind(R.id.UNscentedTxt)
    TextView UnScentedText;
    @Bind(R.id.ScentedImage)
    ImageView ScentedImageView;
    @Bind(R.id.UnScentedImage)
    ImageView UnScentedImageView;
    @Bind(R.id.changeDtChoicetxt)
    TextView ChangeTextView;

    @Bind(R.id.LeaveAtDoor)
    LinearLayout leaveAtDoorLayout;
    @Bind(R.id.onlytome)
    LinearLayout OnlyTomeLayout;
    @Bind(R.id.leaveText)
    TextView TextLeave;
    @Bind(R.id.onlymetxt)
    TextView TextMe;
    @Bind(R.id.doorImage)
    ImageView DoorImageView;
    @Bind(R.id.onlymeImage)
    ImageView OnlymeImageView;
    @Bind(R.id.changeDlChoicetxt)
    TextView ChangeTextView1;

    @Bind(R.id.firstcatcharge)
    TextView firstchargetxt;

//    @Bind(R.id.secondcatcharge)
//    TextView secondchargetxt;


    @Bind(R.id.sameday_no_of_items)
    TextView samedayitems;
    @Bind(R.id.sameday_gross_total)
    TextView samedaytotal;

//    @Bind(R.id.samedaypricelayout)
//    LinearLayout SameDayPriceLayout;
//
    @Bind(R.id.totalpricelayout)
    LinearLayout total_priceLinearLayout;
    @Bind(R.id.textView11)
    TextView txt_total_min;
    @Bind(R.id.llfordropoffdate)
    LinearLayout dropDate;
    /////////////////////////////////////////////////////////////

    DBManager dbm;

    SessionManager sm;
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 1000;

    GoogleApiClient client;
    LocationRequest mLocationRequest;
    PendingResult<LocationSettingsResult> result;
    public static List<CartTable> ct;

    ArrayList<String> productid_arr = new ArrayList<String>();
    ArrayList<String> categoryid_arr = new ArrayList<String>();
    ArrayList<String> productname_arr = new ArrayList<String>();
    ArrayList<String> product_no_of_unit = new ArrayList<String>();
    ArrayList<String> product_price_arr = new ArrayList<String>();
    private EventBus bus = EventBus.getDefault();

    StringRequest sr;
    RequestQueue queue;
    List<String> PickupFromslots = new ArrayList<String>();
    List<String> PickupToslots = new ArrayList<String>();
    List<String> DropoffFromslots = new ArrayList<String>();
    List<String> DropoffToslots = new ArrayList<String>();
    ArrayAdapter<String> dataAdapter;

    Timeslot_SetterGetter timeslot_setterGetter = new Timeslot_SetterGetter();
    time_slot_result time_slot_result_msg = new time_slot_result();
    Calendar now;
    DatePickerDialog dpd;
    Calendar newNow;
    String month;
    String new_date;
    String selected_collection_time, selected_delivery_time, new_collection_date;
    Date collectiondate, collectiondatebycalender, deliverydate;
    int diffInDays;
    String abc;

    @Bind(R.id.samedayoverlaylayout)
    LinearLayout SameDay_OverLayout;
    @Bind(R.id.samedaysecondoverlaylayout)
    LinearLayout SameDay_OverSecondLayout;
    @Bind(R.id.nextdayoverlaylayout)
    LinearLayout NextDay_OverLayout;
    @Bind(R.id.nextdayfirstoverlayout)
    LinearLayout NextDay_First_OverLayout;
    @Bind(R.id.selectdateoverlayout)
    LinearLayout SelectDate_Over_Layout;

    String formatdate;
    String formatdate1;

    int Hour;
    EditText tipValue;
    String selectedDetergent = "Scented", selectedDelivery = "Leave At Door";
    String tip = "0";

    ///////////////////
    public static MapView mapView;
    public static Context ctc;
    private static final int MY_PERMISSIONS_REQUEST_LOCATION = 0;
    public static GoogleMap map;
    public static GPStracker gps;
    public static Double currentlat, currentlong;
    public static Marker now1;
    private Geocoder geocoder;
    private List<Address> addresses;

    ScrollView scrollView;

    ////////////////////////////////
    public static List<CartTable> carttablecategory;
    CheckBox firstCheckBox, secondCheckBox, thirdCheckBox, forthCheckBox , SameDaySecondCheck;
    ArrayList<String> product_id_cat = new ArrayList<String>();
    ArrayList<String> product_cat = new ArrayList<String>();
    ArrayList<String> productname_cat = new ArrayList<String>();
    ArrayList<String> product_no_of_unit_cat = new ArrayList<String>();
    ArrayList<String> product_price_arr_cat = new ArrayList<String>();

    static String samedayunit;
    double totalcartamount;
    Double d1;

    public static ProgressDialog pDialog;

    ArrayList<String> price_catg_id = new ArrayList<String>();
    ArrayList<String> price_catg_name = new ArrayList<String>();
    ArrayList<String> price_catg_price = new ArrayList<String>();

    double firstcharge, secondcharge;

    String day_type = "4";
    String total_price_send;
    int sizeOFdryclean;
    ArrayList<String> dropoff = new ArrayList<>();
    ArrayList<String> pickup = new ArrayList<>();
    DecimalFormat df;

    String FROM = "" ;
    String FirstDayStaticValue = "33.75" ;
    String SecondDayStaticValue = "35.00" ;
    String ThirdDayStaticValue = "29.85" ;
    String ForthDayStaticValue = "35.00" ;
    String FifthDayStaticValue = "29.85" ;
    String SelectedDayPrice = "" ;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        client = new GoogleApiClient.Builder(this)
                .addApi(AppIndex.API)
                .addApi(LocationServices.API)
                .build();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basket);

        ButterKnife.bind(this);
        ctc = getApplicationContext();
        queue = VolleySingleton.getInstance(BasketActivity.this).getRequestQueue();

        Intent ii= getIntent();
        FROM = ii.getStringExtra("FORM");


        dbm = new DBManager(BasketActivity.this);
        sm = new SessionManager(BasketActivity.this);
        bus.register(this);
        tipValue = (EditText) findViewById(R.id.tip);
        scrollView = (ScrollView) findViewById(R.id.scrollview);
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);

        collectiontimetext.setVisibility(View.VISIBLE);
        deliverytimetext.setVisibility(View.VISIBLE);
        activityName.setText("Your Basket");

        mapView = (MapView) findViewById(R.id.mapgh);
        mapView.onCreate(savedInstanceState);
        gps = new GPStracker(ctc);
     //   RushDeliveryCharges();
//            currentlat = gps.getLatitude();
//            currentlong = gps.getLongitude();

        Calendar c = Calendar.getInstance();
        Hour = c.get(Calendar.HOUR_OF_DAY);

        sizeOFdryclean = productsListCategory("40");

        HashMap<String, String> user = sm.getUserDetails();
        homeaddress = user.get(SessionManager.KEY_Address);

        fullAddress = homeaddress +", Apt. / Rm. - "+ user.get(SessionManager.KEY_Appartment) ;

        collectionaddressedt.setText(fullAddress);

        Log.e("user address", "" + homeaddress);

        HashMap<String, String> latlng = sm.getLatLng();

        currentlat = Double.valueOf(latlng.get(SessionManager.KEY_Lat));
        currentlong = Double.valueOf(latlng.get(SessionManager.KEY_Long));

        Log.e("current latitude",""+currentlat);
        Log.e("current Longitude",""+currentlong);





///////////////////////////////////////////////////////

        firstCheckBox = (CheckBox) findViewById(R.id.checkbox1);
        secondCheckBox = (CheckBox) findViewById(R.id.checkbox2);
        thirdCheckBox = (CheckBox) findViewById(R.id.checkbox3);
        forthCheckBox = (CheckBox) findViewById(R.id.checkbox4);
        SameDaySecondCheck = (CheckBox)findViewById(R.id.same_day_second_check);

        //   firstCheckBox.setChecked(true);
        //   day_type="0";
//        Priceofcategory();

        // timeslots("0");
        total_priceLinearLayout.setVisibility(View.GONE);
        dropDate.setVisibility(View.GONE);
////////////////////////////////////////////////////////


        collectionDateBtn.setOnClickListener(collectionDatelistener);
        deliveryDateBtn.setOnClickListener(deliveryDateListener);


        /////////////////////////////////////

        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        Date d = new Date();
        String dayOfTheWeek = sdf.format(d);

        Log.e("day of week", "" + dayOfTheWeek);

        SameDay_OverLayout.setVisibility(View.VISIBLE);
        SameDay_OverLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (collectionDatetxt.getText().toString().equals("")) {
                    Toast.makeText(BasketActivity.this, "Please Select Collection Date First", Toast.LENGTH_SHORT).show();
                } else {
                    firstCheckBox.setChecked(true);

                    SameDay_OverLayout.setVisibility(View.GONE);
                }
            }
        });

        SameDay_OverSecondLayout.setVisibility(View.VISIBLE);
        SameDay_OverSecondLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (collectionDatetxt.getText().toString().equals("")) {
                    Toast.makeText(BasketActivity.this, "Please Select Collection Date First", Toast.LENGTH_SHORT).show();
                } else {
                    SameDaySecondCheck.setChecked(true);

                    SameDay_OverSecondLayout.setVisibility(View.GONE);
                }
            }
        });


        NextDay_OverLayout.setVisibility(View.VISIBLE);
        NextDay_OverLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (collectionDatetxt.getText().toString().equals("")) {
                    Toast.makeText(BasketActivity.this, "Please Select Collection Date First", Toast.LENGTH_SHORT).show();
                } else {
                    thirdCheckBox.setChecked(true);

                    NextDay_OverLayout.setVisibility(View.GONE);
                }
            }
        });
        NextDay_First_OverLayout.setVisibility(View.VISIBLE);
        NextDay_First_OverLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (collectionDatetxt.getText().toString().equals("")) {
                    Toast.makeText(BasketActivity.this, "Please Select Collection Date First", Toast.LENGTH_SHORT).show();
                } else {
                    secondCheckBox.setChecked(true);

                    NextDay_First_OverLayout.setVisibility(View.GONE);
                }
            }
        });

        SelectDate_Over_Layout.setVisibility(View.VISIBLE);
        SelectDate_Over_Layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (collectionDatetxt.getText().toString().equals("")) {
                    Toast.makeText(BasketActivity.this, "Please Select Collection Date First", Toast.LENGTH_SHORT).show();
                } else {
                    forthCheckBox.setChecked(true);

                }
            }
        });


//                if (dayOfTheWeek.equals("Sunday")){
//            SameDay_OverLayout.setVisibility(View.VISIBLE);
//            SameDay_OverLayout.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Toast.makeText(BasketActivity.this, "Sunday Will Be Shut Down !!", Toast.LENGTH_SHORT).show();
//                }
//            });
//        }else {
//                   // SameDay_OverLayout.setVisibility(View.GONE);
//                }
//
//
//        if (dayOfTheWeek.equals("Saturday")){
//            NextDay_OverLayout.setVisibility(View.VISIBLE);
//            NextDay_OverLayout.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Toast.makeText(BasketActivity.this, "Not available for pickup on Saturdays !!", Toast.LENGTH_SHORT).show();
//                }
//            });
//        }else {
//            NextDay_OverLayout.setVisibility(View.GONE);
//        }


//        if (dayOfTheWeek.equals("Monday")){
//            Toast.makeText(BasketActivity.this, "We Are Closed On Sunday !!", Toast.LENGTH_SHORT).show();
//        }else {
        firstCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true) {

                    day_type = "4";
                    if (sizeOFdryclean == 0) {
                        String url = "http://dropmint.space/laundry-app-development/api/dropmint_timeslot_Latest.php?day_type=".concat(day_type).concat("&pick_date=").concat(formatdate).concat("&dry_clean_item=").concat("1");
                        timeslots(url);
                    } else {
                        String url = "http://dropmint.space/laundry-app-development/api/dropmint_timeslot_Latest.php?day_type=".concat(day_type).concat("&pick_date=").concat(formatdate).concat("&dry_clean_item=").concat("0");
                        timeslots(url);
                    }
                    secondCheckBox.setChecked(false);
                    thirdCheckBox.setChecked(false);
                    forthCheckBox.setChecked(false);
                    SameDaySecondCheck.setChecked(false);
                    total_priceLinearLayout.setVisibility(View.VISIBLE);
                    dropDate.setVisibility(View.GONE);


                    if (FROM.equals("2")){
                        totalnoOfitemtxt.setText("0");
                        totalpricetxt.setText("" + FirstDayStaticValue);
                        SelectedDayPrice = FirstDayStaticValue ;

                        Log.e("** first day price",""+SelectedDayPrice);

                        double dd = Double.parseDouble(SelectedDayPrice);
                        if (!tipValue.getText().toString().isEmpty()){
                            double dd1 = Double.parseDouble(tipValue.getText().toString()) ;
                            double d = dd+dd1 ;

                            totalpricetxt.setText(""+df.format(d));
                            total_price_send = totalpricetxt.getText().toString().trim();
                        }
//                        double dd1 = Double.parseDouble(tipValue.getText().toString()) ;
//                        double dl = dd+dd1 ;
//                        Log.e("** final value",""+dl);
//                       // totalpricetxt.setText("");
//                        totalpricetxt.setText(""+df.format(dl));
//                        total_price_send = totalpricetxt.getText().toString().trim();
                    }else {
                        totalnoOfitemtxt.setText("" + dbm.totalNoofitemsincar());
                     //   totalpricetxt.setText("" + dbm.calculationForGrossPrice());
                        double dd = Double.parseDouble(df.format(dbm.calculationForGrossPrice()));
                        double dd1 = Double.parseDouble(tipValue.getText().toString());

                        totalpricetxt.setText("" + df.format(dd+dd1));
                        total_price_send = totalpricetxt.getText().toString().trim();
                    }




                  //  SameDayPriceLayout.setVisibility(View.GONE);


                   // double d = Double.parseDouble(RushChages);

                  //  calculationSameday(firstCheckBox, d);
                } else {

                }
            }
        });
        //   }

        SameDaySecondCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked == true) {


                    day_type = "0";
                    if (sizeOFdryclean == 0) {
                        String url = URLS.time_slot.concat(day_type).concat("&pick_date=").concat(formatdate).concat("&dry_clean_item=").concat("1");
                        timeslots(url);
                    } else {
                        String url = URLS.time_slot.concat(day_type).concat("&pick_date=").concat(formatdate).concat("&dry_clean_item=").concat("0");
                        timeslots(url);
                    }

                    if (FROM.equals("2")){
                        totalnoOfitemtxt.setText("0");
                        totalpricetxt.setText("" + SecondDayStaticValue);
                        SelectedDayPrice = SecondDayStaticValue ;
                        double dd = Double.parseDouble(SelectedDayPrice);
                        if (!tipValue.getText().toString().isEmpty()){
                            double dd1 = Double.parseDouble(tipValue.getText().toString()) ;
                            double d = dd+dd1 ;

                            totalpricetxt.setText(""+df.format(d));
                            total_price_send = totalpricetxt.getText().toString().trim();
                        }

                    }else {
                        totalnoOfitemtxt.setText("" + dbm.totalNoofitemsincar());
                       // totalpricetxt.setText("" + dbm.calculationForGrossPrice());
                        double dd = Double.parseDouble(df.format(dbm.calculationForGrossPrice()));
                        double dd1 = Double.parseDouble(tipValue.getText().toString());

                        totalpricetxt.setText("" + df.format(dd+dd1));
                        total_price_send = totalpricetxt.getText().toString().trim();
                    }


                    secondCheckBox.setChecked(false);
                    thirdCheckBox.setChecked(false);
                    forthCheckBox.setChecked(false);
                    firstCheckBox.setChecked(false);
                    total_priceLinearLayout.setVisibility(View.VISIBLE);
                    dropDate.setVisibility(View.GONE);

                } else {

                }


            }
        });



        secondCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                Date convertDate1 = null;

                if (isChecked == true) {
                    String date = formatdate;

                    DateFormat df1 = new SimpleDateFormat("dd/mm/yyyy");

                    try {
                       convertDate1 = df1.parse(date) ;
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    Log.e("selected Date",""+date);


                    String[] parts = date.split("/");
                    String part1 = parts[0]; // 004
                    String part2 = parts[1];
                    String part3 = parts[2];
                    int dt = Integer.parseInt(part1);
                    dt = dt + 1;
                  //  String sss = "" + dt + "/" + part2 + "/" + part3;

                    Log.e("enterd date",""+convertDate1);

                    String dd = incrementDateByOne(formatdate);
                    String sss = dd ;
                    Log.e("NEXT DATE",""+dd);
                    Log.e("next date", "" + sss);

                    day_type = "1";
                    if (sizeOFdryclean==0){
                        String url = URLS.time_slot.concat(day_type).concat("&pick_date=").concat(formatdate).concat("&dry_clean_item=").concat("1");
                        timeslots(url);
                    }else {
                        String url = URLS.time_slot.concat("1").concat("&pick_date=").concat(formatdate).concat("&dry_clean_item=").concat("0");
                        timeslots(url);
                    }

               //     String url = URLS.time_slot.concat("1").concat("&drop_date=").concat(sss);
               //     timeslots(url);

                    if (FROM.equals("2")){
                        totalnoOfitemtxt.setText("0");
                        totalpricetxt.setText("" + ThirdDayStaticValue);
                        SelectedDayPrice = ThirdDayStaticValue ;
                        double dd2 = Double.parseDouble(SelectedDayPrice);

                        if (!tipValue.getText().toString().isEmpty()){
                            double dd1 = Double.parseDouble(tipValue.getText().toString()) ;
                            double d = dd2+dd1 ;

                            totalpricetxt.setText(""+df.format(d));
                            total_price_send = totalpricetxt.getText().toString().trim();
                        }
//                        double dd1 = Double.parseDouble(tipValue.getText().toString()) ;
//                        double d = dd2+dd1 ;
//                        totalpricetxt.setText(""+df.format(d));
//                        total_price_send = totalpricetxt.getText().toString().trim();
                    }else {
                        totalnoOfitemtxt.setText("" + dbm.totalNoofitemsincar());
                    //    totalpricetxt.setText("" + dbm.calculationForGrossPrice());
                        double dd2 = Double.parseDouble(df.format(dbm.calculationForGrossPrice()));
                        double dd1 = Double.parseDouble(tipValue.getText().toString());

                        totalpricetxt.setText("" + df.format(dd2+dd1));
                        total_price_send = totalpricetxt.getText().toString().trim();
                    }


                    firstCheckBox.setChecked(false);
                    thirdCheckBox.setChecked(false);
                    forthCheckBox.setChecked(false);
                    SameDaySecondCheck.setChecked(false);
                  //  SameDayPriceLayout.setVisibility(View.GONE);
                    total_priceLinearLayout.setVisibility(View.VISIBLE);
                    dropDate.setVisibility(View.GONE);


                } else {

                }

            }
        });

//        if (dayOfTheWeek.equals("Saturday")){
//            Toast.makeText(BasketActivity.this, "Not available for pickup on Saturdays !!", Toast.LENGTH_SHORT).show();
//        }else {
        thirdCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                Date convertDate1 = null;

                if (isChecked == true) {
                    String date = formatdate;

                    DateFormat df1 = new SimpleDateFormat("MM/dd/yyyy");

                    try {
                        convertDate1 = df1.parse(date) ;
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }


                    String[] parts = date.split("/");
                    String part1 = parts[0]; // 004
                    String part2 = parts[1];
                    String part3 = parts[2];
                    int dt = Integer.parseInt(part1);
                    dt = dt + 1;
                   // String sss = "" + dt + "/" + part2 + "/" + part3;
                   // Log.e("next date", "" + sss);


                    String dd = incrementDateByOne(formatdate);

                    Log.e("NEXT DATE",""+dd);
                   // Log.e("next date", "" + sss);

                    String sss  = dd ;

//                    String url = URLS.time_slot.concat("1").concat("&drop_date=").concat(sss);
//                    timeslots(url);
                    day_type = "2";
                    if (sizeOFdryclean==0){
                        String url = URLS.time_slot.concat(day_type).concat("&pick_date=").concat(formatdate).concat("&dry_clean_item=").concat("1");
                        timeslots(url);
                    }else {
                        String url = URLS.time_slot.concat(day_type).concat("&pick_date=").concat(formatdate).concat("&dry_clean_item=").concat("0");
                        timeslots(url);
                    }

                    if (FROM.equals("2")){
                        totalnoOfitemtxt.setText("0");
                        totalpricetxt.setText("" + ForthDayStaticValue);
                        SelectedDayPrice = ForthDayStaticValue ;
                        double dd2 = Double.parseDouble(SelectedDayPrice);
                        if (!tipValue.getText().toString().isEmpty()){
                            double dd1 = Double.parseDouble(tipValue.getText().toString()) ;
                            double d = dd2+dd1 ;

                            totalpricetxt.setText(""+df.format(d));
                            total_price_send = totalpricetxt.getText().toString().trim();
                        }
//                        double dd1 = Double.parseDouble(tipValue.getText().toString()) ;
//                        double d = dd2+dd1 ;
//                        totalpricetxt.setText(""+df.format(d));
//                        total_price_send = totalpricetxt.getText().toString().trim();
                    }else {
                        totalnoOfitemtxt.setText("" + dbm.totalNoofitemsincar());
                      //  totalpricetxt.setText("" + dbm.calculationForGrossPrice());
                        double dd2 = Double.parseDouble(df.format(dbm.calculationForGrossPrice()));
                        double dd1 = Double.parseDouble(tipValue.getText().toString());

                        totalpricetxt.setText("" + df.format(dd2+dd1));
                        total_price_send = totalpricetxt.getText().toString().trim();
                    }

                    firstCheckBox.setChecked(false);
                    secondCheckBox.setChecked(false);
                    forthCheckBox.setChecked(false);
                    SameDaySecondCheck.setChecked(false);
                  //  SameDayPriceLayout.setVisibility(View.GONE);
                    total_priceLinearLayout.setVisibility(View.VISIBLE);
                    dropDate.setVisibility(View.GONE);

                } else {
                  //  SameDayPriceLayout.setVisibility(View.GONE);
                    total_priceLinearLayout.setVisibility(View.VISIBLE);
                }

            }
        });
        //    }


        String s = "  Select Date";
        // String s1="Date";
        forthCheckBox.setText(s);
        forthCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true) {
                    // timeslots("3");
                    // day_type="3";
                    firstCheckBox.setChecked(false);
                    secondCheckBox.setChecked(false);
                    thirdCheckBox.setChecked(false);
                    SameDaySecondCheck.setChecked(false);
                    dropDate.setVisibility(View.VISIBLE);
                 //   SameDayPriceLayout.setVisibility(View.GONE);
                    total_priceLinearLayout.setVisibility(View.VISIBLE);
                    ConstansApplication.DELIVERYDATE = "";

                    if (FROM.equals("2")){
                        totalnoOfitemtxt.setText("0");
                        totalpricetxt.setText("" + FifthDayStaticValue);
                        SelectedDayPrice = FifthDayStaticValue ;
                        double dd2 = Double.parseDouble(SelectedDayPrice);
                        if (!tipValue.getText().toString().isEmpty()){
                            double dd1 = Double.parseDouble(tipValue.getText().toString()) ;
                            double d = dd2+dd1 ;

                            totalpricetxt.setText(""+df.format(d));
                            total_price_send = totalpricetxt.getText().toString().trim();
                        }
//                        double dd1 = Double.parseDouble(tipValue.getText().toString()) ;
//                        double d = dd2+dd1 ;
//                        totalpricetxt.setText(""+df.format(d));
//                        total_price_send = totalpricetxt.getText().toString().trim();
                    }else {
                        totalnoOfitemtxt.setText("" + dbm.totalNoofitemsincar());
                        // totalpricetxt.setText("" + dbm.calculationForGrossPrice());
                        double dd2 = Double.parseDouble(df.format(dbm.calculationForGrossPrice()));
                        double dd1 = Double.parseDouble(tipValue.getText().toString());

                        totalpricetxt.setText("" + df.format(dd2+dd1));
                        total_price_send = totalpricetxt.getText().toString().trim();
                    }

                } else {
                   // SameDayPriceLayout.setVisibility(View.GONE);
                    total_priceLinearLayout.setVisibility(View.VISIBLE);
                    dropDate.setVisibility(View.GONE);
                }



            }
        });


        deliveryTimetxt.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ConstansApplication.DELIVERYTIME = dropoff.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        collectionTimetxt.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ConstansApplication.COLLECTIONTIME = pickup.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        /////////////////////////////////////////////////////

        df = new DecimalFormat("#0.00");
        // subtotal.setText("Sub Total - £ "+df.format(dbm.calculationForGrossPrice()));

        d1 = Double.parseDouble(String.valueOf("2.00"));
        tipValue.setText("" + df.format(d1));

        Double dd = dbm.calculationForGrossPrice() + d1;
        totalpricetxt.setText("" + df.format(dd));

        tipValue.addTextChangedListener(tipwatcher);


        int permissionCheck = ContextCompat.checkSelfPermission(BasketActivity.this, Manifest.permission.ACCESS_FINE_LOCATION);

        Log.e("permission", "" + permissionCheck);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(BasketActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);
        } else {
            //TODO

//            ActivityCompat.requestPermissions(BasketActivity.this,
//                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
//                    MY_PERMISSIONS_REQUEST_LOCATION);
            getthetracker();
        }





        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if (FROM.equals("2")){
            addmoreitembtn.setVisibility(View.GONE);
            itemlistView.setVisibility(View.GONE);
            add_more_instant_txt.setVisibility(View.VISIBLE);
            txt_total_min.setText("Order Minimum");

        }else {
            addmoreitembtn.setVisibility(View.VISIBLE);
            itemlistView.setVisibility(View.VISIBLE);
            add_more_instant_txt.setVisibility(View.GONE);
            txt_total_min.setText("Total");
            loadlistview();
        }

        addmoreitembtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        nextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkvalues();

            }
        });

        ScentedImageView.setImageResource(R.drawable.scented_click);
        ScentedText.setTextColor(getResources().getColor(R.color.muted_blue));
        ChangeTextView.setText("Scented detergent for Wash N Fold");

        UnScentedImageView.setImageResource(R.drawable.unscented);
        UnScentedText.setTextColor(getResources().getColor(R.color.muted_grey));

        DoorImageView.setImageResource(R.drawable.door_click);
        TextLeave.setTextColor(getResources().getColor(R.color.muted_blue));
        ChangeTextView1.setText("I give DropMint permission to leave an order at my front door or with my building concierge");

        OnlymeImageView.setImageResource(R.drawable.me_later);
        TextMe.setTextColor(getResources().getColor(R.color.muted_grey));


        ////////////////////////////////////////////////
        ScentedLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ScentedImageView.setImageResource(R.drawable.scented_click);
                ScentedText.setTextColor(getResources().getColor(R.color.muted_blue));
                ChangeTextView.setText("Scented detergent for Wash N Fold");

                UnScentedImageView.setImageResource(R.drawable.unscented);
                UnScentedText.setTextColor(getResources().getColor(R.color.muted_grey));

                selectedDetergent = "Scented";


            }
        });
        unScentedLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UnScentedImageView.setImageResource(R.drawable.unscented_click);
                UnScentedText.setTextColor(getResources().getColor(R.color.muted_blue));
                ChangeTextView.setText("Allergen & Scent-Free detergent for Wash N Fold");

                ScentedImageView.setImageResource(R.drawable.scented);
                ScentedText.setTextColor(getResources().getColor(R.color.muted_grey));

                selectedDetergent = "Unscented";
            }
        });

        leaveAtDoorLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DoorImageView.setImageResource(R.drawable.door_click);
                TextLeave.setTextColor(getResources().getColor(R.color.muted_blue));
                ChangeTextView1.setText("I give DropMint permission to leave an order at my front door or with my building concierge");

                OnlymeImageView.setImageResource(R.drawable.me_later);
                TextMe.setTextColor(getResources().getColor(R.color.muted_grey));

                selectedDelivery = "Leave At Door";

            }
        });

        OnlyTomeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DoorImageView.setImageResource(R.drawable.door);
                TextLeave.setTextColor(getResources().getColor(R.color.muted_grey));
                ChangeTextView1.setText("If I am unavailable at my selected time, the delivery will be delayed");

                OnlymeImageView.setImageResource(R.drawable.me_later_click);
                TextMe.setTextColor(getResources().getColor(R.color.muted_blue));

                selectedDelivery = "Only To Me";

            }
        });


    }


    View.OnClickListener collectionDatelistener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            clickeddateby = "collectionDatelistener";


            showCollectiondatepickerdialoge();
        }

        private void showCollectiondatepickerdialoge() {


            now = Calendar.getInstance();
            dpd = DatePickerDialog.newInstance(
                    BasketActivity.this,
                    now.get(Calendar.YEAR),
                    now.get(Calendar.MONTH),
                    now.get(Calendar.DAY_OF_MONTH)
            );
            dpd.setMinDate(now);
            // dpd.setAccentColor(Color.WHITE);
            dpd.show(getFragmentManager(), "Datepickerdialog");

        }
    };

    View.OnClickListener deliveryDateListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            if (ConstansApplication.COLLECTIONDATE.equals("")) {

                Toast.makeText(BasketActivity.this, "please fill all details", Toast.LENGTH_SHORT).show();

            } else {
                clickeddateby = "deliveryDateListener";

                dpd = DatePickerDialog.newInstance(
                        BasketActivity.this,
                        newNow.get(Calendar.YEAR),
                        newNow.get(Calendar.MONTH),
                        newNow.get(Calendar.DAY_OF_MONTH)

                );

                dpd.setMinDate(newNow);
                //new.add(Calendar.DATE,1);

                dpd.show(getFragmentManager(), "Datepickerdialog");
            }
        }

    };


//    public double calculationSameday(CheckBox chk, double cahrge1) {
//
////        double grossfortwo = Double.valueOf(productsListCategory(price_catg_id.get(0),price_catg_id.get(1)));
////        Log.e("gross bill for two",""+grossfortwo);
////        double charge = cahrge1 ;
////        Log.e("charge for eco and prime",""+cahrge1);
////        double grosswithcharges =  grossfortwo + grossfortwo * charge ;
//
//        //      Log.e("total of eco and prime",""+grosswithcharges);
//        totalcartamount = dbm.calculationForGrossPrice();
//        Log.e("total cart amount", "" + totalcartamount);
//        //    double chargesofdryclean = totalcartamount - grossfortwo ;
//
//        //    Log.e("price of dry clean products",""+chargesofdryclean) ;
//
//        //    double charge_dryclean = charge2;
//
//        //     double totalfordryclean = chargesofdryclean + chargesofdryclean * charge_dryclean ;
//        //    Log.e("total of dry clean with tax",""+totalfordryclean);
//
//        double subtotalofall = totalcartamount + cahrge1;
//
//
////        if (chk.isChecked() == true) {
////           // SameDayPriceLayout.setVisibility(View.VISIBLE);
////            samedayitems.setText(totalnoOfitemtxt.getText().toString().trim());
////            double dd = subtotalofall + d1;
////            total_price_send = String.valueOf(dd);
////            samedaytotal.setText("" + df.format(dd));
////
////        } else {
////           // SameDayPriceLayout.setVisibility(View.GONE);
////            total_price_send = totalpricetxt.getText().toString().trim();
////        }
//
//        return subtotalofall;
//
//    }


    private int productsListCategory(String id) {
        Integer price, total, total1 = 0;
        int noofitem;
        carttablecategory = dbm.getproductsaccordingcategory(id);

        //  samedayunit = 0;

        try {
            for (int i = 0; i < carttablecategory.size(); i++) {
                product_id_cat.add(carttablecategory.get(i).getProductId());
                productname_cat.add(carttablecategory.get(i).getProductaName());
                product_no_of_unit_cat.add(carttablecategory.get(i).getProductNoofunits());
                product_price_arr_cat.add(carttablecategory.get(i).getProductprice());
                product_cat.add(carttablecategory.get(i).getCategoryId());

                //  samedayunit= samedayunit + (Integer.parseInt(carttablecategory.get(i).getProductNoofunits()));

                price = Integer.parseInt(carttablecategory.get(i).getProductprice());
                noofDryclean = Integer.parseInt(carttablecategory.get(i).getProductNoofunits());
//                        total = noofitem * price ;
//                        total1 = total1 + total ;
            }

        } catch (Exception e) {
        }


        return noofDryclean;

    }


    public void onEvent(CartEvent event) {
        samedayunit = event.getDatatotalitems();
      //  totalnoOfitemtxt.setText(samedayunit);

        Log.e("unit of items", "" + samedayunit);
        Double dd = Double.valueOf(event.getDatagrosstotal());
        Log.e("total price",""+df.format(dd));

        if (FROM.equals("2")){
            totalnoOfitemtxt.setText("0");
            totalpricetxt.setText(SelectedDayPrice);
        }else {

            double dd2 = Double.parseDouble(df.format(dbm.calculationForGrossPrice()));
            double dd1 = Double.parseDouble(tipValue.getText().toString());

            totalpricetxt.setText("" + df.format(dd2+dd1));
            total_price_send = totalpricetxt.getText().toString().trim();

            totalnoOfitemtxt.setText(samedayunit);
          //  totalpricetxt.setText("" + df.format(dd));
        }


      //  totalpricetxt.setText("" + df.format(dd));


        totalcartamount = Double.parseDouble(df.format(dd));

//        if (SameDayPriceLayout.getVisibility()==View.VISIBLE){
//            double d = Double.parseDouble(RushChages);
//            double subtotalofall = totalcartamount + d;
//            SameDayPriceLayout.setVisibility(View.VISIBLE);
//            samedayitems.setText(samedayunit);
//            double dd1 = subtotalofall + d1;
//            total_price_send = String.valueOf(dd1);
//            samedaytotal.setText("" + df.format(dd1));
//        }


    }


    final TextWatcher tipwatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

//            if (firstCheckBox.isChecked() == true) {
//                double d = Double.parseDouble(RushChages);
//                double dd = calculationSameday(firstCheckBox, d);
//                double ddd = dd + 2.0;
//                samedaytotal.setText("" + df.format(ddd));
//                total_price_send = String.valueOf(ddd);
//            } else {

                if (FROM.equals("2")){
                    double dd = Double.parseDouble(SelectedDayPrice);
                    double dd1 = Double.parseDouble("2.00") ;
                    totalpricetxt.setText(""+df.format(dd+dd1));
                    total_price_send = totalpricetxt.getText().toString().trim();
                }else {


                    double dd = Double.parseDouble(df.format(dbm.calculationForGrossPrice()));
                    double dd1 = Double.parseDouble("2.00");

                    totalpricetxt.setText("" + df.format(dd+dd1));
                    total_price_send = totalpricetxt.getText().toString().trim();
                }


        //    }


        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            if (s.length() != 0) {


                if (FROM.equals("2")){
                    Double d = Double.parseDouble(String.valueOf(s));
                    Double d1 = Double.parseDouble(SelectedDayPrice) ;
                    Double dd = d1 + d;

                    totalpricetxt.setText("" + df.format(dd));
                    total_price_send = totalpricetxt.getText().toString().trim();
                }else {
                    Double d = Double.parseDouble(String.valueOf(s));
                    Double dd = dbm.calculationForGrossPrice() + d;

                    totalpricetxt.setText("" + df.format(dd));
                    total_price_send = totalpricetxt.getText().toString().trim();
                }




            } else {


                if (FROM.equals("2")){
                    Double d = Double.parseDouble("0.00");
                    Double d1 = Double.parseDouble(SelectedDayPrice) ;
                    Double dd = d1 + d;

                    totalpricetxt.setText("" + df.format(dd));
                    total_price_send = totalpricetxt.getText().toString().trim();
                }else {
                    Double d = Double.parseDouble("0.00");
                    Double dd = dbm.calculationForGrossPrice() + d;
                 //   total_price_send = String.valueOf(dd);
                    totalpricetxt.setText("" + df.format(dd));
                    total_price_send = totalpricetxt.getText().toString().trim();
                }


              //  totalpricetxt.setText("" + df.format(dbm.calculationForGrossPrice()));
             //   total_price_send = String.valueOf(dbm.calculationForGrossPrice());
            }


        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };


//    @Override
//    protected void onStart() {
//        super.onStart();
//        client.connect();
//    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
        getthetracker();
        checkPlayServices();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {


                    getthetracker();

                }
                break;


            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }


    public void getthetracker() {


        MapsInitializer.initialize(ctc);
        switch (GooglePlayServicesUtil.isGooglePlayServicesAvailable(ctc)) {
            case ConnectionResult.SUCCESS:

                //Toast.makeText(ctc, "tracker", Toast.LENGTH_SHORT).show();
                // Gets to GoogleMap from the MapView and does initialization stuff
                if (mapView != null) {
                    map = mapView.getMap();
                    map.getUiSettings().setMyLocationButtonEnabled(true);
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    map.setMyLocationEnabled(true);
                    gps = new GPStracker(ctc, map);
                    CameraUpdate center =
                            CameraUpdateFactory.newLatLng(new LatLng(currentlat,
                                    currentlong));
                    CameraUpdate zoom = CameraUpdateFactory.zoomTo(15);

                    map.moveCamera(center);
                    map.animateCamera(zoom);

                    new GetLocationAsync(currentlat, currentlong)
                            .execute();

                    now1 = map.addMarker(new MarkerOptions()
                            .position(new LatLng(currentlat, currentlong))
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.location_marker)));
                    map.setMyLocationEnabled(true);

                    now1.showInfoWindow();

                }

                break;
            case ConnectionResult.SERVICE_MISSING:
                Toast.makeText(ctc, "SERVICE MISSING", Toast.LENGTH_SHORT).show();
                break;
            case ConnectionResult.SERVICE_VERSION_UPDATE_REQUIRED:
                Toast.makeText(ctc, "UPDATE REQUIRED", Toast.LENGTH_SHORT).show();
                break;
            default:
                Toast.makeText(ctc, GooglePlayServicesUtil.isGooglePlayServicesAvailable(ctc), Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onConnected(Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

        monthOfYear = monthOfYear + 1;

//        if (monthOfYear==1){
//            month= "Jan";
//        }
//        else if(monthOfYear==2){
//            month = "Feb";
//        }
//        else if(monthOfYear==3){
//            month = "Mar";
//        }
//        else if(monthOfYear==4){
//            month = "Apr";
//        }
//        else if(monthOfYear==5){
//            month = "May";
//        }
//        else if(monthOfYear==6){
//            month = "June";
//        }
//        else if(monthOfYear==7){
//            month = "July";
//        }
//        else if(monthOfYear==8){
//            month = "Aug";
//        }
//        else if(monthOfYear==9){
//            month = "Sep";
//        }
//        else if(monthOfYear==10){
//            month = "Oct";
//        }
//        else if(monthOfYear==11){
//            month = "Nov";
//        }
//        else if(monthOfYear==12){
//            month = "Dec";
//        }


        if(clickeddateby.equals("collectionDatelistener")) {



            new_date = "" + dayOfMonth + "/" + monthOfYear + "/" + year;

            DateFormat inputFormat = new SimpleDateFormat("d/MM/yyyy");
            DateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy");
            String inputDateStr=new_date;
            Date date = null;
            try {
                date = inputFormat.parse(inputDateStr);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            formatdate = outputFormat.format(date);


            newNow = Calendar.getInstance();

            new_collection_date = "" + year + "-" + monthOfYear + "-" + dayOfMonth;


            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            try {
                newNow.setTime(sdf.parse(new_collection_date));


                collectiondate = sdf.parse(new_collection_date);
                collectiondatebycalender = sdf.parse(sdf.format(now.getTime()));//Returns 15/10/2012

            } catch (ParseException e) {
                e.printStackTrace();
            }
            newNow.add(Calendar.DATE, 2);

            DateFormat inputFormat1 = new SimpleDateFormat("dd/MM/yyyy");
            DateFormat outputFormat1 = new SimpleDateFormat("MM/dd/yyyy");
            String inputDateStr1=new_date;
            Date date1 = null;
            try {
                date1 = inputFormat1.parse(inputDateStr1);
            } catch (ParseException e) {
                e.printStackTrace();
            }


            collectionDatetxt.setText(outputFormat1.format(date1));

              Log.e("selected day type",""+day_type);
           if (day_type.equals("4")){
               if (sizeOFdryclean == 0) {
                   String url = "http://dropmint.space/laundry-app-development/api/dropmint_timeslot_Latest.php?day_type=".concat(day_type).concat("&pick_date=").concat(formatdate).concat("&dry_clean_item=").concat("1");
                   timeslots(url);
               } else {
                   String url = "http://dropmint.space/laundry-app-development/api/dropmint_timeslot_Latest.php?day_type=".concat(day_type).concat("&pick_date=").concat(formatdate).concat("&dry_clean_item=").concat("0");
                   timeslots(url);
               }
           }else {
               if (sizeOFdryclean == 0) {
                   String url = URLS.time_slot.concat(day_type).concat("&pick_date=").concat(formatdate).concat("&dry_clean_item=").concat("1");
                   timeslots(url);
               } else {
                   String url = URLS.time_slot.concat(day_type).concat("&pick_date=").concat(formatdate).concat("&dry_clean_item=").concat("0");
                   timeslots(url);
               }
           }





            diffInDays = (int) ((collectiondate.getTime() - collectiondatebycalender.getTime()) / (1000 * 60 * 60 * 24));
//            Log.e("time",""+diffInDays);

//            Toast.makeText(BasketActivity.this, "time " +diffInDays, Toast.LENGTH_SHORT).show();
             ConstansApplication.COLLECTIONDATE = collectionDatetxt.getText().toString().trim();
            if (collectionDatetxt.getText().toString().equals("")) {

                Toast.makeText(BasketActivity.this, "Please select the collection date", Toast.LENGTH_SHORT).show();
                collectionTimetxt.setVisibility(View.GONE);
                deliveryTimetxt.setVisibility(View.GONE);

                collectiontimetext.setVisibility(View.VISIBLE);
                deliverytimetext.setVisibility(View.VISIBLE);
                collectionTimetxt.setEnabled(false);
                deliveryTimetxt.setEnabled(false);

            }

        }

        else if (clickeddateby.equals("deliveryDateListener")) {

            new_date = "" + dayOfMonth + "/" +  monthOfYear + "/" + year;

            String changeddate = "" + year + "-" + monthOfYear + "-" + dayOfMonth;
            SimpleDateFormat dfDate = new SimpleDateFormat("yyyy-MM-dd");

            deliverydate = null;
            Date d1 = null;

            try {
                deliverydate = dfDate.parse(changeddate);
                d1 = dfDate.parse(dfDate.format(newNow.getTime()));//Returns 15/10/2012

            } catch (java.text.ParseException e) {
                e.printStackTrace();
            }
            diffInDays = (int) ((deliverydate.getTime() - collectiondate.getTime()) / (1000 * 60 * 60 * 24));
//            Log.e("time", "" + diffInDays);

//            Toast.makeText(BasketActivity.this, "time " +diffInDays, Toast.LENGTH_SHORT).show();

            DateFormat inputFormat = new SimpleDateFormat("d/MM/yyyy");
            DateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy");
            String inputDateStr=new_date;
            Date date = null;
            try {
                date = inputFormat.parse(inputDateStr);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            formatdate1 = outputFormat.format(date);


            DateFormat inputFormat2 = new SimpleDateFormat("dd/MM/yyyy");
            DateFormat outputFormat2 = new SimpleDateFormat("MM/dd/yyyy");
            String inputDateStr2=formatdate1;
            Date date2 = null;
            try {
                date2 = inputFormat2.parse(inputDateStr2);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            deliveryDatetxt.setText(outputFormat2.format(date2));
            day_type="3";
            if (sizeOFdryclean==0){
                ConstansApplication.DELIVERYDATE = deliveryDatetxt.getText().toString().trim();
                String url = URLS.time_slot.concat(day_type).concat("&pick_date=").concat(formatdate)
                        .concat("&dry_clean_item=").concat("1").concat("&drop_date=").concat(formatdate1);
                timeslots(url);
            }else {
                ConstansApplication.DELIVERYDATE = deliveryDatetxt.getText().toString().trim();
                String url = URLS.time_slot.concat(day_type).concat("&pick_date=").concat(formatdate)
                        .concat("&dry_clean_item=").concat("0").concat("&drop_date=").concat(formatdate1);
                timeslots(url);
            }





            //          deliveryTimetxt.setEnabled(true);

            if (collectionDatetxt.getText().toString().equals("")) {

                Toast.makeText(BasketActivity.this, "Please select the collection date", Toast.LENGTH_SHORT).show();
                collectionTimetxt.setVisibility(View.GONE);
                deliveryTimetxt.setVisibility(View.GONE);

                collectiontimetext.setVisibility(View.VISIBLE);
                deliverytimetext.setVisibility(View.VISIBLE);
                collectionTimetxt.setEnabled(false);
                deliveryTimetxt.setEnabled(false);
            }
        }

    }

    private class GetLocationAsync extends AsyncTask<String, Void, String> {

        // boolean duplicateResponse;
        double x, y;
        StringBuilder str;

        public GetLocationAsync(double latitude, double longitude) {
            // TODO Auto-generated constructor stub

            x = latitude;
            y = longitude;
        }

        @Override
        protected void onPreExecute() {
           // collectionaddressedt.setText("Getting location");
        }

        @Override
        protected String doInBackground(String... params) {

            try {
                geocoder = new Geocoder(BasketActivity.this, Locale.ENGLISH);
                addresses = geocoder.getFromLocation(x, y, 1);
                str = new StringBuilder();
                if (geocoder.isPresent()) {
                    Address returnAddress = addresses.get(0);

                    String localityString = returnAddress.getLocality();
                    String city = returnAddress.getCountryName();
                    String region_code = returnAddress.getCountryCode();
                    String zipcode = returnAddress.getPostalCode();

                    str.append(localityString + "");
                    str.append(city + "" + region_code + "");
                    str.append(zipcode + "");


                } else {
                }
            } catch (Exception e) {
                Log.e("tag", e.getMessage());
            }
            return null;

        }

        @Override
        protected void onPostExecute(String result) {
            try {

                String ad = addresses.get(0).getAddressLine(0) + ", "
                        + addresses.get(0).getAddressLine(1) + " ";

              //  collectionaddressedt.setText(ad);


            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        protected void onProgressUpdate(Void... values) {

        }
    }


    public void checkvalues() {

      //  homeaddress = collectionaddressedt.getText().toString();
        ConstansApplication.DELIVERYNOTES = deliverynotesedt.getText().toString();
        ConstansApplication.COLLECTIONDATE = collectionDatetxt.getText().toString();
        //ConstansApplication.DELIVERYDATE = deliveryDatetxt.getText().toString();
        specialinstructions = specialInstructionedt.getText().toString();
        tip = tipValue.getText().toString();


//        int i = SameDayPriceLayout.getVisibility();
//        Log.e("visibility of layout",""+i);

//        if (i==0){
//            total_price_send = samedaytotal.getText().toString().trim();
//        }else {
            total_price_send = totalpricetxt.getText().toString().trim();
      //  }

        if (ConstansApplication.COLLECTIONDATE.equals("")){
            Toast.makeText(BasketActivity.this, "Please Enter Collection Date", Toast.LENGTH_SHORT).show();
        }else {

            if (firstCheckBox.isChecked()==true||SameDaySecondCheck.isChecked()) {
                 ConstansApplication.DELIVERYDATE = collectionDatetxt.getText().toString().trim();

            }else if (secondCheckBox.isChecked()==true||thirdCheckBox.isChecked()==true){
                String s= incrementDateByOne(formatdate);
                String[] s1 = s.split("/");
                String s2 = s1[0];
                String s3 = s1[1];
                String s4 = s1[2];
              //  int date = Integer.parseInt(s3);
              //  int date1 = date + 1;
            //    String ssss= String.valueOf(date1);
                ConstansApplication.DELIVERYDATE = s3+"/"+s2+"/"+s4;
                Log.e("delivery date",""+ConstansApplication.DELIVERYDATE);
            }
        }

        Log.e("collection date",""+ConstansApplication.COLLECTIONDATE);
        Log.e("collection date",""+ConstansApplication.COLLECTIONTIME);
        Log.e("collection date",""+ConstansApplication.DELIVERYDATE);
        Log.e("collection date",""+ConstansApplication.DELIVERYTIME);
        if (collectionaddressedt.getText().toString().equals("") || ConstansApplication.COLLECTIONDATE.equals("") ||
                ConstansApplication.COLLECTIONTIME.equals("") || ConstansApplication.DELIVERYDATE.equals("") ||
                ConstansApplication.DELIVERYTIME.equals("")) {

            Toast.makeText(BasketActivity.this, "Please Select Day Type", Toast.LENGTH_SHORT).show();
        } else if (sm.checkLogin()) {
//            Toast.makeText(BasketActivity.this, "login nahi hai", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(BasketActivity.this, HelloFacebookSampleActivity.class);
            startActivity(intent);

        }  else {

            Intent intent = new Intent(BasketActivity.this, EReceiptActivity.class);
            intent.putExtra("HOME_ADDRESS", collectionaddressedt.getText().toString());
            intent.putExtra("DELIVERY_NOTE", ConstansApplication.DELIVERYNOTES);
            intent.putExtra("PICKUP_DATE", ConstansApplication.COLLECTIONDATE);
            intent.putExtra("PICKUP_TIME", ConstansApplication.COLLECTIONTIME);
            intent.putExtra("DELIVERY_DATE", ConstansApplication.DELIVERYDATE);
            intent.putExtra("DELIVERY_TIME", ConstansApplication.DELIVERYTIME);
            intent.putExtra("SPECIAL_INSTRUCTIONS", specialinstructions);
            intent.putExtra("DTERENT_TYPE", selectedDetergent);
            intent.putExtra("DELIVERY_TYPE", selectedDelivery);
            intent.putExtra("tip", tip);
            intent.putExtra("daytype",day_type);
            intent.putExtra("totalprice", total_price_send);
            intent.putExtra("FROM" , FROM);
            startActivity(intent);

        }

   }


    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();

                getthetracker();

            } else {
                Toast.makeText(getApplicationContext(),
                        "This device is not supported.", Toast.LENGTH_LONG)
                        .show();
                finish();
            }
            return false;
        }
        return true;
    }

    private void loadlistview() {
        ct = dbm.getAllrows();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    for (int i = 0; i < ct.size(); i++) {
                        productid_arr.add(ct.get(i).getProductId());
                        productname_arr.add(ct.get(i).getProductaName());
                        product_no_of_unit.add(ct.get(i).getProductNoofunits());
                        product_price_arr.add(ct.get(i).getProductprice());
                        categoryid_arr.add(ct.get(i).getCategoryId());
                    }
                    handler.sendEmptyMessage(0);
                } catch (Exception e) {
                }
            }
        }).start();
    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, AbsListView.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

    private final Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            if (msg.what == 0) {

                Log.e("list of category",""+categoryid_arr);

                itemlistView.setAdapter(new AdapterForListItemForBasket(BasketActivity.this, productid_arr, productname_arr, specialinstructions,
                        product_no_of_unit, product_price_arr,categoryid_arr));
                setListViewHeightBasedOnChildren(itemlistView);
            } else if (msg.what == 1) {
            }

        }
    };





    private void timeslots(String Url ) {

        //   String faqurl = "http://keshavgoyal.com/laundry_app1/api/time_slot.php?flag=" +flag;

        String time_slot_url = Url;
        time_slot_url = time_slot_url.replace(" ", "%20");
        Log.e("time slots", "" + time_slot_url);

        sr = new StringRequest(Request.Method.POST, time_slot_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Sucess", "" + response);

                pDialog.dismiss();

                try {

                GsonBuilder gsonBuilder = new GsonBuilder();
                final Gson gson = gsonBuilder.create();
                PickupFromslots.clear();
                PickupToslots.clear();
                DropoffFromslots.clear();
                DropoffToslots.clear();
                pickup.clear();
                dropoff.clear();

                timeslot_setterGetter = gson.fromJson(response, Timeslot_SetterGetter.class);
                if (timeslot_setterGetter.getResult()==1) {

                    for (int i = 0; i < timeslot_setterGetter.getDetails().getPickup().size(); i++) {

                        PickupFromslots.add(timeslot_setterGetter.getDetails().getPickup().get(i).getPick_up());
                        PickupToslots.add(timeslot_setterGetter.getDetails().getPickup().get(i).getDrop_to());
                        pickup.add(PickupFromslots.get(i)+ " - " + PickupToslots.get(i));
                    }



                    for (int j = 0 ; j < timeslot_setterGetter.getDetails().getDrop().size(); j++){
                        DropoffFromslots.add(timeslot_setterGetter.getDetails().getDrop().get(j).getPick_up());
                        DropoffToslots.add(timeslot_setterGetter.getDetails().getDrop().get(j).getDrop_to());
                        dropoff.add(DropoffFromslots.get(j) + " - " + DropoffToslots.get(j));
                    }

                    collectiontimetext.setVisibility(View.GONE);
                    deliverytimetext.setVisibility(View.GONE);
                    collectionTimetxt.setVisibility(View.VISIBLE);
                    deliveryTimetxt.setVisibility(View.VISIBLE);

                   ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(BasketActivity.this,
                            android.R.layout.simple_spinner_item, pickup);
                   dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    collectionTimetxt.setAdapter(dataAdapter);
                    dataAdapter.setNotifyOnChange(true);

                    ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(BasketActivity.this,
                            android.R.layout.simple_spinner_item, dropoff);
                    dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    deliveryTimetxt.setAdapter(dataAdapter1);
                    dataAdapter.setNotifyOnChange(true);



                } else if (timeslot_setterGetter.getResult()==0){
                    time_slot_result_msg = gson.fromJson(response, time_slot_result.class);
                    Toast.makeText(getApplicationContext(), "Unavailable pickup / delivery date", Toast.LENGTH_SHORT).show();
                                pDialog.dismiss();
                    collectiontimetext.setVisibility(View.VISIBLE);
                    deliverytimetext.setVisibility(View.VISIBLE);
                    collectionTimetxt.setVisibility(View.GONE);
                    deliveryTimetxt.setVisibility(View.GONE);

                }else if (timeslot_setterGetter.getResult()==2){
                    time_slot_result_msg = gson.fromJson(response, time_slot_result.class);
                    Toast.makeText(getApplicationContext(), ""+time_slot_result_msg.msg, Toast.LENGTH_SHORT).show();
                    pDialog.dismiss();
                    collectiontimetext.setVisibility(View.VISIBLE);
                    deliverytimetext.setVisibility(View.VISIBLE);
                    collectionTimetxt.setVisibility(View.GONE);
                    deliveryTimetxt.setVisibility(View.GONE);

//                    NextDay_OverLayout.setVisibility(View.VISIBLE);
//                    NextDay_OverLayout.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            Toast.makeText(getApplicationContext(), ""+time_slot_result_msg.msg, Toast.LENGTH_SHORT).show();
//                        }
//                    });
//                    NextDay_First_OverLayout.setVisibility(View.VISIBLE);
//                    NextDay_First_OverLayout.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            Toast.makeText(getApplicationContext(), ""+time_slot_result_msg.msg, Toast.LENGTH_SHORT).show();
//                        }
//                    });

                }

                }catch (Exception e){
                    Log.e("exception",""+e);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Sucess", "" + error.toString());
                if (error instanceof NetworkError){
                    Toast.makeText(BasketActivity.this, "No Internet !!", Toast.LENGTH_SHORT).show();
                }else if (error instanceof NoConnectionError){
                    Toast.makeText(BasketActivity.this, "No Internet", Toast.LENGTH_SHORT).show();
                }else if (error instanceof TimeoutError){
                    Toast.makeText(BasketActivity.this, "Plz Try Again !!", Toast.LENGTH_SHORT).show();
                }
           pDialog.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }
        };
        sr.setRetryPolicy(new DefaultRetryPolicy(30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        pDialog.show();

        queue.add(sr);
    }


//    private void Priceofcategory() {
//
//        //   String faqurl = "http://keshavgoyal.com/laundry_app1/api/time_slot.php?flag=" +flag;
//
//        String time_slot_url = URLS.price_cat;
//        time_slot_url = time_slot_url.replace(" ", "%20");
//        Log.e("edce", "" + time_slot_url);
//
//        sr = new StringRequest(Request.Method.POST, time_slot_url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                //Log.e("Sucess", "" + response);
//
//                pDialog.dismiss();
//                GsonBuilder gsonBuilder = new GsonBuilder();
//                final Gson gson = gsonBuilder.create();
//
//                PriceOfCategory priceresult = new PriceOfCategory();
//
//                priceresult = gson.fromJson(response, PriceOfCategory.class);
//                if (priceresult.getResult()==1) {
//
//                for (int i= 0 ; i < priceresult.getMessage().size(); i++ ){
//
//                  price_catg_id.add(priceresult.getMessage().get(i).getCategory_id());
//                  price_catg_name.add(priceresult.getMessage().get(i).getCategory_name());
//                  price_catg_price.add(priceresult.getMessage().get(i).getWash_price());
//
//                }
//
//                   String pr = price_catg_price.get(0);
//                    pr = pr.replace("+", "");
//                    String pr1 = pr.replace("c","");
//
//                    firstchargetxt.setText("+"+pr1+"c per LB(Eco/Prime)");
//
//                    firstcharge= Double.parseDouble(pr1);
//                    Log.e("price of first",""+pr1);
//
//                    String pr2 = price_catg_price.get(2);
//                    pr2 = pr2.replace("+", "");
//                    String pr3 = pr2.replace("c","");
//                    secondchargetxt.setText("+"+pr3+"c per Dry Cleaning Item");
//                    secondcharge= Double.parseDouble(pr3);
//                    Log.e("price of second",""+secondcharge);
//
//                    calculationSameday(firstCheckBox,firstcharge,secondcharge);
//
//                } else {
//                    Toast.makeText(getApplicationContext(), "Please enter correct details", Toast.LENGTH_SHORT).show();
//                    pDialog.dismiss();
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Log.e("Sucess", "" + error.toString());
//                pDialog.dismiss();
//            }
//        }) {
//            @Override
//            protected Map<String, String> getParams() {
//                Map<String, String> params = new HashMap<String, String>();
//
//                return params;
//            }
//
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("Content-Type", "application/x-www-form-urlencoded");
//                return params;
//            }
//        };
//        sr.setRetryPolicy(new DefaultRetryPolicy(30000,
//                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        pDialog.show();
//
//        queue.add(sr);
//    }


//    private void RushDeliveryCharges(){
//
//        String deliverChrgs = URLS.RushDeliveryCharge;
//        deliverChrgs = deliverChrgs.replace(" ", "%20");
//        Log.e("edce", "" + deliverChrgs);
//
//        sr = new StringRequest(Request.Method.POST, deliverChrgs, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                //Log.e("Sucess", "" + response);
//
//                pDialog.dismiss();
//                GsonBuilder gsonBuilder = new GsonBuilder();
//                final Gson gson = gsonBuilder.create();
//
//                RushDeliveryCharge deliveryCharge = new RushDeliveryCharge();
//
//                deliveryCharge = gson.fromJson(response, RushDeliveryCharge.class);
//                if (deliveryCharge.getResult()==1) {
//
//                     String s= deliveryCharge.getMessage().getDelivery_charge() ;
//                    String ss = s.substring(1);
//                    double d = Double.parseDouble(ss);
//
//                    firstchargetxt.setText("+ "+df.format(d) + " Rush Delivery");
//                    RushChages = df.format(d);
//
////                    String pr = price_catg_price.get(0);
////                    pr = pr.replace("+", "");
////                    String pr1 = pr.replace("c","");
////
////                    firstchargetxt.setText("+"+pr1+"c per LB(Eco/Prime)");
////
////                    firstcharge= Double.parseDouble(pr1);
////                    Log.e("price of first",""+pr1);
////
////                    String pr2 = price_catg_price.get(2);
////                    pr2 = pr2.replace("+", "");
////                    String pr3 = pr2.replace("c","");
////                    secondchargetxt.setText("+"+pr3+"c per Dry Cleaning Item");
////                    secondcharge= Double.parseDouble(pr3);
////                    Log.e("price of second",""+secondcharge);
////
////                    calculationSameday(firstCheckBox,firstcharge,secondcharge);
//
//                } else {
//                    Toast.makeText(getApplicationContext(), "Please enter correct details", Toast.LENGTH_SHORT).show();
//                    pDialog.dismiss();
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Log.e("Sucess", "" + error.toString());
//                if (error instanceof NetworkError){
//                    Toast.makeText(BasketActivity.this, "No Internet !!", Toast.LENGTH_SHORT).show();
//                }else if (error instanceof NoConnectionError){
//                    Toast.makeText(BasketActivity.this, "No Internet", Toast.LENGTH_SHORT).show();
//                }else if (error instanceof TimeoutError){
//                    Toast.makeText(BasketActivity.this, "Plz Try Again !!", Toast.LENGTH_SHORT).show();
//                }
//                pDialog.dismiss();
//            }
//        }) {
//            @Override
//            protected Map<String, String> getParams() {
//                Map<String, String> params = new HashMap<String, String>();
//
//                return params;
//            }
//
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("Content-Type", "application/x-www-form-urlencoded");
//                return params;
//            }
//        };
//        sr.setRetryPolicy(new DefaultRetryPolicy(30000,
//                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        pDialog.show();
//
//        queue.add(sr);
//
//
//
//    }


//    @Override
//    protected void onStop() {
//        super.onStop();
//        client.disconnect();
//    }


//    private void askForGPS() {
//        mLocationRequest = LocationRequest.create();
//        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
//        mLocationRequest.setInterval(30 * 1000);
//        mLocationRequest.setFastestInterval(5 * 1000);
//        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(mLocationRequest);
//        builder.setAlwaysShow(true);
//        result = LocationServices.SettingsApi.checkLocationSettings(client, builder.build());
//        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
//            @Override
//            public void onResult(LocationSettingsResult result) {
//                final Status status = result.getStatus();
//                switch (status.getStatusCode()) {
//                    case LocationSettingsStatusCodes.SUCCESS:
//                        break;
//                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
//                        try {
//                            status.startResolutionForResult(BasketActivity.this, 12);
//                        } catch (IntentSender.SendIntentException e) {
//
//                        }
//                        break;
//                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
//                        break;
//                }
//            }
//        });
//
//
//    }

    public String incrementDateByOne(String sdate) {
       // String sDate = "31012014";
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        Date date = null;
        try {
            date = dateFormat.parse(sdate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, 1);
        String yesterdayAsString = dateFormat.format(calendar.getTime());
        return yesterdayAsString ;
    }

}