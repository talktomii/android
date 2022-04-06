package com.furniture.data.model.admin1

import androidx.databinding.BaseObservable
import com.furniture.data.model.admin.*
import java.io.Serializable

data class Admin1(
    val __v: Int,
    val _id: String,
    val aboutYou: Any,
    val availaibility: ArrayList<Availaibility>,
    val blocked: List<Any>,
    val couponIsUsed: Boolean,
    val coverPhoto: String,
    val customerId: String,
    val email: String,
    val interest: ArrayList<Interest>,
    val isActive: Boolean,
    var bookmark: Boolean,
    val isSocial: Boolean,
    val latestToken: String,
    val location: String,
    val modificationData: String,
    val name: String,
    val price: ArrayList<Price>,
    val profilePhoto: String,
    val registrationDate: String,
    val role: Role,
    val socialNetwork: ArrayList<SocialNetwork>,
    val status: Status,
    val userName: String
) : Serializable

data class Role(
    val _id: String,
    val roleName: String
)
