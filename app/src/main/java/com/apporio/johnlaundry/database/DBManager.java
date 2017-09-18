package com.apporio.johnlaundry.database;

import android.content.Context;
import android.util.Log;

import com.orm.SugarRecord;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import com.apporio.johnlaundry.events.CartEvent;


/**
 * Created by samir on 10/07/15.
 */
public class DBManager {

    public static CartTable ct ;
    public static Context con ;

    public static EventBus bus = EventBus.getDefault() ;

    public DBManager(Context con ){
        this.con = con ;
    }

    public static  void addtocart(String categoryId , String ProductId, String ProductaName,
                                  String ProductServiresAvailable, String Productprice , String ProductNoofunits){

        if(new CheckCart().idalreadyExsistinCart(ProductId)){
            ////change already saved details in com.apporio.johnlaundry.database
            changeExsistingRowintable(ProductId  , ProductNoofunits);
        }else {
            /////  create new row in com.apporio.johnlaundry.database
            createnreRowintable(categoryId , ProductId,  ProductaName,   ProductServiresAvailable,  Productprice ,  ProductNoofunits);
        }

    }

    private static void createnreRowintable(String categoryId , String ProductId, String ProductaName,  String ProductServiresAvailable,
                                            String Productprice , String ProductNoofunits) {
        // running () create new row in table with valuesd ---------

        com.apporio.johnlaundry.logger.Logger.e("create new row chla"+ProductId+ProductaName+Productprice+ProductNoofunits);

        new CartTable(categoryId,ProductId,
                ProductaName,
                ProductServiresAvailable,
                Productprice,
                ProductNoofunits
         ).save();

        updateTotalOnActionBar();
        showdataoncart();

    }

    private static void updateTotalOnActionBar() {
       /* if(ActivityDetector.openActivity.equals("MainActivity")){
            MainActivity.showgrossOnCart(calculationForGrossPrice());
        }if(ActivityDetector.openActivity.equals("CartActivity")){
            CartActivity.showgrossOnCartCartActivity(calculationForGrossPrice());
        }   */


    }

    public static double calculationForGrossPrice() {

        double dtemp1  , multiplier , gross = 0;
        double temp1;
        List<CartTable> templist = CartTable.listAll(CartTable.class);

        for(int i = 0 ; i<templist.size() ; i++){
            dtemp1 = Double.parseDouble(templist.get(i).getProductprice());
            temp1 =  Double.parseDouble(templist.get(i).getProductNoofunits());
            multiplier = temp1 * dtemp1 ;
            gross = gross + multiplier ;
        }

        com.apporio.johnlaundry.logger.Logger.e("calculation for gross price chla "+gross);

        return gross ;
    }



    public static void changeExsistingRowintable(String reportid , String noOfunitreport) {
        List<CartTable> temp =  CartTable.find(CartTable.class, "Product_Id = ?", reportid);
        long id_of_row_intable = temp.get(0).getId();
        ct = CartTable.findById(CartTable.class, id_of_row_intable);
        ct.ProductNoofunits = noOfunitreport ;
        ct.save();
        updateTotalOnActionBar();
        showdataoncart();

        com.apporio.johnlaundry.logger.Logger.e("changeExsistingRowintable chla" + reportid +  noOfunitreport );

    }


    public static void removeItemfromDB(String reportid){

        List<CartTable>  data_in_list;
        data_in_list = CartTable.find(CartTable.class, "Product_Id = ?", reportid);
        long id_of_row = data_in_list.get(0).getId();
        ct = CartTable.findById(CartTable.class, id_of_row);
        ct.delete();
        updateTotalOnActionBar();
        showdataoncart();

        com.apporio.johnlaundry.logger.Logger.e("removeItemfromDB chla" + reportid);

    }

    public static List<CartTable> getproductsaccordingcategory(String categoryid){

        List<CartTable> productscat;
        productscat =  CartTable.find(CartTable.class, "category_Id = ?",categoryid);

//        List<CartTable> productscat1;
//        productscat1 =  CartTable.find(CartTable.class, "category_Id = ?",id);

//        List<CartTable> productscat2=new ArrayList<>();
//        productscat2.addAll(productscat);
//        productscat2.addAll(productscat1);

        return productscat;

    }



    public static  int countNoofRowsInDatabse(){
        List<CartTable> templist = CartTable.listAll(CartTable.class);
        //Toaster.generatemessage(con , "No Of rows in Database"+templist.size());
        Log.e("No Rows in CART TABLE ", "" + templist.size());
        return templist.size();

    }



    public static  List<CartTable> getAllrows(){
        List<CartTable> templist = CartTable.listAll(CartTable.class);
        //Toaster.generatemessage(con , "No Of rows in Database"+templist.size());
        Log.e("No Rows in CART TABLE ", "" + templist.size());

        com.apporio.johnlaundry.logger.Logger.e("getAllrows chla" + templist);
        return templist;


    }

    public static  int totalNoofitemsincar(){
        List<CartTable> templist = CartTable.listAll(CartTable.class);
        //Toaster.generatemessage(con , "No Of rows in Database"+templist.size());
        Log.e("No Rows in CART TABLE ", "" + templist.size());
        int totalitems = 0;
          for(int i = 0 ; i< templist.size() ; i++){
              totalitems = totalitems + (Integer.parseInt(templist.get(i).getProductNoofunits()));
          }
        com.apporio.johnlaundry.logger.Logger.e("totalNoofitemsincar chla" + totalitems);

        return totalitems;

    }

    public static void clearCartTable(){
        SugarRecord.deleteAll(CartTable.class);
    }

    public static void showdataoncart (){
        CartEvent event = null ;
        event = new CartEvent(""+totalNoofitemsincar() , ""+calculationForGrossPrice());
        bus.post(event);

    }

    public  static String getNoofunitAccordingToProductId(String id ){


        if(new CheckCart().idalreadyExsistinCart(id)){

            List<CartTable>  data_in_list;
            data_in_list = CartTable.find(CartTable.class, "Product_Id = ?", id);
            long id_of_row = data_in_list.get(0).getId();
            ct = CartTable.findById(CartTable.class, id_of_row);
            return  ct.getProductNoofunits();
        }else {
            return "0";
        }

    }


    public static JSONArray getJsonScriptForFullCart() {

        JSONArray jsonArray = new JSONArray();


        List<CartTable> cartdata = getAllrows();

        try {
            for (int i = 0; i < cartdata.size(); i++) {
                JSONObject jinnerobject = new JSONObject();
                com.apporio.johnlaundry.logger.Logger.e("product names"+cartdata.get(i).getProductaName());

                jinnerobject.put("product_id", cartdata.get(i).getProductId());
                jinnerobject.put("quantity", cartdata.get(i).getProductNoofunits());
                jinnerobject.put("name",cartdata.get(i).getProductaName());
                jinnerobject.put("price", cartdata.get(i).getProductprice());

                jsonArray.put(jinnerobject);
            }

            Log.e("JSONARRAY ", jsonArray.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        com.apporio.johnlaundry.logger.Logger.e("getJsonScriptForFullCart chla" + jsonArray);

        return jsonArray;
    }


}
