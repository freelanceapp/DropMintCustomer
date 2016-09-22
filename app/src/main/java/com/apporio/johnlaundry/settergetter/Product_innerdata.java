package com.apporio.johnlaundry.settergetter;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gaurav on 10/14/2015.
 */
public class Product_innerdata {

    @SerializedName("result")
    public int result;

    @SerializedName("Message")
    public List<Product_most_innerdata_washpress> product_most_innerdatas= new ArrayList<>();

}
