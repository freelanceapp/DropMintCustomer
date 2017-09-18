package com.apporio.johnlaundry.settergetter;

/**
 * Created by gaurav on 5/17/2016.
 */
public class Edit_profile_setter_getter {


    /**
     * result : 1
     * msg : Record Updated!!
     * user_id : 163
     * User_details : {"user_id":"163","fname":"Piyush","lname":"Kumar","email":"piyushtest@gmail.com","phone_number":"9910623373","home_address":"Spaze i-Tech Park, Gurugram, Haryana, India","pincode":"777","latitude":"28.4136003","longitude":"77.04199079999999","appartment":"26","password":"123abc","facebook_id":"","device_id":"APA91bEh5NIi-_aI47vDx6rS0Yse1dm_bXq8llhB_YzNwaGXDrAPMnNkeFQ8BE57BZVij9FNPZzhsDTx7VzYtufnWQVJ9kwQMlTR0Kp7ysqbANa9F8dzZYVHquR5IeTEUqcymVzVSOLV","flag":"2","status":"1"}
     */

    private int result;
    private String msg;
    private String user_id;
    /**
     * user_id : 163
     * fname : Piyush
     * lname : Kumar
     * email : piyushtest@gmail.com
     * phone_number : 9910623373
     * home_address : Spaze i-Tech Park, Gurugram, Haryana, India
     * pincode : 777
     * latitude : 28.4136003
     * longitude : 77.04199079999999
     * appartment : 26
     * password : 123abc
     * facebook_id :
     * device_id : APA91bEh5NIi-_aI47vDx6rS0Yse1dm_bXq8llhB_YzNwaGXDrAPMnNkeFQ8BE57BZVij9FNPZzhsDTx7VzYtufnWQVJ9kwQMlTR0Kp7ysqbANa9F8dzZYVHquR5IeTEUqcymVzVSOLV
     * flag : 2
     * status : 1
     */

    private UserDetailsBean User_details;

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public UserDetailsBean getUser_details() {
        return User_details;
    }

    public void setUser_details(UserDetailsBean User_details) {
        this.User_details = User_details;
    }

    public static class UserDetailsBean {
        private String user_id;
        private String fname;
        private String lname;
        private String email;
        private String phone_number;
        private String home_address;
        private String pincode;
        private String latitude;
        private String longitude;
        private String appartment;
        private String password;
        private String facebook_id;
        private String device_id;
        private String flag;
        private String status;

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getFname() {
            return fname;
        }

        public void setFname(String fname) {
            this.fname = fname;
        }

        public String getLname() {
            return lname;
        }

        public void setLname(String lname) {
            this.lname = lname;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPhone_number() {
            return phone_number;
        }

        public void setPhone_number(String phone_number) {
            this.phone_number = phone_number;
        }

        public String getHome_address() {
            return home_address;
        }

        public void setHome_address(String home_address) {
            this.home_address = home_address;
        }

        public String getPincode() {
            return pincode;
        }

        public void setPincode(String pincode) {
            this.pincode = pincode;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public String getAppartment() {
            return appartment;
        }

        public void setAppartment(String appartment) {
            this.appartment = appartment;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getFacebook_id() {
            return facebook_id;
        }

        public void setFacebook_id(String facebook_id) {
            this.facebook_id = facebook_id;
        }

        public String getDevice_id() {
            return device_id;
        }

        public void setDevice_id(String device_id) {
            this.device_id = device_id;
        }

        public String getFlag() {
            return flag;
        }

        public void setFlag(String flag) {
            this.flag = flag;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
