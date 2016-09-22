package com.apporio.johnlaundry.GCMclasses;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.apporio.johnlaundry.R;
import com.apporio.johnlaundry.settergetter.revisedorder.RevisedResponse;
import com.apporio.johnlaundry.startUpScreen.RevisedOrderActivity;
import com.apporio.johnlaundry.utils.URLS;
import com.apporio.johnlaundry.utils.VolleySingleton;
import com.google.android.gms.gcm.GcmListenerService;

import com.apporio.johnlaundry.logger.Logger;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


/**
 * Created by admin on 2/15/2016.
 */
public class GCMNotificationIntentService extends GcmListenerService {

    public static final int notifyID = 9001;
    
    Intent resultIntent;

    @Override
    public void onMessageReceived(String from, Bundle data) {
        Logger.e("notification -- " + data.getString("" + GCMConstants.MSG_KEY) + "   from    " + from, "");


      // ViewRevisedOrder(GCMNotificationIntentService.this,"12");

        sendNotification("" + data.getString("" + GCMConstants.MSG_KEY));
    }

    public void sendNotification(String message) {

      resultIntent = new Intent(this,RevisedOrderActivity.class);

        resultIntent.putExtra("msg", message);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(this, 0,
                resultIntent, PendingIntent.FLAG_ONE_SHOT);
        NotificationCompat.Builder mNotifyBuilder;
        NotificationManager mNotificationManager;

        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        mNotifyBuilder = new NotificationCompat.Builder(this)
                .setContentTitle("Laundry")
                .setContentText("Revised Order" + message)
                .setSmallIcon(R.drawable.app_icon);

        // Set pending intent
        mNotifyBuilder.setContentIntent(resultPendingIntent);

        // Set Vibrate, Sound and Light
        int defaults = 0;
        defaults = defaults | Notification.DEFAULT_LIGHTS;
        defaults = defaults | Notification.DEFAULT_VIBRATE;
        defaults = defaults | Notification.DEFAULT_SOUND;

        mNotifyBuilder.setDefaults(defaults);
        mNotifyBuilder.setContentText(""+message);
        mNotifyBuilder.setAutoCancel(true);
        mNotificationManager.notify(notifyID, mNotifyBuilder.build());

    }


//    public  void ViewRevisedOrder(final Context RidesInfo,String Orderid) {
//        String RideInfoURL = URLS.ViewRevisedOrder.concat(Orderid);
//        RideInfoURL = RideInfoURL.replace(" ", "%20");
//        Log.e("", "" + RideInfoURL);
//
//        RequestQueue queue = VolleySingleton.getInstance(RidesInfo).getRequestQueue();
//
//        StringRequest request = new StringRequest(Request.Method.GET, RideInfoURL, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                Log.e("response", "ride info" + response);
//
//               // sendNotification("Your revised order !!");
//
//                try {
//                    GsonBuilder builder = new GsonBuilder();
//                    Gson gson = builder.create();
//
//                    RevisedResponse Rresponse = new RevisedResponse();
//                    Rresponse = gson.fromJson(response, RevisedResponse.class);
//
//                    if (Rresponse.getResult().toString().equals("1")) {
//
////                        RideID = infoResult.Message.ride_id;
////                        cartype_id = infoResult.Message.car_type_id;
////                        cityid = infoResult.Message.city_id;
////                        driverImage=infoResult.Message.driver_image;
////                        DriverID=infoResult.Message.driver_id;
////                        amount=infoResult.Message.amount;
////                        dist=infoResult.Message.distance;
////                        rtime=infoResult.Message.done_date;
////                        drnmae=infoResult.Message.driver_name;
////                        address1=infoResult.Message.begin_address;
////                        address2=infoResult.Message.end_address;
//
//
//
//                    } else {
//
//                    }
//
//                } catch (Exception e) {
//                    Log.e("exception", "revised order exception" + e);
//                }
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Log.e("error", "revised order error" + error);
//            }
//        });
//        request.setRetryPolicy(new DefaultRetryPolicy(50000,
//                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        queue.add(request);
//
//    }

}
