package com.apporio.johnlaundry.MenuModule;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
import com.apporio.johnlaundry.settergetter.FAQINNERDATA;
import com.apporio.johnlaundry.settergetter.FAQ_settergetter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.apporio.johnlaundry.adapters.AdapterForFAQ;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.apporio.johnlaundry.utils.NetworkChecker;
import com.apporio.johnlaundry.utils.URLS;
import com.apporio.johnlaundry.utils.VolleySingleton;

public class FAQ extends Activity {

    ListView faqlistview;
    AdapterForFAQ adapterForFAQ ;

    TextView activityname ;
    @Bind(R.id.back_button_on_action_bar)LinearLayout backbtn ;

    StringRequest sr;
    RequestQueue queue;
    private ProgressDialog pDialog;
    FAQ_settergetter faq_settergetter = new FAQ_settergetter();
    List<FAQINNERDATA> faqinnerdatas = new ArrayList<>();

    String[] faqQuestionArray;
    String[] faqAnswerArray;
    String[] faqIdArray;

    ArrayList<String> faqidlist = new ArrayList<>();
    ArrayList<String> faqquestionlist = new ArrayList<>();
    ArrayList<String> faqanswerlist = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);
        ButterKnife.bind(this);

        activityname = (TextView) findViewById(R.id.activity_name_on_Action_bar);
        activityname.setText("FAQ");

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        faqlistview = (ListView)findViewById(R.id.faqlist);
        queue = VolleySingleton.getInstance(FAQ.this).getRequestQueue();
        checkInternetConnectivity();

    }
    public boolean checkInternetConnectivity() {

        if (new NetworkChecker().isNetworkConnected(FAQ.this)) {
            try {
                pDialog.show();
                faq();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        } else
            return false;
    }
    private void faq() {

     //   String faqurl = "http://keshavgoyal.com/laundry_app1/api/faq.php?merchant_id=0";

        String faqurl= URLS.faqurl;
        faqurl=faqurl.replace(" ","%20");

        sr = new StringRequest(Request.Method.POST,faqurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Sucess", "" + response);

                pDialog.dismiss();
                GsonBuilder gsonBuilder = new GsonBuilder();
                final Gson gson = gsonBuilder.create();

                faqidlist.clear();
                faqquestionlist.clear();
                faqanswerlist.clear();

                faq_settergetter = gson.fromJson(response, FAQ_settergetter.class);
                if (faq_settergetter.result==1){

                    faqinnerdatas = faq_settergetter.faqinnerdataList;

                    for (int i = 0; i < faqinnerdatas.size(); i++) {

                        faqidlist.add(faqinnerdatas.get(i).faq_id);
                        faqquestionlist.add(faqinnerdatas.get(i).faq_qu);
                        faqanswerlist.add(faqinnerdatas.get(i).faq_ans);
                    }

                    faqIdArray = new String[faqinnerdatas.size()];
                    faqQuestionArray = new String[faqinnerdatas.size()];
                    faqAnswerArray = new String[faqinnerdatas.size()];

                    faqIdArray = faqidlist.toArray(faqIdArray);
                    faqQuestionArray = faqquestionlist.toArray(faqQuestionArray);
                    faqAnswerArray = faqanswerlist.toArray(faqAnswerArray);

                    //         Toast.makeText(PriceList.this, "nhn"+CategoryName[1], Toast.LENGTH_SHORT).show();

                    adapterForFAQ = new AdapterForFAQ(FAQ.this,faqQuestionArray,faqAnswerArray);
                    faqlistview.setAdapter(adapterForFAQ);

                    //pDialog.dismiss();

                    pDialog.dismiss();
                }
                else {
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
        sr.setRetryPolicy(new DefaultRetryPolicy(30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


        queue.add(sr);
    }

}