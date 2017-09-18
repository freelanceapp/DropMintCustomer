package com.apporio.johnlaundry.ordersHistory;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
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
import com.apporio.johnlaundry.settergetter.Order_details_in_order_history;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.apporio.johnlaundry.R;
import com.apporio.johnlaundry.settergetter.NO_Data_Setter;
import com.apporio.johnlaundry.settergetter.VIEW_ORDER_MOST_INNERDATA;
import com.apporio.johnlaundry.settergetter.VIEW_ORDER_SETTER_GETTER;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.apporio.johnlaundry.adapters.Orders_List_Adapter;
import com.apporio.johnlaundry.utils.SessionManager;
import com.apporio.johnlaundry.utils.URLS;
import com.apporio.johnlaundry.utils.VolleySingleton;

public class PreviousOrderActivity extends Activity {

    TextView activityname ;
    String[] Items_Quantity_array ;
    String[] ItemStatus_array ;
    String[] Items_Price_array ;
    String[] Order_Id_array;  //
    String[] Delivery_Date_array;
    String[] Delivery_Time_array;

    Orders_List_Adapter orders_list_adapter;
    ListView orderlist;
    NO_Data_Setter no_data_setter = new NO_Data_Setter();
    public static ImageView collectedimage,notcollectedimage,deliveredimage,notdeliveredimage;
    public static View collectedpink,collectedgray,placedpink,placedgray,deliveredpink,deliveredgray;

    TextView OrderNumberText,DeliveryDateText,TotalQuantityText,TotalPriceText,DeliveryTimeText;
    SessionManager sm;
    StringRequest sr;
    String userid;
    RequestQueue queue;

    VIEW_ORDER_SETTER_GETTER view_order_setter_getter = new VIEW_ORDER_SETTER_GETTER();
    List<VIEW_ORDER_MOST_INNERDATA> vieworder_most_innerdatalist = new ArrayList<>();

    ArrayList<String>   items_quantity_list = new ArrayList<String>();
    ArrayList<String>   deliverydate_list = new ArrayList<String>();
    ArrayList<String>   cost_list = new ArrayList<String>();
    ArrayList<String>   order_no_list = new ArrayList<String>();
    ArrayList<String>  ItemStatus_list =new ArrayList<>();
    ArrayList<String>  delivery_time_list = new ArrayList<>();

    ArrayList<String>  product_id_list = new ArrayList<>();
    ArrayList<String>  product_quantity_list = new ArrayList<>();
    ArrayList<String>  product_name_list = new ArrayList<>();
    ArrayList<String>  product_price_list = new ArrayList<>();
    List<Order_details_in_order_history> product_list = new ArrayList<>();

    String items_quantity,delivery_date,cost,order_no,delivery_time,item_status;

