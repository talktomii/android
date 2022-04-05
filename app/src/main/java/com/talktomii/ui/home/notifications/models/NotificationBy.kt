package com.talktomii.ui.home.notifications.models

import com.google.gson.annotations.SerializedName


data class NotificationBy(

    @SerializedName("status") var status: NotificationStatus? = NotificationStatus(),
    @SerializedName("couponIsUsed") var couponIsUsed: Boolean? = null,
    @SerializedName("coverPhoto") var coverPhoto: String? = null,
    @SerializedName("location") var location: String? = null,
    @SerializedName("interest") var interest: String? = null,
    @SerializedName("registrationDate") var registrationDate: String? = null,
    @SerializedName("modificationData") var modificationData: String? = null,
    @SerializedName("isSocial") var isSocial: Boolean? = null,
    @SerializedName("isOnline") var isOnline: Boolean? = null,
    @SerializedName("blocked") var blocked: ArrayList<String> = arrayListOf(),
    @SerializedName("isActive") var isActive: Boolean? = null,
    @SerializedName("_id") var Id: String? = null,
    @SerializedName("email") var email: String? = null,
    @SerializedName("password") var password: String? = null,
    @SerializedName("userName") var userName: String? = null,
    @SerializedName("profilePhoto") var profilePhoto: String? = null,
    @SerializedName("role") var role: String? = null,
    @SerializedName("customerId") var customerId: String? = null,
    @SerializedName("socialNetwork") var socialNetwork: String? = null,
    @SerializedName("price") var price: ArrayList<NotificationPrice> = arrayListOf(),
    @SerializedName("__v") var _v: Int? = null,
    @SerializedName("latestToken") var latestToken: String? = null,
    @SerializedName("aboutYou") var aboutYou: String? = null,
    @SerializedName("name") var name: String? = null,
//    @SerializedName("availaibility") var availaibility: ArrayList<NotificationAvailibility> = arrayListOf()

)