package com.apporio.johnlaundry.settergetter;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gaurav on 11/2/2015.
 */
public class FAQ_settergetter {

    @SerializedName("result")
    public int result;

    @SerializedName("Message")
    public List<FAQINNERDATA> faqinnerdataList = new ArrayList<>();

}
