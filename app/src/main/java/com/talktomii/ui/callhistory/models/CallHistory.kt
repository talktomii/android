package com.talktomii.ui.callhistory.models

import com.google.gson.annotations.SerializedName


data class CallHistory(

    @SerializedName("isDelete") var isDelete: Boolean? = null,
    @SerializedName("isDeleteFor") var isDeleteFor: ArrayList<String> = arrayListOf(),
    @SerializedName("status") var status: String? = null,
    @SerializedName("_id") var Id: String? = null,
    @SerializedName("uid") var uid: Uid? = Uid(),
    @SerializedName("ifid") var ifid: Ifid? = Ifid(),
    @SerializedName("date") var date: String? = null,
    @SerializedName("startTime") var startTime: String? = null,
    @SerializedName("endTime") var endTime: String? = null,
    @SerializedName("duration") var duration: Int? = null,
    @SerializedName("createdAt") var createdAt: String? = null,
    @SerializedName("updatedAt") var updatedAt: String? = null,
    @SerializedName("__v") var _v: Int? = null,
    @SerializedName("price") var price: Int? = null

)