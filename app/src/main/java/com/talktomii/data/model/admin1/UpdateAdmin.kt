package com.talktomii.data.model.admin1

data class UpdateAdminRole(
    val createdAt: String = "",
    val roleName: String = "",
    val description: String = "",
    val Id: String = "",
    val isActivate: String = "",
    val updatedAt: String = ""
)


data class Status(
    val modificationDate: String = "",
    val name: String = ""
)


data class Payload(val admin: com.talktomii.data.model.Admin)


data class UpdateAdmin(
    val result: Int = 0,
    val payload: Payload,
    val message: String = ""
)


//data class Admin(
//    val isSocial: Boolean = false,
//    val role: UpdateAdminRole,
//    val isOnline: Boolean = false,
//    val isActive: Boolean = false,
//    val lname: String = "",
//    val profilePhoto: String = "",
//    val V: Int = 0,
//    val registrationDate: String = "",
//    val customerId: String = "",
//    val email: String = "",
//    val fname: String = "",
//    val coverPhoto: String? = null,
//    val latestToken: String = "",
//    val userName: String = "",
//    val appleId: String? = null,
//    val token: String? = null,
//    val couponIsUsed: Boolean = false,
//    val location: String? = null,
//    val _id: String = "",
//    val modificationData: String = "",
//    val status: Status
//)


