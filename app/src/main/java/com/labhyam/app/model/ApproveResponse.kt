package com.labhyam.app.model;
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable


class ApproveResponse : Serializable {
    @SerializedName("isvalid")
    @Expose
    var isvalid: Boolean? = null

    @SerializedName("message")
    @Expose
    var message: String? = null

    @SerializedName("negativeValues")
    @Expose
    var negativeValues: kotlin.collections.List<NegativeValue>? = null


    companion object {
        private const val serialVersionUID = 817841448160049490L
    }
}
