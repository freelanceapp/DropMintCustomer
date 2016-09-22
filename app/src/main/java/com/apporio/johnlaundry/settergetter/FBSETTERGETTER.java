package com.apporio.johnlaundry.settergetter;

import com.google.gson.annotations.SerializedName;

/**
 * Created by gaurav on 11/5/2015.
 */
public class FBSETTERGETTER {

    @SerializedName("result")
    public int result;

    @SerializedName("msg")
    public String msg;

    @SerializedName("User_details")
    public FBLOGININNERSETTERGETTER fbinnerdata = new FBLOGININNERSETTERGETTER();

}
