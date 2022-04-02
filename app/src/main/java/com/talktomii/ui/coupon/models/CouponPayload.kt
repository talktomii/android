package com.talktomii.ui.coupon.models

import com.google.gson.annotations.SerializedName

data class CouponPayload (
    @SerializedName("newCoupon" ) var newCoupon : NewCoupon? = NewCoupon()
)