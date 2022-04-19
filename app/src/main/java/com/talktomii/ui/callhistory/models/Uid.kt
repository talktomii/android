package com.talktomii.ui.callhistory.models

import com.google.gson.annotations.SerializedName


data class Uid(

    @SerializedName("status") var status: Status? = Status(),
    @SerializedName("couponIsUsed") var couponIsUsed: Boolean? = null,
    @SerializedName("coverPhoto") var coverPhoto: String? = null,
    @SerializedName("location") var location: String? = null,
    @SerializedName("interest") var interest: ArrayList<String> = arrayListOf(),
    @SerializedName("registrationDate") var registrationDate: String? = null,
    @SerializedName("modificationData") var modificationData: String? = null,
    @SerializedName("isSocial") var isSocial: Boolean? = null,
    @SerializedName("blocked") var blocked: ArrayList<String> = arrayListOf(),
    @SerializedName("isActive") var isActive: Boolean? = null,
    @SerializedName("_id") var Id: String? = null,
    @SerializedName("email") var email: String? = null,
    @SerializedName("password") var password: String? = null,
    @SerializedName("userName") var userName: String? = null,
    @SerializedName("profilePhoto") var profilePhoto: String? = null,
    @SerializedName("role") var role: String? = null,
    @SerializedName("customerId") var customerId: String? = null,
    @SerializedName("socialNetwork") var socialNetwork: ArrayList<SocialNetwork> = arrayListOf(),
    @SerializedName("price") var price: ArrayList<Price> = arrayListOf(),
    @SerializedName("availaibility") var availaibility: ArrayList<String> = arrayListOf(),
    @SerializedName("__v") var _v: Int? = null,
    @SerializedName("latestToken") var latestToken: String? = null,
    @SerializedName("aboutYou") var aboutYou: String? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("fname") var fname: String? = null,
    @SerializedName("lname") var lname: String? = null

)