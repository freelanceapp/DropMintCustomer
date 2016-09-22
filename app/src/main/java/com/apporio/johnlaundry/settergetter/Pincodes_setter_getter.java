package com.apporio.johnlaundry.settergetter;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by gaurav on 5/2/2016.
 */
public class Pincodes_setter_getter {

    @SerializedName("result")
    public int result;

    @SerializedName("Message")
    public ArrayList<Pincode_inner_settergetter> pincode_inner_settergetters = new ArrayList<>();


}
