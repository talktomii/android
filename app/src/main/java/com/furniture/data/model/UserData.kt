package com.furniture.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserData(
    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("username")
    val username: String? = null,

    @field:SerializedName("city")
    val city: String? = null,

    @field:SerializedName("city_id")
    val city_id: Int? = null,

    @field:SerializedName("token")
    val token: String? = null,

    @field:SerializedName("access_token")
    val access_token: String? = null,

    @field:SerializedName("id")
    val id: String? = null,

    @field:SerializedName("user_id")
    val user_id: Long? = null,

    @field:SerializedName("country_code")
    val country_code: String? = null,

    @field:SerializedName("phone_no")
    val phone_no: String? = null,

    @field:SerializedName("email")
    val email: String? = null,

    @field:SerializedName("email_verified_at")
    val email_verified_at: String? = null,

    @field:SerializedName("image")
    val image: String? = null,

    @field:SerializedName("date_of_birth")
    val date_of_birth: String? = null,

    @field:SerializedName("gender")
    val gender: String? = null,

    @field:SerializedName("status")
    val status: String? = null,

    @field:SerializedName("device_type")
    val device_type: String? = null,

    @field:SerializedName("notification_token")
    val notification_token: String? = null,

    @field:SerializedName("notification_enabled")
    val notification_enabled: Int? = null,

    @field:SerializedName("address")
    val address: String? = null,

    @field:SerializedName("latitude")
    val latitude: String? = null,

    @field:SerializedName("longitude")
    val longitude: String? = null,

    @field:SerializedName("updated_at")
    val updated_at: String? = null,

    @field:SerializedName("created_at")
    val created_at: String? = null, ):Parcelable
