package com.apporio.johnlaundry.BasketModule;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;
import com.apporio.johnlaundry.R;
import com.apporio.johnlaundry.database.CartTable;
import com.apporio.johnlaundry.settergetter.OrderPlacedSetterGetter;
import com.apporio.johnlaundry.settergetter.PaypalResponse_setter_getter;
import com.apporio.johnlaundry.startUpScreen.MainActivityWithicon;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.apporio.johnlaundry.adapters.AdapterForEReceiptListActivity;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.apporio.johnlaundry.database.DBManager;
import com.apporio.johnlaundry.logger.Logger;
import com.apporio.johnlaundry.utils.NetworkChecker;
import com.apporio.johnlaundry.utils.SessionManager;
import com.apporio.johnlaundry.utils.URLS;
import com.apporio.johnlaundry.utils.VolleySingleton;

public class EReceiptActivity extends Activity {

    @Bind(R.id.list_in_ereceipt_Activity) ListView list_of_all_items;
    @Bind(R.id.payment_layout_in_receipt_activity)LinearLayout changepaymentmethodbtn;
    @Bind(R.id.back_button_on_action_bar)LinearLayout backbtn;
    @Bind(R.id.payment_text_in_receipt_activiy)TextView paymenttxt;
    @Bind(R.id.activity_name_on_Action_bar)TextView activityname;
    @Bind(R.id.total_no_items_in_e_receipt_activity)TextView totalNoOfitmstxt;
    @Bind(R.id.placeorder)TextView placeordertext;

    @Bind(R.id.pickup_address)TextView pickupaddresstxt;
    @Bind(R.id.delivery_address)TextView deliveryaddresstxt;
    @Bind(R.id.collection_date)TextView pickupdatetxt;
    @Bind(R.id.collection_time)TextView pickuptimetxt;
    @Bind(R.id.delivery_date)TextView deliverydatetxt;
    @Bind(R.id.delivery_time)TextView deliverytimetxt;
    @Bind(R.id.delivery_note_inreceipt)TextView deliverynotetxt_receipt;
   // @Bind(R.id.specialinstructions)TextView specialinstructionstxt;
    @Bind(R.id.total_amount)TextView totalPrice;
    @Bind(R.id.tiptosp)TextView tip;
    @Bind(R.id.dtgtype)TextView detergenttype;
    @Bind(R.id.dlytype)TextView delivertype;


    public static List<CartTable> carttable;

    public static OrderPlacedSetterGetter orderPlacedSetterGetter= new OrderPlacedSetterGetter();
    public static String homeaddress,collection_date,collection_time,delivery_date,delivery_time,delivery_note_Receipt,special_instructions,Detergetnt_Type,Delivery_Type;

//    private static PayPalConfiguration config = new PayPalConfiguration()
//
//            // Start with mock environment.  When ready, switch to sandbox (ENVIRONMENT_SANDBOX)
//            // or live (ENVIRONMENT_PRODUCTION)
//            .environment(PayPalConfiguration.ENVIRONMENT_PRODUCTION)
//            .clientId("AeyLNV3-u58H4pfu0xM3ueA-QyFulNOSzVhU6XDjWL4PZ_6_EB21LjDv7X1A_j572Hi9Mnw_1_8x0kHx");

    private static final String CONFIG_ENVIRONMENT = PayPalConfiguration.ENVIRONMENT_NO_NETWORK;
    private static final String CONFIG_CLIENT_ID = "AFcWxV21C7fd0v3bYYYRCpSSRl31AW.nrY8UUmkTDBx-TSEQlHYBvptc";


    private static PayPalConfiguration config = new PayPalConfiguration()
            .environment(CONFIG_ENVIRONMENT)
            .clientId(CONFIG_CLIENT_ID);


    DBManager dbm ;
    SessionManager sm;
    StringRequest sr;
    RequestQueue queue;
    String userid;


