package com.apporio.johnlaundry.settergetter;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gaurav on 3/19/2016.
 */
public class Price_sub_cat_settergetter {

    @SerializedName("result")
    public int result;

    @SerializedName("Message")
    public List<Price_Subcat_Innerdata> price_subcat_innerdatas = new ArrayList<>();


}
