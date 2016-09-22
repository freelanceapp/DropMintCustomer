
package com.apporio.johnlaundry.settergetter.revisedorder;


import java.util.List;

public class RevisedResponse {


    /**
     * result : 1
     * Message : {"pickup_address":"hno 722 sec 31 gurgaon","pickup_date":"Aug 2 2016","pickup_time":"08:00 to 09:00","delivery_address":"hno 722 sec 31 gurgaon","delivery_date":"Aug 5 2016","delivery_time":"08:00 to 09:00","itemdetails":[{"ProductId":"156","quantity":"8","name":"Small Bag","price":"100"}],"delivery_notes":"Aug 5 2016","special_instructions":"","total_prize":"800","total_items":"8","total_quantity":"8","payment_method":"","payment_status":"CASH ON DELIVERY","payment_amount":"800","payment_date_time":"","payment_id":"","payment_platform":"","acc_status":"0","provider_id":"0","order_status":"1","order_date":"2016-08-01 18:27:54"}
     */

    private ResponseBean response;

    public ResponseBean getResponse() {
        return response;
    }

    public void setResponse(ResponseBean response) {
        this.response = response;
    }

    public static class ResponseBean {
        private int result;
        /**
         * pickup_address : hno 722 sec 31 gurgaon
         * pickup_date : Aug 2 2016
         * pickup_time : 08:00 to 09:00
         * delivery_address : hno 722 sec 31 gurgaon
         * delivery_date : Aug 5 2016
         * delivery_time : 08:00 to 09:00
         * itemdetails : [{"ProductId":"156","quantity":"8","name":"Small Bag","price":"100"}]
         * delivery_notes : Aug 5 2016
         * special_instructions :
         * total_prize : 800
         * total_items : 8
         * total_quantity : 8
         * payment_method :
         * payment_status : CASH ON DELIVERY
         * payment_amount : 800
         * payment_date_time :
         * payment_id :
         * payment_platform :
         * acc_status : 0
         * provider_id : 0
         * order_status : 1
         * order_date : 2016-08-01 18:27:54
         */

        private MessageBean Message;

        public int getResult() {
            return result;
        }

        public void setResult(int result) {
            this.result = result;
        }

        public MessageBean getMessage() {
            return Message;
        }

        public void setMessage(MessageBean Message) {
            this.Message = Message;
        }

        public static class MessageBean {
            private String pickup_address;
            private String pickup_date;
            private String pickup_time;
            private String delivery_address;
            private String delivery_date;
            private String delivery_time;
            private String delivery_notes;
            private String special_instructions;
            private String total_prize;
            private String total_items;
            private String total_quantity;
            private String payment_method;
            private String payment_status;
            private String payment_amount;
            private String payment_date_time;
            private String payment_id;
            private String payment_platform;
            private String acc_status;
            private String provider_id;
            private String order_status;
            private String order_date;
            /**
             * ProductId : 156
             * quantity : 8
             * name : Small Bag
             * price : 100
             */

            private List<ItemdetailsBean> itemdetails;

            public String getPickup_address() {
                return pickup_address;
            }

            public void setPickup_address(String pickup_address) {
                this.pickup_address = pickup_address;
            }

            public String getPickup_date() {
                return pickup_date;
            }

            public void setPickup_date(String pickup_date) {
                this.pickup_date = pickup_date;
            }

            public String getPickup_time() {
                return pickup_time;
            }

            public void setPickup_time(String pickup_time) {
                this.pickup_time = pickup_time;
            }

            public String getDelivery_address() {
                return delivery_address;
            }

            public void setDelivery_address(String delivery_address) {
                this.delivery_address = delivery_address;
            }

            public String getDelivery_date() {
                return delivery_date;
            }

            public void setDelivery_date(String delivery_date) {
                this.delivery_date = delivery_date;
            }

            public String getDelivery_time() {
                return delivery_time;
            }

            public void setDelivery_time(String delivery_time) {
                this.delivery_time = delivery_time;
            }

            public String getDelivery_notes() {
                return delivery_notes;
            }

            public void setDelivery_notes(String delivery_notes) {
                this.delivery_notes = delivery_notes;
            }

            public String getSpecial_instructions() {
                return special_instructions;
            }

            public void setSpecial_instructions(String special_instructions) {
                this.special_instructions = special_instructions;
            }

            public String getTotal_prize() {
                return total_prize;
            }

            public void setTotal_prize(String total_prize) {
                this.total_prize = total_prize;
            }

            public String getTotal_items() {
                return total_items;
            }

            public void setTotal_items(String total_items) {
                this.total_items = total_items;
            }

            public String getTotal_quantity() {
                return total_quantity;
            }

            public void setTotal_quantity(String total_quantity) {
                this.total_quantity = total_quantity;
            }

            public String getPayment_method() {
                return payment_method;
            }

            public void setPayment_method(String payment_method) {
                this.payment_method = payment_method;
            }

            public String getPayment_status() {
                return payment_status;
            }

            public void setPayment_status(String payment_status) {
                this.payment_status = payment_status;
            }

            public String getPayment_amount() {
                return payment_amount;
            }

            public void setPayment_amount(String payment_amount) {
                this.payment_amount = payment_amount;
            }

            public String getPayment_date_time() {
                return payment_date_time;
            }

            public void setPayment_date_time(String payment_date_time) {
                this.payment_date_time = payment_date_time;
            }

            public String getPayment_id() {
                return payment_id;
            }

            public void setPayment_id(String payment_id) {
                this.payment_id = payment_id;
            }

            public String getPayment_platform() {
                return payment_platform;
            }

            public void setPayment_platform(String payment_platform) {
                this.payment_platform = payment_platform;
            }

            public String getAcc_status() {
                return acc_status;
            }

            public void setAcc_status(String acc_status) {
                this.acc_status = acc_status;
            }

            public String getProvider_id() {
                return provider_id;
            }

            public void setProvider_id(String provider_id) {
                this.provider_id = provider_id;
            }

            public String getOrder_status() {
                return order_status;
            }

            public void setOrder_status(String order_status) {
                this.order_status = order_status;
            }

            public String getOrder_date() {
                return order_date;
            }

            public void setOrder_date(String order_date) {
                this.order_date = order_date;
            }

            public List<ItemdetailsBean> getItemdetails() {
                return itemdetails;
            }

            public void setItemdetails(List<ItemdetailsBean> itemdetails) {
                this.itemdetails = itemdetails;
            }

            public static class ItemdetailsBean {
                private String ProductId;
                private String quantity;
                private String name;
                private String price;

                public String getProductId() {
                    return ProductId;
                }

                public void setProductId(String ProductId) {
                    this.ProductId = ProductId;
                }

                public String getQuantity() {
                    return quantity;
                }

                public void setQuantity(String quantity) {
                    this.quantity = quantity;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getPrice() {
                    return price;
                }

                public void setPrice(String price) {
                    this.price = price;
                }
            }
        }
    }
}