    public static ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_previous_order);

        sm = new SessionManager(PreviousOrderActivity.this);
        queue = VolleySingleton.getInstance(PreviousOrderActivity.this).getRequestQueue();

        OrderNumberText = (TextView)findViewById(R.id.orderNumber);
        DeliveryDateText = (TextView)findViewById(R.id.delivery_date);
        TotalQuantityText= (TextView)findViewById(R.id.total_items);
        TotalPriceText= (TextView)findViewById(R.id.total_price);
        DeliveryTimeText= (TextView)findViewById(R.id.delivery_time);

        pDialog = new ProgressDialog(PreviousOrderActivity.this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);

        placedpink = (View)findViewById(R.id.placedpink);
        placedgray = (View)findViewById(R.id.placedgray);
        collectedimage = (ImageView)findViewById(R.id.collectedimage);
        notcollectedimage = (ImageView)findViewById(R.id.notcollectedimage);
        collectedpink = (View)findViewById(R.id.collectedpink);
        collectedgray= (View)findViewById(R.id.collectedgray);
        deliveredimage = (ImageView)findViewById(R.id.deliveredimage);
        notdeliveredimage = (ImageView)findViewById(R.id.notdeliveredimage);
        deliveredgray= (View)findViewById(R.id.deliveredgray);
        deliveredpink= (View)findViewById(R.id.deliveredpink);

        // get user data from session
     //   Toast.makeText(PreviousOrderActivity.this, "userid" + userid, Toast.LENGTH_SHORT).show();
        activityname = (TextView) findViewById(R.id.activity_name_on_Action_bar);
        activityname.setText("Order History");

        orderlist = (ListView)findViewById(R.id.orderslist);

        //orderlist.
        orderlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
       //         Toast.makeText(PreviousOrderActivity.this,"list clicked chala",Toast.LENGTH_SHORT).show();

                OrderNumberText.setText(Order_Id_array[position]);
                DeliveryDateText.setText(Delivery_Date_array[position]);
                DeliveryTimeText.setText(Delivery_Time_array[position]);
                TotalQuantityText.setText(Items_Quantity_array[position]);
                TotalPriceText.setText(Items_Price_array[position]);
                String status = ItemStatus_array[position];

                if (status.equals("1")){

                    collectedimage.setVisibility(View.VISIBLE);
                    notcollectedimage.setVisibility(View.GONE);
                    placedpink.setVisibility(View.VISIBLE);
                    placedgray.setVisibility(View.GONE);

                    collectedpink.setVisibility(View.GONE);
                    collectedgray.setVisibility(View.VISIBLE);
                    deliveredimage.setVisibility(View.GONE);
                    deliveredpink.setVisibility(View.GONE);
                    deliveredgray.setVisibility(View.VISIBLE);
                    notdeliveredimage.setVisibility(View.VISIBLE);


                }else if (status.equals("2")){

                    collectedimage.setVisibility(View.VISIBLE);
                    notcollectedimage.setVisibility(View.GONE);
                    placedpink.setVisibility(View.VISIBLE);
                    placedgray.setVisibility(View.GONE);

                    collectedpink.setVisibility(View.VISIBLE);
                    collectedgray.setVisibility(View.GONE);
                    deliveredimage.setVisibility(View.VISIBLE);
                    deliveredpink.setVisibility(View.VISIBLE);
                    deliveredgray.setVisibility(View.GONE);
                    notdeliveredimage.setVisibility(View.GONE);


                }else {
                    collectedimage.setVisibility(View.GONE);
                    notcollectedimage.setVisibility(View.VISIBLE);
                    placedpink.setVisibility(View.GONE);
                    placedgray.setVisibility(View.VISIBLE);

                    collectedpink.setVisibility(View.GONE);
                    collectedgray.setVisibility(View.VISIBLE);
                    deliveredimage.setVisibility(View.GONE);
                    deliveredpink.setVisibility(View.GONE);
                    deliveredgray.setVisibility(View.VISIBLE);
                    notdeliveredimage.setVisibility(View.VISIBLE);


                }




            }

        });
        findViewById(R.id.back_button_on_action_bar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if(!sm.checkLogin()){

            HashMap<String, String> user = sm.getUserDetails();

            // get name
            userid = user.get(SessionManager.KEY_UserId);

            view_order(userid);

        }
    }
    public void view_order(String id){

        //String vieworderurl = "http://keshavgoyal.com/laundry_app1/api/view_order.php?user_id="+ id;
        String vieworderurl=  URLS.vieworderurl.concat(id);
        vieworderurl=vieworderurl.replace(" ","%20");
        Log.e("order history url",""+vieworderurl);
        sr = new StringRequest(Request.Method.POST,vieworderurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("vieworder_success", "" + response);
                pDialog.dismiss();
  //              Toast.makeText(PreviousOrderActivity.this, "" + response, Toast.LENGTH_SHORT).show();

                GsonBuilder gsonBuilder = new GsonBuilder();
                final Gson gson = gsonBuilder.create();

                items_quantity_list.clear();
                deliverydate_list.clear();
                cost_list.clear();
                order_no_list.clear();
                delivery_time_list.clear();
                ItemStatus_list.clear();

                no_data_setter = gson.fromJson(response, NO_Data_Setter.class);
                if (no_data_setter.resultofApi.result == 1)
                {

                    view_order_setter_getter = gson.fromJson(response, VIEW_ORDER_SETTER_GETTER.class);

                    vieworder_most_innerdatalist = view_order_setter_getter.view_order_innerdata.view_order_most_innerdatas;

                    Items_Quantity_array = new String[vieworder_most_innerdatalist.size()];
                    Delivery_Date_array = new String[vieworder_most_innerdatalist.size()];
                    Delivery_Time_array = new String[vieworder_most_innerdatalist.size()];
                    Items_Price_array = new String[vieworder_most_innerdatalist.size()];
                    ItemStatus_array =new String[vieworder_most_innerdatalist.size()];
                    Order_Id_array = new String[vieworder_most_innerdatalist.size()];


                    for (int i = 0; i < vieworder_most_innerdatalist.size(); i++) {

                        items_quantity_list.add(vieworder_most_innerdatalist.get(i).total_quantity);
                        deliverydate_list.add(vieworder_most_innerdatalist.get(i).delivery_date);
                        cost_list.add(vieworder_most_innerdatalist.get(i).payment_amount);
                        order_no_list.add(vieworder_most_innerdatalist.get(i).order_id);
                        delivery_time_list.add(vieworder_most_innerdatalist.get(i).delivery_time);
                        ItemStatus_list.add(vieworder_most_innerdatalist.get(i).order_status);

                 //      product_list = vieworder_most_innerdatalist.get(i).itemdetails;



                    }

                    items_quantity = items_quantity_list.get(0);
                    delivery_date = deliverydate_list.get(0);
                    cost = cost_list.get(0);
                    order_no = order_no_list.get(0);
                    delivery_time = delivery_time_list.get(0);
                    item_status = ItemStatus_list.get(0);

                    if (item_status.equals("1")) {

                        collectedimage.setVisibility(View.VISIBLE);
                        notcollectedimage.setVisibility(View.GONE);
                        placedpink.setVisibility(View.VISIBLE);
                        placedgray.setVisibility(View.GONE);

                        collectedpink.setVisibility(View.GONE);
                        collectedgray.setVisibility(View.VISIBLE);
                        deliveredimage.setVisibility(View.GONE);
                        deliveredpink.setVisibility(View.GONE);
                        deliveredgray.setVisibility(View.VISIBLE);
                        notdeliveredimage.setVisibility(View.VISIBLE);



                    }else if (item_status.equals("2")){
                        collectedimage.setVisibility(View.VISIBLE);
                        notcollectedimage.setVisibility(View.GONE);
                        placedpink.setVisibility(View.VISIBLE);
                        placedgray.setVisibility(View.GONE);

                        collectedpink.setVisibility(View.VISIBLE);
                        collectedgray.setVisibility(View.GONE);
                        deliveredimage.setVisibility(View.VISIBLE);
                        deliveredpink.setVisibility(View.VISIBLE);
                        deliveredgray.setVisibility(View.GONE);
                        notdeliveredimage.setVisibility(View.GONE);

                    }else {

                        collectedimage.setVisibility(View.GONE);
                        notcollectedimage.setVisibility(View.VISIBLE);
                        placedpink.setVisibility(View.GONE);
                        placedgray.setVisibility(View.VISIBLE);

                        collectedpink.setVisibility(View.GONE);
                        collectedgray.setVisibility(View.VISIBLE);
                        deliveredimage.setVisibility(View.GONE);
                        deliveredpink.setVisibility(View.GONE);
                        deliveredgray.setVisibility(View.VISIBLE);
                        notdeliveredimage.setVisibility(View.VISIBLE);
                    }


                    Items_Quantity_array = items_quantity_list.toArray(Items_Quantity_array);
                    Delivery_Date_array = deliverydate_list.toArray(Delivery_Date_array);
                    Delivery_Time_array = delivery_time_list.toArray(Delivery_Time_array);
                    Items_Price_array = cost_list.toArray(Items_Price_array);
                    ItemStatus_array = ItemStatus_list.toArray(ItemStatus_array);
                    Order_Id_array = order_no_list.toArray(Order_Id_array);

                    OrderNumberText.setText(order_no);
                    DeliveryDateText.setText(delivery_date);
                    DeliveryTimeText.setText(delivery_time);
                    TotalQuantityText.setText(items_quantity);
                    TotalPriceText.setText(cost);
                    orders_list_adapter = new Orders_List_Adapter(PreviousOrderActivity.this,
                            Items_Quantity_array,Delivery_Date_array,Items_Price_array,Order_Id_array);
                    orderlist.setAdapter(orders_list_adapter);


                }else {
                    pDialog.dismiss();
                    Toast.makeText(PreviousOrderActivity.this,"No Orders",Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pDialog.dismiss();
                Log.e("vieworder_error", "" + error);
                if (error instanceof NetworkError){
                    Toast.makeText(PreviousOrderActivity.this, "No Internet !!", Toast.LENGTH_SHORT).show();
                }else if (error instanceof NoConnectionError){
                    Toast.makeText(PreviousOrderActivity.this, "No Internet", Toast.LENGTH_SHORT).show();
                }else if (error instanceof TimeoutError){
                    Toast.makeText(PreviousOrderActivity.this, "Plz Try Again !!", Toast.LENGTH_SHORT).show();
                }
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
        sr.setRetryPolicy(new DefaultRetryPolicy(50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        pDialog.show();
        queue.add(sr);
    }

}