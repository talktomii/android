package com.furniture.data.model.admin

import java.io.Serializable

data class Admin(
    val __v: Int,
    val _id: String,
    val aboutYou: Any,
    val availaibility: List<Availaibility>,
    val blocked: List<Any>,
    val couponIsUsed: Boolean,
    val coverPhoto: String,
    val customerId: String,
    val email: String,
    val interest: List<Interest>,
    val isActive: Boolean,
    val isSocial: Boolean,
    val latestToken: String,
    val location: String,
    val modificationData: String,
    val name: String,
    val price: List<Price>,
    val profilePhoto: String,
    val registrationDate: String,
    val role: String,
    val socialNetwork: List<SocialNetwork>,
    val status: Status,
    val userName: String
):Serializable