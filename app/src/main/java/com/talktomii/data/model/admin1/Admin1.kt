package com.talktomii.data.model.admin1

import com.talktomii.data.model.admin.*
import java.io.Serializable

data class Admin1(
    var __v: Int?=null,
    var _id: String?=null,
    var aboutYou: Any?=null,
    var availaibility: ArrayList<Availaibility>?=null,
    var blocked: List<Any>?=null,
    var couponIsUsed: Boolean?=null,
    var coverPhoto: String?=null,
    var customerId: String?=null,
    var email: String?=null,
    var interest: ArrayList<Interest>?=null,
    var isActive: Boolean?=null,
    var bookmark: Boolean?=null,
    var isSocial: Boolean?=null,
    var latestToken: String?=null,
    var location: String?=null,
    var modificationData: String?=null,
    var name: String?=null,
    var price: ArrayList<Price>?=null,
    var profilePhoto: String?=null,
    var registrationDate: String?=null,
    var role: Role?=null,
    var socialNetwork: ArrayList<SocialNetwork>?=null,
    var status: Status?=null,
    var userName: String?=null
) : Serializable

data class Role(
    val _id: String,
    val roleName: String
)
