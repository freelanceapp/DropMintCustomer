package com.apporio.johnlaundry.adapters;

import android.content.Context;
import android.util.Log;
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
import com.apporio.johnlaundry.startUpScreen.MainActivityWithicon;

/**
 * Created by samir on 09/07/15.
 */
public class AdapterForListItemForBasket extends BaseAdapter {

    Context con ;
    ArrayList<String> productid_arr = new ArrayList<String>();
    ArrayList<String>   productname_arr = new ArrayList<String>();
    ArrayList<String>   product_no_of_unit = new ArrayList<String>();
    ArrayList<String>   product_price_arr = new ArrayList<String>();
    ArrayList<String>   product_cat_arr = new ArrayList<String>();
    String Specialinstructions;
    String catid;
    DBManager dbm ;

    public AdapterForListItemForBasket(Context con, ArrayList<String> productid_arr , ArrayList<String>   productname_arr ,String Specialinstructions,
                                       ArrayList<String>   no_of_unit ,ArrayList<String> product_price_arr , ArrayList<String> product_category ){
          this.con = con ;
          this.productid_arr = productid_arr ;
          this.productname_arr = productname_arr ;
          this.product_no_of_unit = no_of_unit;
          this.product_price_arr = product_price_arr ;
          this.Specialinstructions=Specialinstructions;
          this.product_cat_arr = product_category ;

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
            viewHolder.minus = (ImageView)rowView.findViewById(R.id.minus);
            viewHolder.plus = (ImageView)rowView.findViewById(R.id.plus);

            rowView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder)rowView.getTag();
        }


        final DecimalFormat  df = new DecimalFormat("#0.00");

            viewHolder.product_name.setText(productname_arr.get(position));
            viewHolder.gross_per_product.setText(df.format(0));
            viewHolder.no_of_unit_product.setText(product_no_of_unit.get(position));
             double dd = Integer.parseInt(product_no_of_unit.get(position))*(Double.parseDouble(product_price_arr.get(position)));

           // viewHolder.gross_per_product.setText(df.format(""+ ((Integer.parseInt(product_no_of_unit.get(position))*(Double.parseDouble(product_price_arr.get(position)))))));
         viewHolder.gross_per_product.setText(df.format(dd));

        final ViewHolder finalViewHolder = viewHolder;
        final ViewHolder finalViewHolder1 = viewHolder;

        viewHolder.minus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                        int value_no_of_unit = Integer.parseInt(finalViewHolder.no_of_unit_product.getText().toString());

                    if (value_no_of_unit > 1) {
                        finalViewHolder.no_of_unit_product.setText("" + (value_no_of_unit - 1));
                        finalViewHolder1.gross_per_product.setText(df.format("" + ((value_no_of_unit - 1) * (Double.parseDouble(product_price_arr.get(position))))));
                      //  dbm.changeExsistingRowintable(productid_arr.get(position), "" + (Integer.parseInt(product_no_of_unit.get(position)) - 1));
                        product_no_of_unit.set(position ,""+(value_no_of_unit-1));
                        Log.e("category id",""+ product_cat_arr.get(position));

                        dbm.addtocart(product_cat_arr.get(position),productid_arr.get(position), productname_arr.get(position), Specialinstructions,
                                product_price_arr.get(position),
                                product_no_of_unit.get(position));
                    }
                    else{
                        dbm.removeItemfromDB(productid_arr.get(position));
                        productid_arr.remove(position);
                        productname_arr.remove(position);
                        product_no_of_unit.remove(position);
                        product_price_arr.remove(position);


                        notifyDataSetChanged();

                    }
                }
            });

        viewHolder.plus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                      int value_no_of_unit = Integer.parseInt(finalViewHolder.no_of_unit_product.getText().toString());
                     finalViewHolder.no_of_unit_product.setText(""+(value_no_of_unit+1));
                     finalViewHolder1.gross_per_product.setText(df.format("" + ((value_no_of_unit + 1) * (Double.parseDouble(product_price_arr.get(position))))));
                    product_no_of_unit.set(position, "" + (value_no_of_unit + 1));
                    Log.e("totalunits", "" + (Integer.parseInt(product_no_of_unit.get(position)) + 1));
                    Log.e("bvsqjghgvqj",""+(value_no_of_unit+1));
                    Log.e("category id",""+ product_cat_arr.get(position));

                    // dbm.changeExsistingRowintable(productid_arr.get(position) , ""+(Integer.parseInt(product_no_of_unit.get(position))+1));
                    dbm.addtocart(product_cat_arr.get(position),productid_arr.get(position), productname_arr.get(position), Specialinstructions,
                            product_price_arr.get(position),
                            product_no_of_unit.get(position));
                }
            });
        return rowView;
    }
    static class ViewHolder {
        TextView product_name  , gross_per_product , no_of_unit_product ;
        ImageView deletetn,minus,plus ;
    }
}
