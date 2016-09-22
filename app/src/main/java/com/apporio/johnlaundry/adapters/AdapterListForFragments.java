package com.apporio.johnlaundry.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.apporio.johnlaundry.startUpScreen.MainActivityWithicon;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.apporio.johnlaundry.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import com.apporio.johnlaundry.database.DBManager;
import com.apporio.johnlaundry.utils.URLS;

public class AdapterListForFragments extends BaseAdapter {

    Context con ;
    SharedPreferences sharedpreferences;
    ArrayList<String> social_link  = new ArrayList<String>();
    ArrayList<String>   productid_list = new ArrayList<String>();
    ArrayList<String>   specification_list = new ArrayList<String>();
    ArrayList<String>   base_name_list = new ArrayList<String>();
    ArrayList<String>   image_list = new ArrayList<String>();
    ArrayList<String>   price_list= new ArrayList<String>();
    ArrayList<String>   product_noofunit_arr= new ArrayList<String>();
    String category_name_list;
    String categoryid;
    DBManager dbm;
    int value;
    String itemsunits;
    public static final String MyPREFERENCES = "MyPrefs" ;
    public AdapterListForFragments(Context con , ArrayList<String>   productid_list ,ArrayList<String>   specification_list ,
                                   ArrayList<String>  base_name_list  , ArrayList<String> price_list_image_list,
                                   ArrayList<String> image_list, ArrayList<String> price_list, ArrayList<String> product_noofunit_arr,
                                   String category_name_list,String categoryid){

        this.con = con ;
        this.productid_list = productid_list;
        this.specification_list = specification_list ;
        this.base_name_list = base_name_list ;
        this.social_link = price_list_image_list;
        this.image_list= image_list ;
        this.price_list = price_list;
        this.product_noofunit_arr = product_noofunit_arr;
        this.category_name_list  = category_name_list;
        this.categoryid = categoryid ;
            dbm = new DBManager(con);

    }
    @Override
    public int getCount() {
        return productid_list.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        View rowView = view;
        ViewHolder viewHolder = new ViewHolder();
        if(rowView == null){
            LayoutInflater inflater = (LayoutInflater) con.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
            rowView = inflater.inflate(R.layout.item_for_fragment_list, null);
            // configure view holder

                viewHolder.imageview = (ImageView) rowView.findViewById(R.id.banner_in_fragment_list);
                viewHolder.product_name = (TextView) rowView.findViewById(R.id.product_name_in_item_for_fragment_list);
                viewHolder.product_service_name = (TextView) rowView.findViewById(R.id.product_service_name_in_item_for_fragment_list);
                viewHolder.product_price = (TextView) rowView.findViewById(R.id.product_price_in_item_for_fragment_list);
                viewHolder.noofunit_product = (TextView) rowView.findViewById(R.id.no_of_unit_in_item_for_fragment_list);
                viewHolder.bannerimage = (ImageView) rowView.findViewById(R.id.banner_in_fragment_list);

                viewHolder.plusbtn = (ImageView) rowView.findViewById(R.id.plus_button_in_item_for_list_fragment);
                viewHolder.minusbtn = (ImageView) rowView.findViewById(R.id.minus_button_in_item_for_list_fragment);
                viewHolder.sartext = (TextView) rowView.findViewById(R.id.sar);
                viewHolder.PriceFrame = (FrameLayout)rowView.findViewById(R.id.frameforprice);
                    rowView.setTag(viewHolder);
              }else {
            viewHolder = (ViewHolder)rowView.getTag();
        }

//           viewHolder.imageview.setImageResource(Integer.parseInt(productimages_arr.get(i)));
           viewHolder.product_name.setText(base_name_list.get(i));
           viewHolder.product_service_name.setText(specification_list.get(i));
        DecimalFormat  df = new DecimalFormat("#0.00");
        double ddd = Double.parseDouble(price_list.get(i));
           viewHolder.product_price.setText(""+df.format(ddd));
        viewHolder.noofunit_product.setText(dbm.getNoofunitAccordingToProductId(productid_list.get(i)));
        String images = image_list.get(i);
        images = URLS.Base_Url+ images;

        Log.e("bahjd",""+images);
        Picasso.with(con)
                .load(images)
                .error(R.drawable.p_nine_to_five)
                .placeholder(R.drawable.p_nine_to_five)
                .into(viewHolder.imageview);

        // viewHolder.noofunit_product.setText(product_noofunit_arr.get(i));
        

        final ViewHolder finalViewHolder = viewHolder;
        sharedpreferences = con.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
///////////////////////  PLS
        final ViewHolder finalViewHolder3 = viewHolder;
        viewHolder.plusbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                YoYo.with(Techniques.Pulse)
                            .duration(200)
                            .playOn(finalViewHolder3.plusbtn);
                    YoYo.with(Techniques.RubberBand)
                            .duration(300)
                            .playOn(finalViewHolder3.noofunit_product);
                value = Integer.parseInt(finalViewHolder.noofunit_product.getText().toString());
                finalViewHolder.noofunit_product.setText("" + (value + 1));
                product_noofunit_arr.set(i, "" + (value + 1));
                Log.e("category id",""+ categoryid);
                Log.e("id"+productid_list.get(i),"name"+base_name_list.get(i)+"specific"+specification_list.get(i)+"price"+ price_list.get(i)+"no of unit"+product_noofunit_arr.get(i));
                dbm.addtocart(categoryid,productid_list.get(i), base_name_list.get(i), specification_list.get(i), price_list.get(i),
                        product_noofunit_arr.get(i));

            }
        });
