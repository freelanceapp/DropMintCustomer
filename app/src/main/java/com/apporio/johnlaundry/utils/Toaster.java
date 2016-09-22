package com.apporio.johnlaundry.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by samir on 06/07/15.
 */
public class Toaster {

    public static void generatemessage (Context con , String s){
        Toast.makeText(con , ""+s ,Toast.LENGTH_SHORT).show();

    }
}
