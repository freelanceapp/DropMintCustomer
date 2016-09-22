package com.apporio.johnlaundry.events;

/**
 * Created by samir on 17/09/15.
 */
public class CartEvent {
    private String datatotalitems ;

    private String datagrosstotal ;

    public CartEvent(String datatotalitem , String datagrosstotal ){
        this.datatotalitems = datatotalitem  ;
        this.datagrosstotal = datagrosstotal  ;
    }




    public String getDatagrosstotal() {
        return datagrosstotal;
    }

    public String getDatatotalitems() {
        return datatotalitems;
    }

}
