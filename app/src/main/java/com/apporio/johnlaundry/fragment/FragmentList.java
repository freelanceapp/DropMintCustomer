package com.apporio.johnlaundry.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.apporio.johnlaundry.R;
import com.apporio.johnlaundry.settergetter.NO_Data_Setter;
import com.apporio.johnlaundry.settergetter.OFFERS_MOST_INNERDATA;
import com.apporio.johnlaundry.settergetter.OFFERS_SETTERGETTER;
import com.apporio.johnlaundry.settergetter.Product_most_innerdata_washpress;
import com.apporio.johnlaundry.settergetter.Product_setter_getter;
import com.apporio.johnlaundry.startUpScreen.MainActivityWithicon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.apporio.johnlaundry.adapters.AdapterListForFragments;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.apporio.johnlaundry.database.DBManager;
import com.apporio.johnlaundry.utils.URLS;
import com.apporio.johnlaundry.utils.VolleySingleton;

public class FragmentList extends Fragment {

    @Bind(R.id.list_for_fragment)ListView listview;

    Product_setter_getter product_setter_getter = new Product_setter_getter();
    List<Product_most_innerdata_washpress> product_most_innerdatalist = new ArrayList<>();
    OFFERS_SETTERGETTER offers_setter_getter = new OFFERS_SETTERGETTER();
    List<OFFERS_MOST_INNERDATA> offers_most_innerdatalist = new ArrayList<>();

    public static NO_Data_Setter no_data_setter = new NO_Data_Setter();

    ArrayList<String>   productid_list = new ArrayList<String>();
    ArrayList<String>   categoryid_list = new ArrayList<String>();
    ArrayList<String>   categoryname_list = new ArrayList<String>();
    ArrayList<String>   base_name_list = new ArrayList<String>();
    ArrayList<String>   specification_list = new ArrayList<String>();

    ArrayList<String>   social_list= new ArrayList<String>();
    ArrayList<String>   image_list= new ArrayList<String>();
//    ArrayList<String>   price_press_list= new ArrayList<String>();
    ArrayList<String>   price_wash_press_list= new ArrayList<String>();
    ArrayList<String>   product_noof_units= new ArrayList<String>();
    ArrayList<String>   bannerimagelist = new ArrayList<>();

    ArrayList<String>   productid_list_offers = new ArrayList<String>();
    ArrayList<String>   categoryid_list_offers = new ArrayList<String>();
    ArrayList<String>   categoryname_list_offers = new ArrayList<String>();
    ArrayList<String>   base_name_list_offers = new ArrayList<String>();
    ArrayList<String>   specification_list_offers = new ArrayList<String>();
    ArrayList<String>   price_list_name_list_offers = new ArrayList<String>();
    ArrayList<String>   price_list_image_list_offers = new ArrayList<String>();
    ArrayList<String>   image_list_offers = new ArrayList<String>();
    ArrayList<String>   bannerimage_list_offers = new ArrayList<String>();
    ArrayList<String>   price_offers_list= new ArrayList<String>();
    ArrayList<String>   product_noof_units_offers= new ArrayList<String>();

    String categoryid,category_name;
    public static String CategoryId;
    DBManager dbm ;

    MainActivityWithicon mainactivity = new MainActivityWithicon();
    StringRequest sr;
    RequestQueue queue;
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static SharedPreferences sharedpreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_list, container, false);
        ButterKnife.bind(this, v);
        sharedpreferences = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        return v;
        }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
