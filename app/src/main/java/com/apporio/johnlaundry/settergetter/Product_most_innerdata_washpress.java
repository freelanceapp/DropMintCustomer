package com.apporio.johnlaundry.settergetter;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by gaurav on 10/14/2015.
 */
public class Product_most_innerdata_washpress implements Serializable{


    @SerializedName("product_id")
    public String product_id;

    @SerializedName("category_id")
    public String category_id;

    @SerializedName("category_name")
    public String category_name;

    @SerializedName("base_name")
    public String base_name;

    @SerializedName("base_price")
    public String base_price;

    @SerializedName("specification")
    public String specification;

    @SerializedName("image")
    public String image;

    @SerializedName("social_link")
    public String social_link;

}
