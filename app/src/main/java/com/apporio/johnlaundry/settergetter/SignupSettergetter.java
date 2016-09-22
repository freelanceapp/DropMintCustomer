package com.apporio.johnlaundry.settergetter;

import com.google.gson.annotations.SerializedName;

/**
 * Created by gaurav on 11/16/2015.
 */
public class SignupSettergetter {


    @SerializedName("result")
    public int result;

    @SerializedName("msg")
    public String msg;

    @SerializedName("User_details")
    public LOGININNERDATA logininnerdata = new LOGININNERDATA();

}
