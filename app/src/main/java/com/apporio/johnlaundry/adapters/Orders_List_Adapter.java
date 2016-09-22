package com.apporio.johnlaundry.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.apporio.johnlaundry.R;
import com.apporio.johnlaundry.settergetter.Order_details_in_order_history;
import com.apporio.johnlaundry.startUpScreen.RevisedOrderActivity;

import java.util.List;

/**
 * Created by gaurav on 10/12/2015.
 */
public class Orders_List_Adapter extends BaseAdapter {

    String [] Items;
    String [] DeliveryDate;
    String [] Cost;
    String [] OrdeNo;

    Context ctc;

    LayoutInflater inflater;
    public Orders_List_Adapter(Context context, String[] Items, String[] DeliveryDate,
                               String[] Cost, String[] OrderNo) {

        this.ctc=context;
        this.Items=Items;
        this.DeliveryDate=DeliveryDate;
        this.Cost=Cost;
        this.OrdeNo=OrderNo;

        inflater = LayoutInflater.from(this.ctc);
    }

    @Override
    public int getCount() {
        return OrdeNo.length;
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

        TextView tvItems,tvDeliveryDate,tvCost,tvOrderNo;
        LinearLayout viewOrder;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        Holder holder;
        if(convertView== null) {
            convertView = inflater.inflate(R.layout.item_for_order_history_new,null);
            holder = new Holder();
            convertView.setTag(holder);
        }
        else {
            holder = (Holder)convertView.getTag();
        }


        holder.tvItems = (TextView)convertView.findViewById(R.id.nmbr_of_items);
        holder.tvItems.setText(Items[position]);

        holder.viewOrder = (LinearLayout)convertView.findViewById(R.id.view_order);

        holder.tvDeliveryDate = (TextView)convertView.findViewById(R.id.delivery_date);
        holder.tvDeliveryDate.setText(DeliveryDate[position]);

        holder.tvCost = (TextView)convertView.findViewById(R.id.cost);
        holder.tvCost.setText(Cost[position]);

        holder.tvOrderNo = (TextView)convertView.findViewById(R.id.order_no);
        holder.tvOrderNo.setText(OrdeNo[position]);

        holder.viewOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ii = new Intent(ctc , RevisedOrderActivity.class);
                ii.putExtra("ordrid",OrdeNo[position]);
                ctc.startActivity(ii);
            }
        });


        return convertView;
    }
}
