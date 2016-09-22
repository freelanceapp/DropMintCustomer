package com.apporio.johnlaundry.BasketModule;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.apporio.johnlaundry.settergetter.PriceOfCategory;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationServices;
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
        OnConnectionFailedListener, DatePickerDialog.OnDateSetListener  {

    String clickeddateby;


    private static final int DEFAULTZOOM = 15;

    String homeaddress,  specialinstructions;

    @Bind(R.id.list_view_in_item_for_basket)
    ListView itemlistView;
    @Bind(R.id.back_button_on_action_bar)
    LinearLayout backbtn;
    @Bind(R.id.activity_name_on_Action_bar)
    TextView activityName;
    @Bind(R.id.your_item_layout_in_basket_activity)
    LinearLayout addmoreitembtn;
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
    EditText collectionaddressedt;

////// same layot with different can be sed in case of defferent delivery address

    @Bind(R.id.delivery_date_in_basket_activiy_txt)TextView  deliveryDatetxt;
    @Bind(R.id.delivery_time_in_basket_activity)
    LinearLayout deliveryTimeBtn;
    @Bind(R.id.delivery_date_in_basket_activity)LinearLayout deliveryDateBtn;
    @Bind(R.id.collection_date_iun_basket_activity)LinearLayout collectionDateBtn;
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

    @Bind(R.id.sameday_no_of_items)
    TextView samedayitems;
    @Bind(R.id.sameday_gross_total)
    TextView samedaytotal;
    @Bind(R.id.samedaypricelayout)
    LinearLayout SameDayPriceLayout;
    @Bind(R.id.totalpricelayout)
    LinearLayout total_priceLinearLayout;
    @Bind(R.id.llfordropoffdate)
    LinearLayout dropDate;
    /////////////////////////////////////////////////////////////

    DBManager dbm;

    SessionManager sm;
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 1000;
    private Location mLastLocation;
    private GoogleApiClient mGoogleApiClient;

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

    Calendar now;
    DatePickerDialog dpd;
    Calendar newNow;
    String month;
    String new_date;
    String selected_collection_time,selected_delivery_time,new_collection_date;
    Date collectiondate,collectiondatebycalender,deliverydate;
    int diffInDays;
    String abc;

    @Bind(R.id.samedayoverlaylayout)
    LinearLayout SameDay_OverLayout;
    @Bind(R.id.nextdayoverlaylayout)
    LinearLayout NextDay_OverLayout;

    int Hour;
    EditText tipValue;
    String selectedDetergent = "Scented", selectedDelivery = "Leave At Door";
    String tip = "0";

    ///////////////////
    public static MapView mapView;
    public static Context ctc;
    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 0;
    public static GoogleMap map;
    public static GPStracker gps;
    public static Double currentlat, currentlong;
    public static Marker now1;
    private Geocoder geocoder;
    private List<Address> addresses;

    ScrollView scrollView;

    ////////////////////////////////
    public static List<CartTable> carttablecategory;
    CheckBox firstCheckBox,secondCheckBox,thirdCheckBox,forthCheckBox;
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

    double firstcharge , secondcharge ;

    String day_type;
    String total_price_send;

    ArrayList<String> dropoff = new ArrayList<>();
    ArrayList<String> pickup = new ArrayList<>();
    DecimalFormat df;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basket);

        ButterKnife.bind(this);
        ctc = getApplicationContext();
        queue = VolleySingleton.getInstance(BasketActivity.this).getRequestQueue();

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

        currentlat = gps.getLatitude();
        currentlong = gps.getLongitude();


        Calendar c = Calendar.getInstance();
        Hour = c.get(Calendar.HOUR_OF_DAY);

        if (!sm.checkLogin()) {

            HashMap<String, String> user = sm.getUserDetails();

            homeaddress = user.get(SessionManager.KEY_Address);
        }

        collectionaddressedt.setText(homeaddress);
        totalnoOfitemtxt.setText("" + dbm.totalNoofitemsincar());
        totalpricetxt.setText("" + dbm.calculationForGrossPrice());

