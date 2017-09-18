package com.apporio.johnlaundry.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.widget.Toast;

import java.util.HashMap;

public class SessionManager {
    // Shared Preferences
    SharedPreferences pref;

    // Editor for Shared preferences
    SharedPreferences.Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "LoginPrefrences";

    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";

    // Email address (make variable public to access from outside)
    public static final String KEY_UserId= "userid";
    public static final String KEY_DisplayName= "user_displayname";
    public static final String KEY_Nickname= "user_nickname";
    public static final String KEY_FirstName= "first_name";
    public static final String KEY_LastName= "last_name";
    public static final String KEY_UserEmail= "user_email";
    public static final String KEY_Phone = "phone";
    public static final String KEY_Country = "country";
    public static final String KEY_Company_Name= "companyname";
    public static final String KEY_Address = "adress";
    public static final String KEY_Town= "town";
    public static final Bitmap KEY_Userpic = null;
    public static final String KEY_Zip= "zip";
    public static final String KEY_Password= "password";
    public static final String KEY_DeviceID= "deviceid";
    public static final String KEY_CategoryId = "categoryid";
    public static final String KEY_Appartment= "Appartment";
    public static final String KEY_Lat= "lat";
    public static final String KEY_Long = "long";


    // Constructor
    public SessionManager(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }


    /**
     * Create login session
     * */


     public void createLoginSession(String userid ,String firstname,String lastname ,String email,String phone,String homeaddress
             ,String password ){
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);
        // Storing name in pref
         editor.putString(KEY_UserId, userid);
         editor.putString(KEY_FirstName, firstname);
         editor.putString(KEY_LastName, lastname);
         editor.putString(KEY_UserEmail , email);
         editor.putString(KEY_Phone , phone);
         editor.putString(KEY_Address,homeaddress);
         editor.putString(KEY_Password,password);
         editor.commit();
    }

    public void SaveLatlong(String lat , String lng ,String Appartment){

        editor.putString(KEY_Lat,lat);
        editor.putString(KEY_Long , lng);
        editor.putString(KEY_Appartment , Appartment);
        editor.commit();

    }


    public void saveDeviceId(String deviceid){

        editor.putString(KEY_DeviceID,deviceid);
        editor.commit();
    }




    public HashMap<String,String> getDeviceId(){
        HashMap<String,String> deviceid=new HashMap<String,String>();
        deviceid.put(KEY_DeviceID, pref.getString(KEY_DeviceID, ""));
        return  deviceid;
    }

    public void changefirstname(String address ,String phone ){
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(KEY_Address, address);
        editor.putString(KEY_Phone, phone);
        editor.commit();
    }



    public void changebillingdetails( String country , String company , String address ,String town , String state , String zip){
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(KEY_Country, country);
        editor.putString(KEY_Company_Name, company);
        editor.putString(KEY_Address, address);
        editor.putString(KEY_Town, town);
        editor.putString(KEY_Zip, zip);
        editor.commit();
    }


    /**
     * Check login method wil check user login status
     * If false it will redirect user to login page
     * Else won't do anything
     * */
    public boolean checkLogin(){
        // Check login status
        if(!this.isLoggedIn(true)){

        return true;
        }
return false;
    }

      public boolean cheskUseroginStatus(){
             if(!this.isLoggedIn(false)){
                return false;
             }else
                 return true;
      }


    /**
     * Get stored session data
     * */
    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<String, String>();
        // user name
        user.put(KEY_UserId, pref.getString(KEY_UserId, ""));
        user.put(KEY_DisplayName, pref.getString(KEY_DisplayName, ""));
        user.put(KEY_UserEmail, pref.getString(KEY_UserEmail, ""));
        user.put(KEY_FirstName, pref.getString(KEY_FirstName, ""));
        user.put(KEY_LastName, pref.getString(KEY_LastName, ""));
        user.put(KEY_Nickname, pref.getString(KEY_Nickname, ""));
        user.put(KEY_Country, pref.getString(KEY_Country, ""));
        user.put(KEY_Company_Name, pref.getString(KEY_Company_Name, ""));
        user.put(KEY_Address, pref.getString(KEY_Address, ""));
        user.put(KEY_Town, pref.getString(KEY_Town, ""));
        user.put(KEY_Appartment , pref.getString(KEY_Appartment , ""));
        user.put(KEY_Zip, pref.getString(KEY_Zip, ""));
        user.put(KEY_Phone, pref.getString(KEY_Phone, ""));

        return user;
    }

    public HashMap<String , String> getLatLng(){

        HashMap<String, String> latlng = new HashMap<String, String>();
        latlng.put(KEY_Lat , pref.getString(KEY_Lat , ""));
        latlng.put(KEY_Long , pref.getString(KEY_Long , ""));
        latlng.put(KEY_Appartment , pref.getString(KEY_Appartment , ""));
        return latlng;
    }




    /**
     * Clear session details
     * */
    public void logoutUser(){
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();
        Toast.makeText(_context , "You are now Logged Out " , Toast.LENGTH_LONG).show();
//        MainActivity.myaccount.setVisibility(View.VISIBLE);
//        MainActivity.logoutuser.setVisibility(View.GONE);

    }

    /**
     * Quick check for login
     * **/
    // Get Login State
    public boolean isLoggedIn(boolean b){
        return pref.getBoolean(IS_LOGIN, false);
    }




}
