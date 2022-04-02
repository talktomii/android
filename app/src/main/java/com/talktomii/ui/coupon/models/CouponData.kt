package com.talktomii.ui.coupon.models

import com.google.gson.annotations.SerializedName

data class CouponData(
    @SerializedName("result") var result: Int? = null,
    @SerializedName("message") var message: String? = null,
//    @SerializedName("payload") var payload: CouponPayload? = CouponPayload()
)