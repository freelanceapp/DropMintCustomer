package com.apporio.johnlaundry.settergetter;

import com.google.gson.annotations.SerializedName;

/**
 * Created by gaurav on 11/15/2015.
 */
public class Privacypolicy {

    @SerializedName("result")
    public int result;

    @SerializedName("Message")
    public Privacy_policy_inner_settergetter privacy_policy_inner_settergetter= new Privacy_policy_inner_settergetter();

}
