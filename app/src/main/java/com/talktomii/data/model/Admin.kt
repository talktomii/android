package com.talktomii.data.model

import com.talktomii.data.model.admin.Price

data class Admin(
    val _id: String,
    val coverPhoto: String,
    val email: String,
    val isSocial: Boolean,
    val couponIsUsed: Boolean,
    val name: String,
    val fname: String,
    val lname: String,
    val profilePhoto: String,
    val role: Role,
    val status: Status,
    val userName: String,
    var price: ArrayList<Price>,
)