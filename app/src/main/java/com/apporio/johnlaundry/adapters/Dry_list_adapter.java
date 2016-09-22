package com.apporio.johnlaundry.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.apporio.johnlaundry.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.apporio.johnlaundry.utils.URLS;

/**
 * Created by gaurav on 11/26/2015.
 */
public class Dry_list_adapter extends BaseExpandableListAdapter {

    private Context _context;
    private List<String> _listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<String, List<String>> _listDataChild;

    ArrayList<String> price_list_image_list  = new ArrayList<String>();
    ArrayList<String>   productid_list = new ArrayList<String>();
    ArrayList<String>   specification_list = new ArrayList<String>();
   // ArrayList<String>   base_name_list = new ArrayList<String>();
    ArrayList<String>   image_list = new ArrayList<String>();
    ArrayList<String>   price_list= new ArrayList<String>();
    public ArrayList<String>   product_noofunit_arr= new ArrayList<String>();

    String image[];
//    int[] image;
    public Dry_list_adapter(Context context, List<String> listDataHeader, HashMap<String, List<String>> listChildData, String image[]
    ,ArrayList<String> price_list) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
        this.image = image;
        this.price_list = price_list;
  //      this.image = image;
        this.product_noofunit_arr = product_noofunit_arr;
    }
    static class ViewHolder {
        public ImageView imageview ,plusbtn , minusbtn,bannerimage;
        public TextView  txtListChild, noofunit_product, product_price ;

    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition)
    {
        return childPosition;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = new ViewHolder();

        final String childText = (String) getChild(groupPosition, childPosition);
        final int child = (int) getChildId(groupPosition, childPosition);
//        Toast.makeText(_context,""+child +" "+childText,Toast.LENGTH_LONG).show();

        if (convertView == null) {

            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_item, null);
        }

         viewHolder.txtListChild = (TextView) convertView
                .findViewById(R.id.lblListItem);

        viewHolder.txtListChild.setText(childText);

        viewHolder.product_price = (TextView) convertView.findViewById(R.id.product_price_in_item_for_fragment_list);
        viewHolder.product_price.setText(price_list.get(child));



        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
//        int images = (int) getGroup(groupPosition);
         if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_group, null);
        }

        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.lblListHeaderdry);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);

        ImageView imageView = (ImageView)convertView.findViewById(R.id.banner_in_suitsfragment);
        String images = image[groupPosition];
        images = URLS.Base_Url+ images.replace("//","/");

        Log.e("bahjd", "" + images);
        Picasso.with(_context)
                .load(images)
                .placeholder(R.drawable.name_only)
                .error(R.drawable.name_only)
                .into(imageView);

        return convertView;

    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
