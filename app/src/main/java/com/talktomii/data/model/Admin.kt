package com.talktomii.data.model

data class Admin(
    val _id: String,
    val coverPhoto: String,
    val email: String,
    val isSocial: Boolean,
    val couponIsUsed: Boolean,
    val name: String,
    val profilePhoto: String,
    val role: Role,
    val status: Status,
    val userName: String
)