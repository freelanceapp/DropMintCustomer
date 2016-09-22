package com.apporio.johnlaundry.settergetter;

import com.google.gson.annotations.SerializedName;

/**
 * Created by apporio5 on 23-08-2016.
 */
public class Order_details_in_order_history {

    @SerializedName("product_id")
    public String product_id;

    @SerializedName("quantity")
    public String quantity;

    @SerializedName("name")
    public String name;

    @SerializedName("price")
    public String price;


}