    public static ArrayList<String> productid_arr = new ArrayList<String>();
    public static ArrayList<String>   productname_arr = new ArrayList<String>();
    public static ArrayList<String>   product_no_of_unit = new ArrayList<String>();
    public static ArrayList<String>   product_price_arr = new ArrayList<String>();
    public static ProgressDialog pDialog;
    String tipPrice,totalpricewithtip,day_type;
    String baseurl;
    public static ArrayList<String> paypalResponce = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ereceipt);
        ButterKnife.bind(this);

        sm = new SessionManager(EReceiptActivity.this);
        queue = VolleySingleton.getInstance(EReceiptActivity.this).getRequestQueue();
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);

        activityname.setText("E-receipt");
        dbm = new DBManager(EReceiptActivity.this);

        //carttable.clear();
        totalNoOfitmstxt.setText("" + dbm.totalNoofitemsincar());


        // get user data from session
        HashMap<String, String> user = sm.getUserDetails();

        // get name
        userid = user.get(SessionManager.KEY_UserId);


        loadlistview();

        Intent intent = getIntent();

        homeaddress = intent.getStringExtra("HOME_ADDRESS");
        delivery_note_Receipt = intent.getStringExtra("DELIVERY_NOTE");
        collection_date = intent.getStringExtra("PICKUP_DATE");
        collection_time = intent.getStringExtra("PICKUP_TIME");
        delivery_date = intent.getStringExtra("DELIVERY_DATE");
        delivery_time = intent.getStringExtra("DELIVERY_TIME");
      //  special_instructions = intent.getStringExtra("SPECIAL_INSTRUCTIONS");
        Delivery_Type=intent.getStringExtra("DELIVERY_TYPE");
        Detergetnt_Type=intent.getStringExtra("DTERENT_TYPE");
        tipPrice=intent.getStringExtra("tip");
        totalpricewithtip=intent.getStringExtra("totalprice");
        day_type = intent.getStringExtra("daytype");

        pickupaddresstxt.setText(homeaddress);
        deliveryaddresstxt.setText(homeaddress);
        pickupdatetxt.setText(collection_date);
        pickuptimetxt.setText(collection_time);
        deliverydatetxt.setText(delivery_date);
        deliverytimetxt.setText(delivery_time);
        deliverynotetxt_receipt.setText(delivery_note_Receipt);
       // specialinstructionstxt.setText(special_instructions);
        tip.setText(tipPrice);
        detergenttype.setText(Detergetnt_Type);
        delivertype.setText(Delivery_Type);
        totalPrice.setText(totalpricewithtip);

        Log.e("data",""+totalpricewithtip + delivery_time + delivery_date +collection_date+ collection_time);


        changepaymentmethodbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSingleChoisePayment();
            }
        });

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        try {
            Intent intenttopaypal = new Intent(this, PayPalService.class);

            intenttopaypal.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);

            startService(intenttopaypal);
        } catch (Exception e) {
            e.printStackTrace();
        }
                placeordertext.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            if (paymenttxt.getText().toString().trim().equals("")){
                Toast.makeText(EReceiptActivity.this, "Please Select Payment Method", Toast.LENGTH_SHORT).show();
            }
            else if (paymenttxt.getText().toString().trim().equals("PayPal")){

                String finalprice = totalPrice.getText().toString().trim();
                Toast.makeText(EReceiptActivity.this, "Paypal" + finalprice, Toast.LENGTH_SHORT).show();

                if (!finalprice.equals("0")) {
                    PayPalPayment payment = new PayPalPayment(new BigDecimal(String.valueOf(finalprice)), "GBP", "Pay",
                            PayPalPayment.PAYMENT_INTENT_SALE);
                    Intent intent = new Intent(EReceiptActivity.this, PaymentActivity.class);
                    // send the same configuration for restart resiliency
                    intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
                    intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment);
                    startActivityForResult(intent, 0);                } else {
                    Toast.makeText(EReceiptActivity.this, "No Item Selected", Toast.LENGTH_SHORT).show();
                }

            }
            else {
                checkNetworkState();
             //   getJsonScriptForFullCart();

            //    Toast.makeText(EReceiptActivity.this, "cash", Toast.LENGTH_SHORT).show();

            }

        }
    });

    }

    @Override
    protected void onActivityResult (int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            PaymentConfirmation confirm = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
            if (confirm != null) {
                try {
                    Log.i("paymentExample", confirm.toJSONObject().toString(4));

                    String response =confirm.toJSONObject().toString(4);

                    Log.e("pay pal ",""+response);

                    PaypalResponse_setter_getter paypalresponse  = new PaypalResponse_setter_getter();
                    Gson gson  = new Gson();

                    paypalresponse =  gson.fromJson(response, PaypalResponse_setter_getter.class);

                    if(paypalresponse!=null)
                    {

                        Log.e("create time",""+paypalresponse.response.create_time);
                        Log.e("ids",""+paypalresponse.response.ids);
                        Log.e("intents",""+paypalresponse.response.intents);
                        Log.e("state", "" + paypalresponse.response.state);
                       // Log.e("platform",""+paypalresponse.response.platform);

                        homeaddress = homeaddress.replaceAll(",","");


                        Log.e("adjcha",homeaddress);
                     //   baseurl = "http://keshavgoyal.com/laundry_app1/api/order.php?pickup_address=";



                        String cartorderurl= URLS.order_url.concat(userid).concat(URLS.order_url1).concat(Delivery_Type).concat(URLS.order_url2)
                                .concat(Detergetnt_Type).concat(URLS.order_url3).concat(homeaddress).concat(URLS.order_url4).concat(collection_date)
                                .concat(URLS.order_url5).concat(collection_time).concat(URLS.order_url6).concat(homeaddress).concat(URLS.order_url7)
                                .concat(delivery_date).concat(URLS.order_url8).concat(delivery_time).concat(URLS.order_url9)
                                .concat(String.valueOf(getTotalPrice())).concat(URLS.order_url10).concat(delivery_note_Receipt).concat(URLS.order_url11)
                                .concat(" ").concat(URLS.order_url12).concat(String.valueOf(totalpricewithtip)).concat(URLS.order_url13)
                                .concat(String.valueOf(dbm.totalNoofitemsincar())).concat(URLS.order_url14).concat(String.valueOf(dbm.totalNoofitemsincar()))
                                .concat(URLS.order_url15).concat("paypal").concat(URLS.order_url16).concat(paypalresponse.response.state)
                                .concat(URLS.order_url17).concat(String.valueOf(totalpricewithtip)).concat(URLS.order_url18).concat(paypalresponse.response.create_time)
                                .concat(URLS.order_url19).concat(paypalresponse.response.ids).concat(URLS.order_url20).concat("Android").concat(URLS.order_url21).concat("0").concat(URLS.order_url22).concat(tipPrice)
                                .concat(URLS.order_url23).concat(day_type);


                        cartorderurl =  cartorderurl.replace(" ","%20");
                        cartorderurl = cartorderurl.replaceAll("\n", "%20");


                        Pypalorder(cartorderurl);

                    }

                    else
                    {
                        Toast.makeText(EReceiptActivity.this, "No Responce ", Toast.LENGTH_SHORT).show();
                    }

                    // TODO: send 'confirm' to your server for verification.
                    // see https://developer.paypal.com/webapps/developer/docs/integration/mobile/verify-mobile-payment/
                    // for more details.

                } catch (JSONException e) {
                    Log.e("paymentExample", "an extremely unlikely failure occurred: ", e);
                }
            }
        }
        else if (resultCode == Activity.RESULT_CANCELED) {
            Log.e("paymentExample", "The user canceled.");
            Toast.makeText(EReceiptActivity.this, "Cancelled ", Toast.LENGTH_SHORT).show();
        }
        else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {

            Toast.makeText(EReceiptActivity.this, "Invalid Payment ", Toast.LENGTH_SHORT).show();
            Log.e("paymentExample", "An invalid Payment or PayPalConfiguration was submitted. Please see the docs.");
        }
    }
    private void generatesecreenmessage(String s) {
        Toast.makeText(EReceiptActivity.this, "" + s, Toast.LENGTH_SHORT).show();
    }

    private boolean checkNetworkState() {
        if(new NetworkChecker().isNetworkConnected(EReceiptActivity.this)){
            CartOrder();
            try {


            } catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        }else
            generatesecreenmessage("your internet is not connected");


        return false;
    }

    public void loadlistview() {
        carttable =  dbm.getAllrows();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    product_price_arr.clear();
                    productid_arr.clear();
                    productname_arr.clear();
                    product_no_of_unit.clear();
                    for(int i = 0 ; i< carttable.size() ; i++){
                        productid_arr.add(carttable.get(i).getProductId());
                        productname_arr.add(carttable.get(i).getProductaName());
                        product_no_of_unit.add(carttable.get(i).getProductNoofunits());
                        product_price_arr.add(carttable.get(i).getProductprice());

                    Log.e("jnnsn",productname_arr.get(i));
                    }
                    handler.sendEmptyMessage(0);
                } catch (Exception e) {
                }
            }
        }).start();
