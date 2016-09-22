package com.apporio.johnlaundry.settergetter;

import com.google.gson.annotations.SerializedName;

/**
 * Created by gaurav on 11/2/2015.
 */
public class Terms_and_Conditions_Settergetter {

    @SerializedName("result")
    public String result;

    @SerializedName("Message")
    public T_C_innerdata t_c_innerdata= new T_C_innerdata();

}
