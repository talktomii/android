package com.furniture.data.model.drawer.bookmark

data class Ifid(
    val __v: Int,
    val _id: String,
    val aboutYou: Any,
    val availaibility: List<Availaibility>,
    val blocked: List<String>,
    val couponIsUsed: Boolean,
    val coverPhoto: Any,
    val customerId: String,
    val email: String,
    val interest: List<String>,
    val isActive: Boolean,
    val isSocial: Boolean,
    val latestToken: String,
    val location: Any,
    val modificationData: String,
    val name: String,
    val price: List<Price>,
    val profilePhoto: String,
    val registrationDate: String,
    val role: String,
    val socialNetwork: List<SocialNetwork>,
    val status: Status,
    val userName: String
)