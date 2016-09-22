package com.apporio.johnlaundry.database;

import android.content.Context;
import android.util.Log;

import java.util.List;

/**
 * Created by samir on 04/07/15.
 */
public class CheckCart {

    Context con ;
    boolean checkid = false ;

    public boolean idalreadyExsistinCart(String id){

        try {
            List<CartTable> templist = CartTable.find(CartTable.class, " Product_Id = ?", id);
            Log.d("Size of templist ", "" + templist.size());
            if (templist.size() == 1) {
                checkid = true;
            } else {
                checkid = false;
            }

        } catch (Exception e) {
            //Log.d("Product Id is not saved in Databse ", "Id is not saved in Databse ");
        }

        return checkid;

    }

}
