package com.apporio.johnlaundry.database;

import com.orm.SugarRecord;

/**
 * Created by samir on 10/07/15.
 */
public class CategoryTable  extends SugarRecord {

    public String CategoryId ;
    public String CategoryName ;

    public CategoryTable(){

    }


    public CategoryTable(String categoryid , String categoryname){

        this.CategoryId = categoryid;
        this.CategoryName = categoryname;

    }


    public String getCategoryId() {
        return CategoryId;
    }

    public void setCategoryId(String categoryId) {
        CategoryId = categoryId;
    }

    public String getCategoryName() {
        return CategoryName;
    }

    public void setCategoryName(String categoryName) {
        CategoryName = categoryName;
    }



}
