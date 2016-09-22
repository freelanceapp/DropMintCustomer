package com.apporio.johnlaundry.startUpScreen;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.apporio.johnlaundry.R;
import com.apporio.johnlaundry.adapters.AdapterForEReceiptListActivity;
import com.apporio.johnlaundry.adapters.Adapter_items_details;
import com.apporio.johnlaundry.settergetter.revisedorder.RevisedResponse;
import com.apporio.johnlaundry.utils.URLS;
import com.apporio.johnlaundry.utils.VolleySingleton;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

public class RevisedOrderActivity extends Activity {

    TextView ActivityTextView,ok_order;
    LinearLayout backLayout;

    TextView pickupaddress,pickupdate,pickuptime,deliveryaddress,deliverytime,deliverydate,orderdate,deliverynote,totalprice,
    totalquantity;
    List<RevisedResponse.ResponseBean.MessageBean.ItemdetailsBean> products = new ArrayList<>();
    ListView itemdetails_List;

    ArrayList<String> pr_id = new ArrayList<>();
    ArrayList<String> pr_quantity = new ArrayList<>();
    ArrayList<String> pr_name = new ArrayList<>();
    ArrayList<String> pr_price = new ArrayList<>();

    public static ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_revised_order);

        backLayout=(LinearLayout)findViewById(R.id.back_button_on_action_bar);
        ActivityTextView=(TextView)findViewById(R.id.activity_name_on_Action_bar);
        backLayout.setVisibility(View.VISIBLE);
        ActivityTextView.setText("Revised Order");
        ok_order=(TextView)findViewById(R.id.ok_order);
        pDialog = new ProgressDialog(RevisedOrderActivity.this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);


        pickupaddress=(TextView)findViewById(R.id.pickup_address);
        pickupdate=(TextView)findViewById(R.id.pickup_date);
        pickuptime=(TextView)findViewById(R.id.pickup_time);
        deliveryaddress=(TextView)findViewById(R.id.delivery_address);
        deliverytime=(TextView)findViewById(R.id.delivery_time);
        deliverydate=(TextView)findViewById(R.id.delivery_date);
        orderdate=(TextView)findViewById(R.id.order_date);
        deliverynote=(TextView)findViewById(R.id.delivery_note);
        totalprice=(TextView)findViewById(R.id.total_price);
        totalquantity=(TextView)findViewById(R.id.total_quantity);
        itemdetails_List=(ListView)findViewById(R.id.item_details_list);

        backLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ok_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        Intent i=getIntent();


        ViewRevisedOrder(RevisedOrderActivity.this,i.getStringExtra("ordrid"));

    }



    public  void ViewRevisedOrder(final Context RidesInfo, String Orderid) {
        String RideInfoURL = URLS.ViewRevisedOrder.concat(Orderid);
        RideInfoURL = RideInfoURL.replace(" ", "%20");
        Log.e("", "" + RideInfoURL);

        RequestQueue queue = VolleySingleton.getInstance(RidesInfo).getRequestQueue();

        StringRequest request = new StringRequest(Request.Method.GET, RideInfoURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("response", "ride info" + response);
                 pDialog.dismiss();

                try {
                    GsonBuilder builder = new GsonBuilder();
                    Gson gson = builder.create();

                    RevisedResponse Rresponse = new RevisedResponse();
                    Rresponse = gson.fromJson(response, RevisedResponse.class);

                    if (Rresponse.getResponse().getResult()==1) {

                        pickupaddress.setText(Rresponse.getResponse().getMessage().getPickup_address());
                        pickupdate.setText(Rresponse.getResponse().getMessage().getPickup_date());
                        pickuptime.setText(Rresponse.getResponse().getMessage().getPickup_time());
                        deliveryaddress.setText(Rresponse.getResponse().getMessage().getDelivery_address());
                        deliverytime.setText(Rresponse.getResponse().getMessage().getDelivery_time());
                        deliverydate.setText(Rresponse.getResponse().getMessage().getDelivery_date());
                        orderdate.setText(Rresponse.getResponse().getMessage().getOrder_date());
                        deliverynote.setText(Rresponse.getResponse().getMessage().getDelivery_notes());
                        totalprice.setText(Rresponse.getResponse().getMessage().getTotal_prize());
                        totalquantity.setText(Rresponse.getResponse().getMessage().getTotal_quantity());

                       products = Rresponse.getResponse().getMessage().getItemdetails();

                        for (int i = 0 ; i < products.size() ; i++ ){
                       pr_id.add(products.get(i).getProductId());
                       pr_name.add(products.get(i).getName());
                       pr_price.add(products.get(i).getPrice());
                       pr_quantity.add(products.get(i).getQuantity());

                        }

                     itemdetails_List.setAdapter(new Adapter_items_details(RevisedOrderActivity.this,pr_id,pr_name,pr_price,pr_quantity));
                        setListViewHeightBasedOnChildren(itemdetails_List);

                    } else {

                    }

                } catch (Exception e) {
                    Log.e("exception", "revised order exception" + e);
                    pDialog.dismiss();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error", "revised order error" + error);
                pDialog.dismiss();
            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        pDialog.show();
        queue.add(request);

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

}