///////////////////////////////////////////////////////

        firstCheckBox=(CheckBox)findViewById(R.id.checkbox1);
        secondCheckBox=(CheckBox)findViewById(R.id.checkbox2);
        thirdCheckBox=(CheckBox)findViewById(R.id.checkbox3);
        forthCheckBox=(CheckBox)findViewById(R.id.checkbox4);

     //   firstCheckBox.setChecked(true);
     //   day_type="0";
        Priceofcategory();

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

        Log.e("day of week",""+dayOfTheWeek);

                if (dayOfTheWeek.equals("Sunday")){
            SameDay_OverLayout.setVisibility(View.VISIBLE);
            SameDay_OverLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(BasketActivity.this, "Sunday Will Be Shut Down !!", Toast.LENGTH_SHORT).show();
                }
            });
        }else {
                    SameDay_OverLayout.setVisibility(View.GONE);
                }


        if (dayOfTheWeek.equals("Saturday")){
            NextDay_OverLayout.setVisibility(View.VISIBLE);
            NextDay_OverLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(BasketActivity.this, "Not available for pickup on Saturdays !!", Toast.LENGTH_SHORT).show();
                }
            });
        }else {
            NextDay_OverLayout.setVisibility(View.GONE);
        }


//        if (dayOfTheWeek.equals("Monday")){
//            Toast.makeText(BasketActivity.this, "We Are Closed On Sunday !!", Toast.LENGTH_SHORT).show();
//        }else {
            firstCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked==true){
                        timeslots("0");
                        day_type="0";
                        secondCheckBox.setChecked(false);
                        thirdCheckBox.setChecked(false);
                        forthCheckBox.setChecked(false);
                        SameDayPriceLayout.setVisibility(View.VISIBLE);
                        total_priceLinearLayout.setVisibility(View.GONE);
                        dropDate.setVisibility(View.GONE);

                        calculationSameday(firstCheckBox,firstcharge,secondcharge);
                    }else {

                    }
                }
            });
     //   }
        


        secondCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked==true){
                    timeslots("1");
                    day_type="1";
                    firstCheckBox.setChecked(false);
                    thirdCheckBox.setChecked(false);
                    forthCheckBox.setChecked(false);
                    SameDayPriceLayout.setVisibility(View.GONE);
                    total_priceLinearLayout.setVisibility(View.VISIBLE);
                    dropDate.setVisibility(View.GONE);



                }else {

                }

            }
        });

