package com.apporio.johnlaundry.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by samir on 03/07/15.
 */
public class EmailChecker {


   public  boolean isEmailIsCorrect(String Email) {
        String pttn = "[a-zA-Z0-9._-]+@[a-zA-Z0-9._-]+\\.+[a-zA-Z0-9._-]+";
        Pattern p = Pattern.compile(pttn);
        Matcher m = p.matcher(Email);

        if (m.matches()) {
            return true;
        }
        return false;
    }


}
