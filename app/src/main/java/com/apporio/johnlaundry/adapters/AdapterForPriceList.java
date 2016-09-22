package com.apporio.johnlaundry.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.apporio.johnlaundry.MenuModule.PriceList;
import com.apporio.johnlaundry.R;
import com.apporio.johnlaundry.settergetter.PriceListSetterGetter;

import java.util.List;

/**
 * Created by gaurav on 10/13/2015.
 */
public class AdapterForPriceList extends BaseAdapter{

//    String [] CategoryName;
//    String [] CategoryId;

    Context ctc;
    PriceListSetterGetter data;
    LayoutInflater inflater;
//    public AdapterForPriceList(Context context,String[] Categoryname,String[] CategoryId) {
//
//        this.ctc=context;
//        this.CategoryId=CategoryId;
//        this.CategoryName=Categoryname;
////        this.BasePrice=BasePrice;
//        inflater = LayoutInflater.from(this.ctc);
//    }

    public AdapterForPriceList(Context priceList, PriceListSetterGetter priceListSetterGetter) {
        this.ctc=priceList;
        this.data=priceListSetterGetter;
        inflater = LayoutInflater.from(this.ctc);
    }

    @Override
    public int getCount() {
        return data.getMessage().size();
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

        TextView tvcategoryname;
        LinearLayout addviewLinearLayout;

    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Holder holder;
        if(convertView== null) {
            convertView = inflater.inflate(R.layout.price_list_items,null);
            holder = new Holder();
            convertView.setTag(holder);
        }
        else {
            holder = (Holder)convertView.getTag();
        }



        holder.tvcategoryname = (TextView)convertView.findViewById(R.id.price_list_category_name);
        holder.tvcategoryname.setText(data.getMessage().get(position).getCategory_name());

        holder.addviewLinearLayout=(LinearLayout)convertView.findViewById(R.id.addviewlayout);


        doaddview(holder.addviewLinearLayout,data.getMessage().get(position).getProducts().size(),position,data.getMessage().get(position).getProducts());


        return convertView;
    }




    private void doaddview(LinearLayout addviewLayout, int size, int name, List<PriceListSetterGetter.MessageBean.ProductsBean> price) {
        for (int i=0;i<size;i++) {
            addviewLayout.addView(HorizontalList(R.layout.add_view_item,  price.get(i).getBase_name(),
                    price.get(i).getBase_price(), i));
        }



    }


    public  View HorizontalList(int layout_name, final String prname,String pPrice,final int pos) {

        LayoutInflater layoutInflater =
                (LayoutInflater)ctc.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        final View addView = layoutInflater.inflate(layout_name, null);

        TextView Title=(TextView)addView.findViewById(R.id.product_name);
        TextView price=(TextView)addView.findViewById(R.id.product_price);


        Title.setText(prname);
        price.setText("$ "+pPrice);


        return addView ;
    }



}