//        getJsonScriptForFullCart();
  //      dbm.getJsonScriptForFullCart();
    }

    private final Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            if (msg.what == 0) {
                list_of_all_items.setAdapter(new AdapterForEReceiptListActivity(EReceiptActivity.this , productid_arr , productname_arr ,product_no_of_unit , product_price_arr));
                setListViewHeightBasedOnChildren(list_of_all_items);
            } else if(msg.what == 1) {
            }

       }
    };

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

    private void showSingleChoisePayment() {
        String[] array = {"PayPal", "Cash"};


        new MaterialDialog.Builder(this)
                .title("Payment Method")
                .items(array)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        paymenttxt.setText("" + text);
                        if (text.equals("PayPal")) {

                            //Toast.makeText(EReceiptActivity.this, "credit chala", Toast.LENGTH_SHORT).show();
                        } else {
                        }
                    }
                })
                .positiveText(android.R.string.cancel).show();
    }

    public static JSONArray getTotalPrice() {

        JSONArray jsonArray = new JSONArray();

        try {
            for (int i = 0; i < carttable.size(); i++) {
                Log.e("bahdb", String.valueOf(carttable.size()));
                JSONObject jinnerobject = new JSONObject();
//                            Log.e("quantity", productname_arr.get(i));
                jinnerobject.put("product_id", productid_arr.get(i));
                jinnerobject.put("quantity", product_no_of_unit.get(i));
                jinnerobject.put("name",productname_arr.get(i));
                jinnerobject.put("price", product_price_arr.get(i));

                jsonArray.put(jinnerobject);
                Log.e("JSON ", jinnerobject.toString());

            }


            Log.e("JSONARRAY ", jsonArray.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonArray;
    }



    public void CartOrder(){

        Log.e("adjcha",homeaddress);
        homeaddress = homeaddress.replaceAll(",","");


        String cartorderurl= URLS.order_url.concat(userid).concat(URLS.order_url1).concat(Delivery_Type).concat(URLS.order_url2)
                .concat(Detergetnt_Type).concat(URLS.order_url3).concat(homeaddress).concat(URLS.order_url4).concat(collection_date)
                .concat(URLS.order_url5).concat(collection_time).concat(URLS.order_url6).concat(homeaddress).concat(URLS.order_url7)
                .concat(delivery_date).concat(URLS.order_url8).concat(delivery_time).concat(URLS.order_url9)
                .concat(String.valueOf(getTotalPrice())).concat(URLS.order_url10).concat(delivery_note_Receipt).concat(URLS.order_url11)
                .concat(" ").concat(URLS.order_url12).concat(String.valueOf(totalpricewithtip)).concat(URLS.order_url13)
                .concat(String.valueOf(dbm.totalNoofitemsincar())).concat(URLS.order_url14).concat(String.valueOf(dbm.totalNoofitemsincar()))
                .concat(URLS.order_url15).concat("").concat(URLS.order_url16).concat(paymenttxt.getText().toString().trim())
                .concat(URLS.order_url17).concat(String.valueOf(totalpricewithtip)).concat(URLS.order_url18).concat("")
                .concat(URLS.order_url19).concat("").concat(URLS.order_url20).concat("").concat(URLS.order_url21).concat("0").concat(URLS.order_url22).concat(tipPrice)
                .concat(URLS.order_url23).concat(day_type);

               cartorderurl =  cartorderurl.replace(" ","%20");
               cartorderurl = cartorderurl.replaceAll("\n","%20");

        Log.e("carturl",""+ cartorderurl);
         sr = new StringRequest(Request.Method.POST,cartorderurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                pDialog.dismiss();
                Log.e("orderSucess", "" + response);
              //  Toast.makeText(EReceiptActivity.this , "Your order has been placed",Toast.LENGTH_SHORT).show();
                GsonBuilder gsonBuilder = new GsonBuilder();
                final Gson gson = gsonBuilder.create();

                orderPlacedSetterGetter = gson.fromJson(response, OrderPlacedSetterGetter.class);
                if (orderPlacedSetterGetter.result==1){

                    String messsage = orderPlacedSetterGetter.msg;
                    Toast.makeText(EReceiptActivity.this,"Thank you for your order",Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(getApplicationContext(), MainActivityWithicon.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }

                    pDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("ordererror", "" + error);
            pDialog.dismiss();
            }
        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();

                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("Content-Type","application/x-www-form-urlencoded");
                return params;
            }
        };
        sr.setRetryPolicy(new DefaultRetryPolicy(
                50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            pDialog.show();
        queue.add(sr);
    }

    public void Pypalorder(String baseurl1){

//        Log.e("adjcha",homeaddress);
//        homeaddress = homeaddress.replaceAll(",","");
//        String cartorderurl = baseurl+homeaddress+"&pickup_date="+collection_date+"&pickup_time="+collection_time+"&delivery_address="+
//                homeaddress+"&delivery_date="+delivery_date+"&delivery_time="+delivery_time+"&itemdetails="+
//                getJsonScriptForFullCart()+"&delivery_notes="+delivery_note_Receipt+"&special_instructions="+special_instructions+
//                "& total_prize="+dbm.calculationForGrossPrice()+"&user_id="+userid+"&payment_method="+""+"&payment_status="+paymenttxt.getText().toString().trim()+
//                "&payment_amount="+""+"&payment_date_time="+""+"&payment_id="+""+"&payment_platform="+""+"&total_items="+
//                "&total_quantity="+dbm.totalNoofitemsincar()+"&merchant_id=0";

//        cartorderurl =  cartorderurl.replace(" ","%20");
//        cartorderurl = cartorderurl.replaceAll("\n","%20");

//        Log.e("url", cartorderurl);
        sr = new StringRequest(Request.Method.POST,baseurl1, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("orderSucess", "" + response);
                Logger.e("order successs hua "+response);
                //  Toast.makeText(EReceiptActivity.this , "Your order has been placed",Toast.LENGTH_SHORT).show();
                GsonBuilder gsonBuilder = new GsonBuilder();
                final Gson gson = gsonBuilder.create();

                orderPlacedSetterGetter = gson.fromJson(response, OrderPlacedSetterGetter.class);
                if (orderPlacedSetterGetter.result==1){

                    String messsage = orderPlacedSetterGetter.msg;
                    Toast.makeText(EReceiptActivity.this,"Thank you for your order",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), MainActivityWithicon.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }

                pDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("ordererror", "" + error);
                pDialog.dismiss();
            }
        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();

                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("Content-Type","application/x-www-form-urlencoded");
                return params;
            }
        };
        sr.setRetryPolicy(new DefaultRetryPolicy(
                50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        queue.add(sr);
    }

}