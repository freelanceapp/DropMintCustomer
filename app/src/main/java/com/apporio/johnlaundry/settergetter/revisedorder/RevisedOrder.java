
package com.apporio.johnlaundry.settergetter.revisedorder;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class RevisedOrder {

    @SerializedName("revisedResponse")
    @Expose
    private RevisedResponse revisedResponse;

    /**
     * 
     * @return
     *     The revisedResponse
     */
    public RevisedResponse getRevisedResponse() {
        return revisedResponse;
    }

    /**
     * 
     * @param revisedResponse
     *     The revisedResponse
     */
    public void setRevisedResponse(RevisedResponse revisedResponse) {
        this.revisedResponse = revisedResponse;
    }

}
