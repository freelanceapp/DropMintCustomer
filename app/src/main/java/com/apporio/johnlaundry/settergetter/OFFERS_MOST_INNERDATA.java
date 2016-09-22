package com.apporio.johnlaundry.settergetter;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by gaurav on 11/5/2015.
 */
public class OFFERS_MOST_INNERDATA implements Serializable {


    @SerializedName("product_id")
    public String product_id;

    @SerializedName("category_id")
    public String category_id;

    @SerializedName("price_id")
    public String price_id;

    @SerializedName("category_name")
    public String category_name;

    @SerializedName("base_name")
    public String base_name;

    @SerializedName("specification")
    public String specification;

    @SerializedName("price_list_name")
    public String price_list_name;

    @SerializedName("price_list_image")
    public String price_list_image;

    @SerializedName("image")
    public String image;

    @SerializedName("offer_price")
    public String offer_price;

}