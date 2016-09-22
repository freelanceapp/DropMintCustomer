package com.apporio.johnlaundry.settergetter;

import java.util.List;

/**
 * Created by gaurav on 1/9/2016.
 */
public class Timeslot_SetterGetter {


    /**
     * result : 1
     * Details : {"Pickup":[{"slot_id":"9","pick_up":"08:00","drop_to":"09:00","time_type":"0","day_type":"1","payment":[]},{"slot_id":"15","pick_up":"14:00","drop_to":"15:00","time_type":"0","day_type":"1","payment":["fdgdf","fgfd"]},{"slot_id":"19","pick_up":"18:00","drop_to":"19:00","time_type":"0","day_type":"1","payment":[]}],"Drop":[{"slot_id":"12","pick_up":"11:00","drop_to":"12:00","time_type":"1","day_type":"1","payment":[]},{"slot_id":"18","pick_up":"17:00","drop_to":"18:00","time_type":"1","day_type":"1","payment":[]}]}
     */

    private int result;
    private DetailsBean Details;

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public DetailsBean getDetails() {
        return Details;
    }

    public void setDetails(DetailsBean Details) {
        this.Details = Details;
    }

    public static class DetailsBean {
        /**
         * slot_id : 9
         * pick_up : 08:00
         * drop_to : 09:00
         * time_type : 0
         * day_type : 1
         * payment : []
         */

        private List<PickupBean> Pickup;
        /**
         * slot_id : 12
         * pick_up : 11:00
         * drop_to : 12:00
         * time_type : 1
         * day_type : 1
         * payment : []
         */

        private List<DropBean> Drop;

        public List<PickupBean> getPickup() {
            return Pickup;
        }

        public void setPickup(List<PickupBean> Pickup) {
            this.Pickup = Pickup;
        }

        public List<DropBean> getDrop() {
            return Drop;
        }

        public void setDrop(List<DropBean> Drop) {
            this.Drop = Drop;
        }

        public static class PickupBean {
            private String slot_id;
            private String pick_up;
            private String drop_to;
            private String time_type;
            private String day_type;
            private List<?> payment;

            public String getSlot_id() {
                return slot_id;
            }

            public void setSlot_id(String slot_id) {
                this.slot_id = slot_id;
            }

            public String getPick_up() {
                return pick_up;
            }

            public void setPick_up(String pick_up) {
                this.pick_up = pick_up;
            }

            public String getDrop_to() {
                return drop_to;
            }

            public void setDrop_to(String drop_to) {
                this.drop_to = drop_to;
            }

            public String getTime_type() {
                return time_type;
            }

            public void setTime_type(String time_type) {
                this.time_type = time_type;
            }

            public String getDay_type() {
                return day_type;
            }

            public void setDay_type(String day_type) {
                this.day_type = day_type;
            }

            public List<?> getPayment() {
                return payment;
            }

            public void setPayment(List<?> payment) {
                this.payment = payment;
            }
        }

        public static class DropBean {
            private String slot_id;
            private String pick_up;
            private String drop_to;
            private String time_type;
            private String day_type;
            private List<?> payment;

            public String getSlot_id() {
                return slot_id;
            }

            public void setSlot_id(String slot_id) {
                this.slot_id = slot_id;
            }

            public String getPick_up() {
                return pick_up;
            }

            public void setPick_up(String pick_up) {
                this.pick_up = pick_up;
            }

            public String getDrop_to() {
                return drop_to;
            }

            public void setDrop_to(String drop_to) {
                this.drop_to = drop_to;
            }

            public String getTime_type() {
                return time_type;
            }

            public void setTime_type(String time_type) {
                this.time_type = time_type;
            }

            public String getDay_type() {
                return day_type;
            }

            public void setDay_type(String day_type) {
                this.day_type = day_type;
            }

            public List<?> getPayment() {
                return payment;
            }

            public void setPayment(List<?> payment) {
                this.payment = payment;
            }
        }
    }
}
