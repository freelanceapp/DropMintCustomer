package com.apporio.johnlaundry.database;

import com.orm.SugarRecord;

/**
 * Created by samir on 10/07/15.
 */
public class CartTable  extends SugarRecord {

    public String categoryId;
    public String ProductId ;
    public String ProductaName ;
    public String ProductServiresAvailable ;
    public String Productprice ;
    public  String ProductNoofunits ;

    public CartTable(){

    }

    public CartTable(String categoryId , String ProductId, String ProductaName,  String ProductServiresAvailable, String Productprice
            , String ProductNoofunits ) {
        this.categoryId = categoryId;
        this.ProductId = ProductId;
        this.ProductaName = ProductaName;
        this.ProductServiresAvailable = ProductServiresAvailable;
        this.Productprice = Productprice;
        this.ProductNoofunits = ProductNoofunits ;
    }







    public String getProductId() {
        return ProductId;
    }

    public void setProductId(String productId) {
        ProductId = productId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        categoryId = categoryId;
    }


    public String getProductaName() {
        return ProductaName;
    }

    public void setProductaName(String productaName) {
        ProductaName = productaName;
    }

    public String getProductServiresAvailable() {
        return ProductServiresAvailable;
    }

    public void setProductServiresAvailable(String productServiresAvailable) {
        ProductServiresAvailable = productServiresAvailable;
    }

    public String getProductprice() {
        return Productprice;
    }

    public void setProductprice(String productprice) {
        Productprice = productprice;
    }

    public String getProductNoofunits() {
        return ProductNoofunits;
    }

    public void setProductNoofunits(String productNoofunits) {
        ProductNoofunits = productNoofunits;
    }



}
