package com.apporio.johnlaundry.settergetter;

import com.google.gson.annotations.SerializedName;

/**
 * Created by gaurav on 11/2/2015.
 */
public class KeepInTouchSettergetter {

    @SerializedName("result")
    public String result;

    @SerializedName("Message")
    public KeepInTouchInnerdata keepInTouchInnerdata= new KeepInTouchInnerdata();

}
