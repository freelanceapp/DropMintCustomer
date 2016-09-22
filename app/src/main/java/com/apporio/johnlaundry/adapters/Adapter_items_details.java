package com.apporio.johnlaundry.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.apporio.johnlaundry.R;

import java.util.ArrayList;

/**
 * Created by gaurav on 2/13/2016.
 */
public class Adapter_items_details extends BaseAdapter {



    Context con  ;
    LayoutInflater inflater;

    ArrayList<String> Pid;
    ArrayList<String> name;
    ArrayList<String> quantity;
    ArrayList<String> price;



    public Adapter_items_details(Context context, ArrayList<String> pr_id, ArrayList<String> pr_name, ArrayList<String> pr_price, ArrayList<String> pr_quantity) {

        this.con = context;
        this.Pid = pr_id;
        this.name = pr_name;
        this.quantity = pr_quantity;
        this.price = pr_price;
        inflater = (LayoutInflater) con.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return name.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public static class Holder{
        TextView item_name_text,item_quantity_text,item_price_text,category_name,Edit;

    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final Holder holder;
        if(convertView== null) {
            convertView = inflater.inflate(R.layout.order_history_products_item,null);
            holder = new Holder();
            convertView.setTag(holder);
        }
        else {
            holder = (Holder)convertView.getTag();
        }
        holder.item_name_text = (TextView)convertView.findViewById(R.id.item_name);
        holder.item_quantity_text = (TextView)convertView.findViewById(R.id.item_quantity);
        holder.item_price_text = (TextView)convertView.findViewById(R.id.item_price);
        holder.category_name= (TextView)convertView.findViewById(R.id.category_name);

        holder.item_name_text.setText(name.get(position));
        holder.item_quantity_text.setText(quantity.get(position));
        holder.item_price_text.setText(price.get(position));
        holder.category_name.setText(Pid.get(position));



        return convertView;


    }
}