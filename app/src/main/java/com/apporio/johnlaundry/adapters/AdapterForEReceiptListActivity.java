package com.apporio.johnlaundry.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.apporio.johnlaundry.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

import com.apporio.johnlaundry.database.DBManager;

/**
 * Created by samir on 16/07/15.
 */
public class AdapterForEReceiptListActivity extends BaseAdapter {

    Context con ;
    ArrayList<String> productid_arr = new ArrayList<String>();
    ArrayList<String>   productname_arr = new ArrayList<String>();
    ArrayList<String>   product_no_of_unit = new ArrayList<String>();
    ArrayList<String>   product_price_arr = new ArrayList<String>();
    DecimalFormat df;
    DBManager dbm ;

    public AdapterForEReceiptListActivity(Context con , ArrayList<String> productid_arr , ArrayList<String>   productname_arr ,ArrayList<String>   no_of_unit ,ArrayList<String>   product_price_arr ){
        this.con = con ;
        this.productid_arr = productid_arr ;
        this.productname_arr = productname_arr ;
        this.product_no_of_unit = no_of_unit ;
        this.product_price_arr = product_price_arr ;
        dbm = new DBManager(con);
    }

    @Override
    public int getCount() {
        return productid_arr.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View rowView = convertView;
        ViewHolder viewHolder = new ViewHolder();
        if(rowView == null){
            LayoutInflater inflater = (LayoutInflater) con.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
            rowView = inflater.inflate(R.layout.item_for_list_in_basket, null);

            viewHolder.product_name = (TextView) rowView.findViewById(R.id.product_name_item_inlist_of_basket_activity);
            viewHolder.gross_per_product = (TextView) rowView.findViewById(R.id.gross_total_per_unit_in_item_inlist_of_basket_activity);
            viewHolder.no_of_unit_product = (TextView) rowView.findViewById(R.id.no_of_unit_item_inlist_of_basket_activity);
            viewHolder.minus = (ImageView) rowView.findViewById(R.id.minus);
            viewHolder.plus = (ImageView) rowView.findViewById(R.id.plus);
            rowView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder)rowView.getTag();
        }

        viewHolder.product_name.setText(productname_arr.get(position));
        viewHolder.gross_per_product.setText("0");
        df = new DecimalFormat("#0.00");
        viewHolder.no_of_unit_product.setText(product_no_of_unit.get(position));
        double dd = ((Integer.parseInt(product_no_of_unit.get(position))*(Double.parseDouble(product_price_arr.get(position)))));
        viewHolder.gross_per_product.setText(""+ df.format(dd));
        viewHolder.minus.setVisibility(View.GONE);
        viewHolder.plus.setVisibility(View.GONE);
        return rowView;
    }


    static class ViewHolder {
        TextView product_name  , gross_per_product , no_of_unit_product ;
        ImageView minus,plus ;
    }
}
