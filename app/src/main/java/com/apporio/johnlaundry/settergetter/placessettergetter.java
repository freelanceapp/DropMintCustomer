package com.apporio.johnlaundry.settergetter;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gaurav on 10/14/2015.
 */
public class placessettergetter {

    @SerializedName("status")
    public String status;

    @SerializedName("predictions")

    public List<Innerplaces> predictionsplace= new ArrayList<Innerplaces>();

}
