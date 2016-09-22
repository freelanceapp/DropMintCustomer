package com.apporio.johnlaundry.settergetter;

import java.util.List;

/**
 * Created by apporio5 on 19-08-2016.
 */
public class PriceOfCategory {


    /**
     * result : 1
     * Message : [{"category_id":"37","category_name":"Eco Laundry","wash_price":"+.25c"},{"category_id":"38","category_name":"Prime Laundry","wash_price":"+.25c"},{"category_id":"40","category_name":"Dryclean Laundry","wash_price":"+.50c"}]
     */

    private int result;
    /**
     * category_id : 37
     * category_name : Eco Laundry
     * wash_price : +.25c
     */

    private List<MessageBean> Message;

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public List<MessageBean> getMessage() {
        return Message;
    }

    public void setMessage(List<MessageBean> Message) {
        this.Message = Message;
    }

    public static class MessageBean {
        private String category_id;
        private String category_name;
        private String wash_price;

        public String getCategory_id() {
            return category_id;
        }

        public void setCategory_id(String category_id) {
            this.category_id = category_id;
        }

        public String getCategory_name() {
            return category_name;
        }

        public void setCategory_name(String category_name) {
            this.category_name = category_name;
        }

        public String getWash_price() {
            return wash_price;
        }

        public void setWash_price(String wash_price) {
            this.wash_price = wash_price;
        }
    }
}
