package com.apporio.johnlaundry.MenuModule;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.apporio.johnlaundry.R;
import com.apporio.johnlaundry.settergetter.PriceListInnerData;
import com.apporio.johnlaundry.settergetter.PriceListSetterGetter;
import com.apporio.johnlaundry.settergetter.Price_Subcat_Innerdata;
import com.apporio.johnlaundry.settergetter.Pricelistresponsesetter_getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.apporio.johnlaundry.adapters.AdapterForPriceList;
import com.apporio.johnlaundry.adapters.Adapter_for_Pricelist_sub_cate;
import com.apporio.johnlaundry.adapters.Dry_list_adapter;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.apporio.johnlaundry.utils.URLS;
import com.apporio.johnlaundry.utils.VolleySingleton;

public class PriceList extends Activity {


    TextView activityname ;
    @Bind(R.id.back_button_on_action_bar)LinearLayout backbtn ;

    AdapterForPriceList adapterForPriceList;

    StringRequest sr;
    RequestQueue queue;

    public static ProgressDialog pDialog;
    int status ;

    ImageView up_circle,down_circle;

    ListView main_categories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_price_list);
        ButterKnife.bind(this);

        queue = VolleySingleton.getInstance(PriceList.this).getRequestQueue();
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);

        Price();
        main_categories = (ListView)findViewById(R.id.main_categories);

        activityname = (TextView) findViewById(R.id.activity_name_on_Action_bar);
        activityname.setText("Price List");


        up_circle = (ImageView)findViewById(R.id.circle_up);
        down_circle= (ImageView)findViewById(R.id.circle_down);

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }
    private void Price() {


        String pricelisturl= URLS.pricelisturl;
        pricelisturl=pricelisturl.replace(" ","%20");

        sr = new StringRequest(Request.Method.GET,pricelisturl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Sucess", "" + response);

                GsonBuilder gsonBuilder = new GsonBuilder();
                final Gson gson = gsonBuilder.create();

                PriceListSetterGetter priceListSetterGetter = new PriceListSetterGetter();
                priceListSetterGetter =gson.fromJson(response, PriceListSetterGetter.class);
                if (priceListSetterGetter.getResult()==1){

                    adapterForPriceList = new AdapterForPriceList(PriceList.this,priceListSetterGetter);
                    main_categories.setAdapter(adapterForPriceList);

                    pDialog.dismiss();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                    pDialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Sucess", "" + error.toString());

                if (error instanceof NetworkError){
                    Toast.makeText(PriceList.this, "No Internet !!", Toast.LENGTH_SHORT).show();
                }else if (error instanceof NoConnectionError){
                    Toast.makeText(PriceList.this, "No Internet", Toast.LENGTH_SHORT).show();
                }else if (error instanceof TimeoutError){
                    Toast.makeText(PriceList.this, "Plz Try Again !!", Toast.LENGTH_SHORT).show();
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
