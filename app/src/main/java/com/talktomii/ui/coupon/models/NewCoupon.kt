package com.talktomii.ui.coupon.models

import com.google.gson.annotations.SerializedName

data class NewCoupon(

    @SerializedName("couponIsUsed") var couponIsUsed: Boolean? = null,
    @SerializedName("_id") var Id: String? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("isAll") var isAll: Boolean? = null,
    @SerializedName("isEndDate") var isEndDate: Boolean? = null,
    @SerializedName("endDate") var endDate: String? = null,
    @SerializedName("isActive") var isActive: Boolean? = null,
    @SerializedName("type") var type: String? = null,
    @SerializedName("amount") var amount: Int? = null,
    @SerializedName("createdAt") var createdAt: String? = null,
    @SerializedName("updatedAt") var updatedAt: String? = null,
    @SerializedName("__v") var _v: Int? = null

)