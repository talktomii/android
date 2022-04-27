package com.talktomii.data.model.admin1

import com.talktomii.data.model.Interest
import com.talktomii.data.model.admin.Availaibility
import com.talktomii.data.model.admin.Price
import com.talktomii.data.model.admin.SocialNetwork
import com.talktomii.data.model.admin.Status
import java.io.Serializable

data class Admin1(
    val __v: Int,
    val _id: String,
    var aboutYou: Any,
    var availaibility: ArrayList<Availaibility>,
    val blocked: List<Any>,
    val couponIsUsed: Boolean,
    val coverPhoto: String,
    val customerId: String,
    val email: String,
    var interest: ArrayList<Interest>,
    val isActive: Boolean,
    var bookmark: Boolean,
    val isSocial: Boolean,
    val latestToken: String,
    var location: String,
    val modificationData: String,
    var name: String,
    var fname: String,
    var lname: String,
//    var priceList: ArrayList<Int>,
    var price: ArrayList<Price>,
    val profilePhoto: String,
    val registrationDate: String,
    val role: Role,
    val socialNetwork: ArrayList<SocialNetwork>,
    val status: Status,
    var userName: String,
    val badges: ArrayList<BadgesItem> = arrayListOf()

) : Serializable

data class Role(
    val _id: String,
    val roleName: String
)
