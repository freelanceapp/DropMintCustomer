package com.apporio.johnlaundry.settergetter;

import com.google.gson.annotations.SerializedName;

/**
 * Created by gaurav on 10/14/2015.
 */
public class LOGIN_SETTER_GETTER {

    @SerializedName("result")
    public String result;

    @SerializedName("User_details")
    public LOGININNERDATA logininnerdata = new LOGININNERDATA();

    @SerializedName("msg")
    public String msg ;


}