//        if (dayOfTheWeek.equals("Saturday")){
//            Toast.makeText(BasketActivity.this, "Not available for pickup on Saturdays !!", Toast.LENGTH_SHORT).show();
//        }else {
            thirdCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    

                    if (isChecked==true){
                        timeslots("2");
                        day_type="2";
                        firstCheckBox.setChecked(false);
                        secondCheckBox.setChecked(false);
                        forthCheckBox.setChecked(false);
                        SameDayPriceLayout.setVisibility(View.GONE);
                        total_priceLinearLayout.setVisibility(View.VISIBLE);
                        dropDate.setVisibility(View.GONE);

                    }else {
                        SameDayPriceLayout.setVisibility(View.GONE);
                        total_priceLinearLayout.setVisibility(View.VISIBLE);
                    }

                }
            }); 
    //    }
        
        
      
        String s= "  Select Date";
       // String s1="Date";
        forthCheckBox.setText(s);
        forthCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked==true){
                    timeslots("3");
                    day_type="3";
                    firstCheckBox.setChecked(false);
                    secondCheckBox.setChecked(false);
                    thirdCheckBox.setChecked(false);
                    dropDate.setVisibility(View.VISIBLE);
                    SameDayPriceLayout.setVisibility(View.GONE);
                    total_priceLinearLayout.setVisibility(View.VISIBLE);
                    ConstansApplication.DELIVERYDATE = "";
                }else {
                    SameDayPriceLayout.setVisibility(View.GONE);
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


        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            try {

                String permission = "android.permission.READ_PHONE_STATE";

                int permissionCheck = ContextCompat.checkSelfPermission(BasketActivity.this,
                        Manifest.permission.ACCESS_FINE_LOCATION);

                if (ContextCompat.checkSelfPermission(BasketActivity.this,
                        Manifest.permission_group.LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {

                    // Should we show an explanation?
                    if (ActivityCompat.shouldShowRequestPermissionRationale(BasketActivity.this,
                            Manifest.permission_group.LOCATION)) {
                        ActivityCompat.requestPermissions(BasketActivity.this,
                                new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                                MY_PERMISSIONS_REQUEST_READ_CONTACTS);

                        Toast.makeText(getApplicationContext(), "Please allow phone permission to receive push notifications !!!", Toast.LENGTH_LONG).show();


                    } else {


                        getthetracker();
                    }
                } else {
                    getthetracker();
                }
            } catch (Exception e) {
                Log.e("ghghghhg", "" + e);
            }
        } else {
            ActivityCompat.requestPermissions(BasketActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    MY_PERMISSIONS_REQUEST_READ_CONTACTS);
            getthetracker();
        }



        loadlistview();


        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
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



    View.OnClickListener   collectionDatelistener  = new View.OnClickListener() {
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










    public double calculationSameday(CheckBox chk, double cahrge1, double charge2){

        double grossfortwo = Double.valueOf(productsListCategory(price_catg_id.get(0),price_catg_id.get(1)));
        Log.e("gross bill for two",""+grossfortwo);
        double charge = cahrge1 ;
        Log.e("charge for eco and prime",""+cahrge1);
        double grosswithcharges =  grossfortwo + grossfortwo * charge ;

        Log.e("total of eco and prime",""+grosswithcharges);
        totalcartamount = dbm.calculationForGrossPrice();
        Log.e("total cart amount",""+totalcartamount);
        double chargesofdryclean = totalcartamount - grossfortwo ;

        Log.e("price of dry clean products",""+chargesofdryclean) ;

        double charge_dryclean = charge2;

        double totalfordryclean = chargesofdryclean + chargesofdryclean * charge_dryclean ;
        Log.e("total of dry clean with tax",""+totalfordryclean);

        double subtotalofall = grosswithcharges + totalfordryclean ;


        if (chk.isChecked()==true){
            SameDayPriceLayout.setVisibility(View.VISIBLE);
            samedayitems.setText(totalnoOfitemtxt.getText().toString().trim());
            double dd= subtotalofall+d1;
            total_price_send = String.valueOf(dd);
            samedaytotal.setText(""+df.format(dd));

        }else {
            SameDayPriceLayout.setVisibility(View.GONE);
            total_price_send = totalpricetxt.getText().toString().trim();
        }

        return subtotalofall;

    }




    private int productsListCategory(String id,String id1) {
        Integer price  , total , total1 = 0;
        int noofitem;
        carttablecategory=dbm.getproductsaccordingcategory(id,id1);

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
                        noofitem = Integer.parseInt(carttablecategory.get(i).getProductNoofunits());
                        total = noofitem * price ;
                        total1 = total1 + total ;
                    }

                } catch (Exception e) {
                }

        Log.e("prices",""+product_price_arr_cat);
        Log.e("cat iddddd",""+product_cat);
        Log.e("product iddddd",""+product_id_cat);

        return total1 ;

    }






    public void onEvent(CartEvent event) {
        samedayunit = event.getDatatotalitems();
        totalnoOfitemtxt.setText(samedayunit);
        totalpricetxt.setText("" +df.format(event.getDatagrosstotal()));

        Log.e("unit of items",""+samedayunit);
        totalcartamount = dbm.calculationForGrossPrice();

    }


    final TextWatcher tipwatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            if (firstCheckBox.isChecked()==true){
                double dd =   calculationSameday(firstCheckBox,firstcharge,secondcharge);
                double ddd= dd + 2.0;
                samedaytotal.setText("" +df.format(ddd) );
                total_price_send = String.valueOf(ddd);
            }else {
                totalpricetxt.setText("" + df.format(dbm.calculationForGrossPrice()));
                total_price_send = String.valueOf(dbm.calculationForGrossPrice());
            }


        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            if (s.length() != 0) {

                if (firstCheckBox.isChecked()==true){
                  double d = Double.parseDouble(String.valueOf(s));
                  double dd =   calculationSameday(firstCheckBox,firstcharge,secondcharge);
                  double ddd= d+dd;
                  total_price_send = String.valueOf(ddd);
                  samedaytotal.setText("" +df.format(ddd));
                }else {
                    Double d = Double.parseDouble(String.valueOf(s));
                    Double dd = dbm.calculationForGrossPrice() + d;
                    total_price_send = String.valueOf(dd);
                    totalpricetxt.setText("" +df.format(dd) );
                }

            } else {
                totalpricetxt.setText("" +df.format(dbm.calculationForGrossPrice()) );
                total_price_send = String.valueOf(dbm.calculationForGrossPrice());
            }


        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };


    @Override
    protected void onStart() {
        super.onStart();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }

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
            case MY_PERMISSIONS_REQUEST_READ_CONTACTS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Toast.makeText(BasketActivity.this, "permission granted", Toast.LENGTH_SHORT).show();
