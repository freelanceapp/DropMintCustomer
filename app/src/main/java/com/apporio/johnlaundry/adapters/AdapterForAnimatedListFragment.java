package com.apporio.johnlaundry.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.apporio.johnlaundry.R;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import com.apporio.johnlaundry.database.DBManager;

/**
 * Created by samir on 27/07/15.
 */
public class AdapterForAnimatedListFragment extends BaseAdapter {

    Context con ;
    ArrayList<Integer> productimages_arr  = new ArrayList<Integer>();
    ArrayList<String>   productid_arr = new ArrayList<String>();
    ArrayList<String>   productname_arr = new ArrayList<String>();
    ArrayList<String>   productservice_unit_arr = new ArrayList<String>();
    ArrayList<String>   product_price_arr = new ArrayList<String>();
    ArrayList<String>   product_noofunit_arr= new ArrayList<String>();


    DBManager dbm  ;

     Animation rotateclockwise , rorateanticlockwise ;


    public AdapterForAnimatedListFragment(Context con , ArrayList<String>   productid_arr ,ArrayList<String>   productname_arr , ArrayList<String>   productservice_unit_arr , ArrayList<String>   product_price_arr , ArrayList<String>   product_noofunit_arr  , ArrayList<Integer> productimages_arr ){
        this.con = con ;
        this.productid_arr = productid_arr ;
        this.productname_arr = productname_arr ;
        this.productservice_unit_arr = productservice_unit_arr ;
        this.product_price_arr = product_price_arr ;
        this.product_noofunit_arr = product_noofunit_arr ;
        this.productimages_arr = productimages_arr ;

         rotateclockwise = AnimationUtils.loadAnimation(con, R.anim.rotate_clockwise);
         rorateanticlockwise = AnimationUtils.loadAnimation(con, R.anim.rotate_anticlockwise);


        dbm = new DBManager(con);

    }
    @Override
    public int getCount() {
        return productid_arr.size();
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
        final ViewHolder holder;
        if (view != null) {
            holder = (ViewHolder) view.getTag();
        } else {
            LayoutInflater inflater = (LayoutInflater) con.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
            view = inflater.inflate(R.layout.item_for_fragment_list, viewGroup, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }

        holder.imageView.setImageResource(productimages_arr.get(i));


        return view;
    }


    static class ViewHolder {
        @Bind(R.id.banner_in_fragment_list) ImageView imageView;



        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    }