//        Toast.makeText(con,sharedpreferences.getString("Itemunits",("" + (value + 1))),Toast.LENGTH_SHORT).show();


/////////////////////  MINS
        viewHolder.minusbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                YoYo.with(Techniques.Pulse)
                        .duration(100)
                        .playOn(finalViewHolder3.minusbtn);
                YoYo.with(Techniques.RubberBand)
                        .duration(300)
                        .playOn(finalViewHolder3.noofunit_product);

                int value = Integer.parseInt(finalViewHolder.noofunit_product.getText().toString());
                if(value>0){
                    if(value == 1){
                        finalViewHolder.noofunit_product.setText(""+(value - 1));
                        product_noofunit_arr.set(i ,""+(value-1));
                        // remove it from DB
                        dbm.removeItemfromDB(productid_list.get(i));

                    }if(value >1){
                        finalViewHolder.noofunit_product.setText("" + (value - 1));
                        product_noofunit_arr.set(i ,""+(value-1));
                        ///edit it into DB
                        Log.e("category id",""+ categoryid);

                        dbm.addtocart(categoryid,productid_list.get(i) , base_name_list.get(i) , specification_list.get(i) ,
                                price_list.get(i) , product_noofunit_arr.get(i));
                    }
                }
                else {
                    //do nothing
                }
            }
        });


        final View finalRowView = rowView;
        final ViewHolder finalViewHolder1 = viewHolder;
        final ViewHolder finalViewHolder2 = viewHolder;

        if (category_name_list.equals("Offers")){
                finalViewHolder1.PriceFrame.setVisibility(View.GONE);
                finalViewHolder1.noofunit_product.setVisibility(View.GONE);
                finalViewHolder1.plusbtn.setVisibility(View.GONE);
                finalViewHolder1.minusbtn.setVisibility(View.GONE);
                finalViewHolder1.product_price.setVisibility(View.GONE);
                finalViewHolder1.sartext.setVisibility(View.GONE);

            rowView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        if (i==0) {
//                            Uri dataUri = Uri.parse("fb://....");
//                            Intent receiverIntent = new Intent(Intent.ACTION_VIEW, dataUri);
//
//                            PackageManager packageManager = con.getPackageManager();
//                            List<ResolveInfo> activities = packageManager.queryIntentActivities(receiverIntent, 0);
//
//                            if (activities.size() > 0) {
//                                final String url = "fb://page/720261808073667";
//                                receiverIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
//                                receiverIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
//                                con.startActivity(receiverIntent);
//                            } else {
                                Uri webpage = Uri.parse(social_link.get(i));
                                Intent webIntent = new Intent(Intent.ACTION_VIEW, webpage);
                                Intent receiverIntent = new Intent(Intent.ACTION_VIEW, webpage);
                                PackageManager packageManager = con.getPackageManager();
                                List<ResolveInfo> activities = packageManager.queryIntentActivities(receiverIntent, 0);
                                packageManager = con.getPackageManager();
                                activities = packageManager.queryIntentActivities(webIntent, 0);

                                if (activities.size() > 0) {
                                    con.startActivity(webIntent);
                                }

                        else{
//                            Toast.makeText(con, "chal gaya", Toast.LENGTH_SHORT).show();
                       }
                    }
                });
        }else {
            finalViewHolder1.PriceFrame.setVisibility(View.VISIBLE);
            finalViewHolder1.noofunit_product.setVisibility(View.VISIBLE);
            finalViewHolder1.plusbtn.setVisibility(View.VISIBLE);
            finalViewHolder1.minusbtn.setVisibility(View.VISIBLE);
            finalViewHolder1.product_price.setVisibility(View.VISIBLE);
            finalViewHolder1.sartext.setVisibility(View.VISIBLE);

//            rowView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Toast.makeText(con, "" + i, Toast.LENGTH_SHORT).show();
//                    YoYo.with(Techniques.Pulse)
//                            .duration(100)
//                            .playOn(finalViewHolder1.imageview);
//                    YoYo.with(Techniques.RubberBand)
//                            .duration(300)
//                            .playOn(finalViewHolder2.noofunit_product);
//                    int value = Integer.parseInt(finalViewHolder.noofunit_product.getText().toString());
//                    finalViewHolder.noofunit_product.setText("" + (value + 1));
//                    product_noofunit_arr.set(i, "" + (value + 1));
//                    dbm.addtocart(productid_list.get(i), base_name_list.get(i), specification_list.get(i), price_list.get(i),
//                            product_noofunit_arr.get(i));
//                    finalViewHolder.noofunit_product.setText(product_noofunit_arr.get(i));
//
//
//                }
//            });
        }
        return rowView;
        }

    static class ViewHolder {
        public ImageView imageview ,plusbtn , minusbtn,bannerimage;
        public TextView  product_name  , product_service_name  , product_price , noofunit_product,sartext;
        public FrameLayout PriceFrame;
    }

}