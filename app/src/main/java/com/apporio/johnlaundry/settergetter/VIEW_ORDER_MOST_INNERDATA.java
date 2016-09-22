package com.apporio.johnlaundry.settergetter;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gaurav on 10/27/2015.
 */
public class VIEW_ORDER_MOST_INNERDATA {

    @SerializedName("order_id")
    public String order_id;

    @SerializedName("pickup_address")
    public String pickup_address;

    @SerializedName("pickup_date")
    public String pickup_date;

    @SerializedName("pickup_time")
    public String pickup_time;

    @SerializedName("delivery_address")
    public String delivery_address;

    @SerializedName("delivery_date")
    public String delivery_date;

    @SerializedName("delivery_time")
    public String delivery_time;

    @SerializedName("delivery_notes")
    public String delivery_notes;

    @SerializedName("special_instructions")
    public String special_instructions;

    @SerializedName("total_prize")
    public String total_prize;

    @SerializedName("total_items")
    public String total_items;

    @SerializedName("total_quantity")
    public String total_quantity;

    @SerializedName("payment_method")
    public String payment_method;

    @SerializedName("payment_status")
    public String payment_status;

    @SerializedName("payment_amount")
    public String payment_amount;

    @SerializedName("payment_date_time")
    public String payment_date_time;

    @SerializedName("payment_id")
    public String payment_id;

    @SerializedName("payment_platform")
    public String payment_platform;

    @SerializedName("order_status")
    public String order_status;

    @SerializedName("itemdetails")
    public List<Order_details_in_order_history> itemdetails= new ArrayList<>();


}