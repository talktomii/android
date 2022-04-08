package com.talktomii.data.photo

data class UpdatePhoto(
    val result: Int,
    val message: String,
    val payload: Payload
)

data class Payload(
    val admin: Admin
)

data class Admin(
    val status: Status,
    val couponIsUsed: Boolean,
    val coverPhoto: String,
    val location: Any? = null,
    val interest: Any? = null,
    val registrationDate: String,
    val modificationData: String,
    val isSocial: Boolean,
    val isOnline: Boolean,
    val blocked: List<Any?>,
    val isActive: Boolean,
    val _id: String,

    val email: String,
    val password: String,
    val name: String,
    val userName: String,
    val role: String,
    val __v: Long,

    val profilePhoto: String,
    val aboutYou: Any? = null,
    val availaibility: List<Availaibility?>,
    val price: Any? = null,
    val socialNetwork: Any? = null,
    val latestToken: String
)

data class Availaibility(
    val startTime: String,
    val endTime: String,
    val day: List<String>,
    val end: String,
    val isBooked: Boolean,
    val _id: String
)

data class Status(
    val name: String,
    val modificationDate: String
)