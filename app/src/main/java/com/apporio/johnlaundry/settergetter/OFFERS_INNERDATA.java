package com.apporio.johnlaundry.settergetter;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gaurav on 11/5/2015.
 */
public class OFFERS_INNERDATA {

    @SerializedName("result")
    public int result;

    @SerializedName("Message")
    public List<OFFERS_MOST_INNERDATA> offers_most_innerdatas= new ArrayList<>();

}
