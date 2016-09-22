
package com.apporio.johnlaundry.settergetter.revisedorder;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Message {

    @SerializedName("pickup_address")
    @Expose
    private String pickupAddress;
    @SerializedName("pickup_date")
    @Expose
    private String pickupDate;
    @SerializedName("pickup_time")
    @Expose
    private String pickupTime;
    @SerializedName("delivery_address")
    @Expose
    private String deliveryAddress;
    @SerializedName("delivery_date")
    @Expose
    private String deliveryDate;
    @SerializedName("delivery_time")
    @Expose
    private String deliveryTime;
    @SerializedName("itemdetails")
    @Expose
    private List<Itemdetail> itemdetails = new ArrayList<Itemdetail>();
    @SerializedName("delivery_notes")
    @Expose
    private String deliveryNotes;
    @SerializedName("special_instructions")
    @Expose
    private String specialInstructions;
    @SerializedName("total_prize")
    @Expose
    private String totalPrize;
    @SerializedName("total_items")
    @Expose
    private String totalItems;
    @SerializedName("total_quantity")
    @Expose
    private String totalQuantity;
    @SerializedName("payment_method")
    @Expose
    private String paymentMethod;
    @SerializedName("payment_status")
    @Expose
    private String paymentStatus;
    @SerializedName("payment_amount")
    @Expose
    private String paymentAmount;
    @SerializedName("payment_date_time")
    @Expose
    private String paymentDateTime;
    @SerializedName("payment_id")
    @Expose
    private String paymentId;
    @SerializedName("payment_platform")
    @Expose
    private String paymentPlatform;
    @SerializedName("acc_status")
    @Expose
    private String accStatus;
    @SerializedName("provider_id")
    @Expose
    private String providerId;
    @SerializedName("order_status")
    @Expose
    private String orderStatus;
    @SerializedName("order_date")
    @Expose
    private String orderDate;

    /**
     * 
     * @return
     *     The pickupAddress
     */
    public String getPickupAddress() {
        return pickupAddress;
    }

    /**
     * 
     * @param pickupAddress
     *     The pickup_address
     */
    public void setPickupAddress(String pickupAddress) {
        this.pickupAddress = pickupAddress;
    }

    /**
     * 
     * @return
     *     The pickupDate
     */
    public String getPickupDate() {
        return pickupDate;
    }

    /**
     * 
     * @param pickupDate
     *     The pickup_date
     */
    public void setPickupDate(String pickupDate) {
        this.pickupDate = pickupDate;
    }

    /**
     * 
     * @return
     *     The pickupTime
     */
    public String getPickupTime() {
        return pickupTime;
    }

    /**
     * 
     * @param pickupTime
     *     The pickup_time
     */
    public void setPickupTime(String pickupTime) {
        this.pickupTime = pickupTime;
    }

    /**
     * 
     * @return
     *     The deliveryAddress
     */
    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    /**
     * 
     * @param deliveryAddress
     *     The delivery_address
     */
    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    /**
     * 
     * @return
     *     The deliveryDate
     */
    public String getDeliveryDate() {
        return deliveryDate;
    }

    /**
     * 
     * @param deliveryDate
     *     The delivery_date
     */
    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    /**
     * 
     * @return
     *     The deliveryTime
     */
    public String getDeliveryTime() {
        return deliveryTime;
    }

    /**
     * 
     * @param deliveryTime
     *     The delivery_time
     */
    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    /**
     * 
     * @return
     *     The itemdetails
     */
    public List<Itemdetail> getItemdetails() {
        return itemdetails;
    }

    /**
     * 
     * @param itemdetails
     *     The itemdetails
     */
    public void setItemdetails(List<Itemdetail> itemdetails) {
        this.itemdetails = itemdetails;
    }

    /**
     * 
     * @return
     *     The deliveryNotes
     */
    public String getDeliveryNotes() {
        return deliveryNotes;
    }

    /**
     * 
     * @param deliveryNotes
     *     The delivery_notes
     */
    public void setDeliveryNotes(String deliveryNotes) {
        this.deliveryNotes = deliveryNotes;
    }

    /**
     * 
     * @return
     *     The specialInstructions
     */
    public String getSpecialInstructions() {
        return specialInstructions;
    }

    /**
     * 
     * @param specialInstructions
     *     The special_instructions
     */
    public void setSpecialInstructions(String specialInstructions) {
        this.specialInstructions = specialInstructions;
    }

    /**
     * 
     * @return
     *     The totalPrize
     */
    public String getTotalPrize() {
        return totalPrize;
    }

    /**
     * 
     * @param totalPrize
     *     The total_prize
     */
    public void setTotalPrize(String totalPrize) {
        this.totalPrize = totalPrize;
    }

    /**
     * 
     * @return
     *     The totalItems
     */
    public String getTotalItems() {
        return totalItems;
    }

    /**
     * 
     * @param totalItems
     *     The total_items
     */
    public void setTotalItems(String totalItems) {
        this.totalItems = totalItems;
    }

    /**
     * 
     * @return
     *     The totalQuantity
     */
    public String getTotalQuantity() {
        return totalQuantity;
    }

    /**
     * 
     * @param totalQuantity
     *     The total_quantity
     */
    public void setTotalQuantity(String totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    /**
     * 
     * @return
     *     The paymentMethod
     */
    public String getPaymentMethod() {
        return paymentMethod;
    }

    /**
     * 
     * @param paymentMethod
     *     The payment_method
     */
    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    /**
     * 
     * @return
     *     The paymentStatus
     */
    public String getPaymentStatus() {
        return paymentStatus;
    }

    /**
     * 
     * @param paymentStatus
     *     The payment_status
     */
    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    /**
     * 
     * @return
     *     The paymentAmount
     */
    public String getPaymentAmount() {
        return paymentAmount;
    }

    /**
     * 
     * @param paymentAmount
     *     The payment_amount
     */
    public void setPaymentAmount(String paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    /**
     * 
     * @return
     *     The paymentDateTime
     */
    public String getPaymentDateTime() {
        return paymentDateTime;
    }

    /**
     * 
     * @param paymentDateTime
     *     The payment_date_time
     */
    public void setPaymentDateTime(String paymentDateTime) {
        this.paymentDateTime = paymentDateTime;
    }

    /**
     * 
     * @return
     *     The paymentId
     */
    public String getPaymentId() {
        return paymentId;
    }

    /**
     * 
     * @param paymentId
     *     The payment_id
     */
    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    /**
     * 
     * @return
     *     The paymentPlatform
     */
    public String getPaymentPlatform() {
        return paymentPlatform;
    }

    /**
     * 
     * @param paymentPlatform
     *     The payment_platform
     */
    public void setPaymentPlatform(String paymentPlatform) {
        this.paymentPlatform = paymentPlatform;
    }

    /**
     * 
     * @return
     *     The accStatus
     */
    public String getAccStatus() {
        return accStatus;
    }

    /**
     * 
     * @param accStatus
     *     The acc_status
     */
    public void setAccStatus(String accStatus) {
        this.accStatus = accStatus;
    }

    /**
     * 
     * @return
     *     The providerId
     */
    public String getProviderId() {
        return providerId;
    }

    /**
     * 
     * @param providerId
     *     The provider_id
     */
    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    /**
     * 
     * @return
     *     The orderStatus
     */
    public String getOrderStatus() {
        return orderStatus;
    }

    /**
     * 
     * @param orderStatus
     *     The order_status
     */
    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    /**
     * 
     * @return
     *     The orderDate
     */
    public String getOrderDate() {
        return orderDate;
    }

    /**
     * 
     * @param orderDate
     *     The order_date
     */
    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

}