//                    TelephonyManager mngr = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
//                    String dd = mngr.getDeviceId();

                    getthetracker();
//                    parsingfornotification.parsing(MainActivity.this, ""+dd, regId);
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {
                    Toast.makeText(BasketActivity.this, "permission denied", Toast.LENGTH_SHORT).show();
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
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

        if (monthOfYear==1){
            month= "Jan";
        }
        else if(monthOfYear==2){
            month = "Feb";
        }
        else if(monthOfYear==3){
            month = "Mar";
        }
        else if(monthOfYear==4){
            month = "Apr";
        }
        else if(monthOfYear==5){
            month = "May";
        }
        else if(monthOfYear==6){
            month = "June";
        }
        else if(monthOfYear==7){
            month = "July";
        }
        else if(monthOfYear==8){
            month = "Aug";
        }
        else if(monthOfYear==9){
            month = "Sep";
        }
        else if(monthOfYear==10){
            month = "Oct";
        }
        else if(monthOfYear==11){
            month = "Nov";
        }
        else if(monthOfYear==12){
            month = "Dec";
        }


        if(clickeddateby.equals("collectionDatelistener")) {


            new_date = "" + month + " " + dayOfMonth + " " + year;


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

            collectionDatetxt.setText(new_date);
            diffInDays = (int) ((collectiondate.getTime() - collectiondatebycalender.getTime()) / (1000 * 60 * 60 * 24));
//            Log.e("time",""+diffInDays);

//            Toast.makeText(BasketActivity.this, "time " +diffInDays, Toast.LENGTH_SHORT).show();
             ConstansApplication.COLLECTIONDATE = new_date;
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

            new_date = "" + month + " " + dayOfMonth + " " + year;

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
            deliveryDatetxt.setText(new_date);
            ConstansApplication.DELIVERYDATE = new_date;
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
            collectionaddressedt.setText("Getting location");
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

                collectionaddressedt.setText(ad);


            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        protected void onProgressUpdate(Void... values) {

        }
    }


    public void checkvalues() {

        homeaddress = collectionaddressedt.getText().toString();
        ConstansApplication.DELIVERYNOTES = deliverynotesedt.getText().toString();
        ConstansApplication.COLLECTIONDATE = collectionDatetxt.getText().toString();
        //ConstansApplication.DELIVERYDATE = deliveryDatetxt.getText().toString();
        specialinstructions = specialInstructionedt.getText().toString();
        tip = tipValue.getText().toString();


        int i = SameDayPriceLayout.getVisibility();
        Log.e("visibility of layout",""+i);

        if (i==0){
            total_price_send = samedaytotal.getText().toString().trim();
        }else {
            total_price_send = totalpricetxt.getText().toString().trim();
        }

        if (ConstansApplication.COLLECTIONDATE.equals("")){
            Toast.makeText(BasketActivity.this, "Please Enter Collection Date", Toast.LENGTH_SHORT).show();
        }else {

            if (firstCheckBox.isChecked()==true) {
                 ConstansApplication.DELIVERYDATE = collectionDatetxt.getText().toString().trim();

            }else if (secondCheckBox.isChecked()==true||thirdCheckBox.isChecked()==true){
                String s= collectionDatetxt.getText().toString().trim();
                String[] s1 = s.split(" ");
                String s2 = s1[0];
                String s3 = s1[1];
                String s4 = s1[2];
                int date = Integer.parseInt(s3);
                int date1 = date + 1;
                String ssss= String.valueOf(date1);
                ConstansApplication.DELIVERYDATE = s2 + " " + ssss + " " + s4;
                Log.e("delivery date",""+ConstansApplication.DELIVERYDATE);
            }
        }


        if (homeaddress.equals("") || ConstansApplication.COLLECTIONDATE.equals("") ||
                ConstansApplication.COLLECTIONTIME.equals("") || ConstansApplication.DELIVERYDATE.equals("") ||
                ConstansApplication.DELIVERYTIME.equals("")) {

            Toast.makeText(BasketActivity.this, "Please complete all details", Toast.LENGTH_SHORT).show();
        } else if (sm.checkLogin()) {
//            Toast.makeText(BasketActivity.this, "login nahi hai", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(BasketActivity.this, HelloFacebookSampleActivity.class);
            startActivity(intent);

        } else if (itemlistView.getChildCount() == 0) {
            Toast.makeText(BasketActivity.this, "No item selected", Toast.LENGTH_SHORT).show();

        } else {

            Intent intent = new Intent(BasketActivity.this, EReceiptActivity.class);
            intent.putExtra("HOME_ADDRESS", homeaddress);
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





    private void timeslots(String flag) {

        //   String faqurl = "http://keshavgoyal.com/laundry_app1/api/time_slot.php?flag=" +flag;

        String time_slot_url = URLS.time_slot.concat(flag);
        time_slot_url = time_slot_url.replace(" ", "%20");
        Log.e("", "" + time_slot_url);

        sr = new StringRequest(Request.Method.POST, time_slot_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Log.e("Sucess", "" + response);

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



                } else {
                    Toast.makeText(getApplicationContext(), "Please enter correct details", Toast.LENGTH_SHORT).show();
                                pDialog.dismiss();
                }

                }catch (Exception e){
                    Log.e("exception",""+e);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Sucess", "" + error.toString());
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


    private void Priceofcategory() {

        //   String faqurl = "http://keshavgoyal.com/laundry_app1/api/time_slot.php?flag=" +flag;

        String time_slot_url = URLS.price_cat;
        time_slot_url = time_slot_url.replace(" ", "%20");
        Log.e("edce", "" + time_slot_url);

        sr = new StringRequest(Request.Method.POST, time_slot_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Log.e("Sucess", "" + response);

                pDialog.dismiss();
                GsonBuilder gsonBuilder = new GsonBuilder();
                final Gson gson = gsonBuilder.create();

                PriceOfCategory priceresult = new PriceOfCategory();

                priceresult = gson.fromJson(response, PriceOfCategory.class);
                if (priceresult.getResult()==1) {

                for (int i= 0 ; i < priceresult.getMessage().size(); i++ ){

                  price_catg_id.add(priceresult.getMessage().get(i).getCategory_id());
                  price_catg_name.add(priceresult.getMessage().get(i).getCategory_name());
                  price_catg_price.add(priceresult.getMessage().get(i).getWash_price());

                }

                   String pr = price_catg_price.get(0);
                    pr = pr.replace("+", "");
                    String pr1 = pr.replace("c","");
                    firstcharge= Double.parseDouble(pr1);
                    Log.e("price of first",""+pr1);

                    String pr2 = price_catg_price.get(2);
                    pr2 = pr2.replace("+", "");
                    String pr3 = pr2.replace("c","");
                    secondcharge= Double.parseDouble(pr3);
                    Log.e("price of second",""+secondcharge);

                    calculationSameday(firstCheckBox,firstcharge,secondcharge);

                } else {
                    Toast.makeText(getApplicationContext(), "Please enter correct details", Toast.LENGTH_SHORT).show();
                    pDialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Sucess", "" + error.toString());
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



}