//            Toast.makeText(getActivity(), "gggggggggg ", Toast.LENGTH_SHORT).show();
            try {
                viewproduct();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        dbm = new DBManager(getActivity());
        queue = VolleySingleton.getInstance(getActivity()).getRequestQueue();

        try {
            viewproduct();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static FragmentList newInstance(Context ctc ,  String text) {
        FragmentList f = new FragmentList();
        Bundle b = new Bundle();
        b.putString("msg", text);
        f.setArguments(b);


//        Toast.makeText(ctc, "Fragment "+CategoryId, Toast.LENGTH_SHORT).show();
        return f;
    }
    public void viewproductoffer() {
        CategoryId = getArguments().getString("msg");

       // String viewproducturl  = "http://keshavgoyal.com/laundry_app1/api/view_product.php?merchant_id=0&category_id="+ CategoryId;

        String viewproducturl= URLS.viewproducturl.concat("0").concat(URLS.viewproducturl1).concat(CategoryId);
        viewproducturl=viewproducturl.replace(" ", "%20");

        sr = new StringRequest(Request.Method.POST, viewproducturl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Sucess", "" + response);
                //  Toast.makeText(LoginCleanline.this , ""+response ,Toast.LENGTH_SHORT).show();


                try {

                GsonBuilder gsonBuilder = new GsonBuilder();
                final Gson gson = gsonBuilder.create();

                productid_list_offers.clear();
                product_noof_units_offers.clear();
                categoryid_list_offers.clear();
                categoryname_list_offers.clear();
                specification_list_offers.clear();
                base_name_list_offers.clear();
                price_list_name_list_offers.clear();
                price_list_image_list_offers.clear();
                image_list_offers.clear();
                price_offers_list.clear();

                no_data_setter= gson.fromJson(response, NO_Data_Setter.class);

                if (no_data_setter.resultofApi.result==1) {

                    offers_setter_getter= gson.fromJson(response, OFFERS_SETTERGETTER.class);

                    offers_most_innerdatalist = offers_setter_getter.offers_innerdata.offers_most_innerdatas;

                    for (int i = 0; i < offers_most_innerdatalist.size(); i++) {
                        productid_list_offers.add(offers_most_innerdatalist.get(i).product_id);
                        specification_list_offers.add(offers_most_innerdatalist.get(i).specification);
                        base_name_list_offers.add(offers_most_innerdatalist.get(i).base_name);
                        categoryid_list_offers.add(offers_most_innerdatalist.get(i).category_id);
                        categoryname_list_offers.add(offers_most_innerdatalist.get(i).category_name);
                        price_list_name_list_offers.add(offers_most_innerdatalist.get(i).price_list_name);
                        price_list_image_list_offers.add(offers_most_innerdatalist.get(i).price_list_image);
                        image_list_offers.add(offers_most_innerdatalist.get(i).image);
                        price_offers_list.add(offers_most_innerdatalist.get(i).offer_price);
                        product_noof_units_offers.add(offers_most_innerdatalist.get(i).specification);
//                        //     Toast.makeText(getApplicationContext(), "" + UserId, Toast.LENGTH_SHORT).show();

                       category_name =  categoryname_list_offers.get(i);
                        categoryid = categoryid_list_offers.get(i);
//                        Toast.makeText(getActivity(),"bhdc"+price_offers_list,Toast.LENGTH_SHORT).show();
                    }

                    if (category_name.equals("Offers")) {

//                        listview.setAdapter(new AdapterListForFragments(getActivity(), productid_list_offers,
//                                specification_list_offers, base_name_list_offers, price_list_image_list_offers,
//                                image_list_offers, price_offers_list,product_noof_units_offers,category_name,categoryid));

//                        listview.setAdapter(new AdapterListForFragments(getActivity(), productid_list_offers,
//                                specification_list_offers, price_offers_list,product_noof_units_offers,category_name));
                    }
                    try {
                        //viewproduct();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else {
//                    Toast.makeText(getActivity(), "No Data Found", Toast.LENGTH_SHORT).show();
                    //pDialog.dismiss();
                }
            }catch (Exception e){
                    Log.e("exception",""+e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //pDialog.dismiss();
               Log.e("volley error", "" + error.toString());
   //             Toast.makeText(getActivity(), "" +error, Toast.LENGTH_SHORT).show();

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
        sr.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        queue.add(sr);
    }

    public void viewproduct() {
        CategoryId = getArguments().getString("msg");

        Log.e("zdfd",""+CategoryId);
        //   Toast.makeText(getActivity(),"id"+CategoryId,Toast.LENGTH_SHORT).show();
       // String viewproducturl  = "http://keshavgoyal.com/laundry_app1/api/view_product.php?merchant_id=0&category_id="+ CategoryId;

        String viewproducturl= URLS.viewproducturl.concat("0").concat(URLS.viewproducturl1).concat(CategoryId);
        viewproducturl=viewproducturl.replace(" ", "%20");

        sr = new StringRequest(Request.Method.POST, viewproducturl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Sucess", "" + response);
                //  Toast.makeText(LoginCleanline.this , ""+response ,Toast.LENGTH_SHORT).show();

                try {

                GsonBuilder gsonBuilder = new GsonBuilder();
                final Gson gson = gsonBuilder.create();

                productid_list.clear();
                product_noof_units.clear();
                categoryid_list.clear();
                categoryname_list.clear();
                specification_list.clear();
                base_name_list.clear();

                    social_list.clear();
                image_list.clear();
                price_wash_press_list.clear();
//                price_press_list.clear();

                product_setter_getter = gson.fromJson(response, Product_setter_getter.class);
                if (product_setter_getter.product_innerdata.result==1) {

                    product_most_innerdatalist = product_setter_getter.product_innerdata.product_most_innerdatas;

                    for (int i = 0; i < product_most_innerdatalist.size(); i++)
                    {
                        productid_list.add(product_most_innerdatalist.get(i).product_id);
                        specification_list.add(product_most_innerdatalist.get(i).specification);
                        base_name_list.add(product_most_innerdatalist.get(i).base_name);
                        categoryid_list.add(product_most_innerdatalist.get(i).category_id);
                        categoryname_list.add(product_most_innerdatalist.get(i).category_name);

                        social_list.add(product_most_innerdatalist.get(i).social_link);
                        //image_list.add(product_most_innerdatalist.get(i).image);
                        image_list.add(product_most_innerdatalist.get(i).image);
  //                      price_press_list.add(product_most_innerdatalist.get(i).price_press);
                        price_wash_press_list.add(product_most_innerdatalist.get(i).base_price);
                        product_noof_units.add(product_most_innerdatalist.get(i).specification);


//                        Log.e("press_price_list", "" + price_press_list);
                        Log.e("wash_press_price_list", "" + price_wash_press_list);
                        categoryid = categoryid_list.get(i);
                        category_name = categoryname_list.get(i);
                      //  Toast.makeText(getActivity(),"bhdc"+categoryname_list,Toast.LENGTH_SHORT).show();
                    }

                    listview.setAdapter(new AdapterListForFragments(getActivity(), productid_list, specification_list,
                                base_name_list, social_list, image_list, price_wash_press_list,product_noof_units,
                                category_name,categoryid));


                } else {

  //                  Toast.makeText(getActivity(), "No Data Found", Toast.LENGTH_SHORT).show();
                    //pDialog.dismiss();
                }
            }catch (Exception e){
                    Log.e("exception",""+e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //pDialog.dismiss();
                Log.e("Sucess", "" + error.toString());
//                Toast.makeText(getActivity(), "" +error, Toast.LENGTH_SHORT).show();

            }
        })
        {
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
        sr.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        queue.add(sr);
    }

}