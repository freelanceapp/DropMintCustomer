package com.apporio.johnlaundry.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.apporio.johnlaundry.R;

/**
 * Created by gaurav on 3/19/2016.
 */
public class Adapter_for_Pricelist_sub_cate extends BaseAdapter {

    String [] ProductName;
    String [] ProductPrice;

    Context ctc;

    LayoutInflater inflater;
    public Adapter_for_Pricelist_sub_cate(Context context,String[] ProductName,String[] ProductPrice) {

        this.ctc=context;
        this.ProductName=ProductName;
        this.ProductPrice=ProductPrice;
//        this.BasePrice=BasePrice;
        inflater = LayoutInflater.from(this.ctc);
    }

    @Override
    public int getCount() {
        return ProductName.length;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public  class Holder{

        TextView tvproductname,tvproduct_price;

    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Holder holder;
        if(convertView== null) {
            convertView = inflater.inflate(R.layout.sub_cat_price_list,null);
            holder = new Holder();
            convertView.setTag(holder);
        }
        else {
            holder = (Holder)convertView.getTag();
        }
        holder.tvproductname = (TextView)convertView.findViewById(R.id.price_list_product_name);
        holder.tvproductname.setText(ProductName[position]);

        holder.tvproduct_price = (TextView)convertView.findViewById(R.id.price_list_product_price);
        holder.tvproduct_price.setText(ProductPrice[position]);

        return convertView;
    }
}
