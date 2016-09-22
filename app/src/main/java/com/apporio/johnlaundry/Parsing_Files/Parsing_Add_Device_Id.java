package com.apporio.johnlaundry.Parsing_Files;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.apporio.johnlaundry.settergetter.AddDeviceResult;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.apporio.johnlaundry.utils.URLS;
import com.apporio.johnlaundry.utils.VolleySingleton;

/**
 * Created by admin on 6/17/2016.
 */
public class Parsing_Add_Device_Id {


    public static RequestQueue queue;
    public static StringRequest request;

    public static void Add_Device_ID(final Context DeviceIDActivity,String User_id,String flag,String D_id){

        String AddDeviceURL= URLS.Add_Device_Id.concat(User_id).concat(URLS.Add_Device_Id1).concat(flag).concat(URLS.Add_Device_Id2).concat(D_id);
        AddDeviceURL=AddDeviceURL.replace(" ","%20");
        Log.e("add device id url", "" + AddDeviceURL);

        queue = VolleySingleton.getInstance(DeviceIDActivity).getRequestQueue();
        request = new StringRequest(Request.Method.GET, AddDeviceURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("add device", "" + response);
                try {
                    GsonBuilder builder = new GsonBuilder();
                    Gson gson = builder.create();

                    AddDeviceResult deviceResult=new AddDeviceResult();
                    deviceResult=gson.fromJson(response,AddDeviceResult.class);

                    if (deviceResult.result.equals("1")){
                          Toast.makeText(DeviceIDActivity, "" + deviceResult.msg, Toast.LENGTH_LONG).show();

                    }else {
                        Toast.makeText(DeviceIDActivity, "" + deviceResult.msg, Toast.LENGTH_LONG).show();
                    }


                    //      Log.e(" add emergency", "" + rstatusResultCore.msg);

                } catch (Exception e) {

                    Log.e("Exception", "add device Exception" + e);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.e("error add device", "" + error);

            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(request);

    }



}
