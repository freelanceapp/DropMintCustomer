package com.apporio.johnlaundry.settergetter;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gaurav on 10/14/2015.
 */
public class CATEGORY_SETTER_GETTER {

    @SerializedName("result")
    public String result;

    @SerializedName("Message")
    public List<Category_InnerData> category_innerDatas = new ArrayList<>();

}
