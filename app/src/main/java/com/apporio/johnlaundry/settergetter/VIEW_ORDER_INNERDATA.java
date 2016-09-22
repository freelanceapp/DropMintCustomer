package com.apporio.johnlaundry.settergetter;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gaurav on 10/27/2015.
 */
public class VIEW_ORDER_INNERDATA {

    @SerializedName("result")
    public int result;

    @SerializedName("Message")
    public List<VIEW_ORDER_MOST_INNERDATA> view_order_most_innerdatas= new ArrayList<>();

}