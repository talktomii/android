package com.talktomii.data.model.appointment

data class SocialNetworkItem(
    val name: String = "",
    val link: String? = null,
    val Id: String = ""
)


data class AppointmentStatus(
    val modificationDate: String = "",
    val name: String = ""
)


data class AppointmentUid(
    val isSocial: Boolean = false,
    val role: String = "",
    val isOnline: Boolean = false,
    val isActive: Boolean = false,
    val password: String = "",
    val lname: String = "",
    val profilePhoto: String = "",
    val interest: List<AppointmentInterestItem>?,
    val V: Int = 0,
    val aboutYou: String? = null,
    val registrationDate: String = "",
    val customerId: String = "",
    val email: String = "",
    val fname: String = "",
    val coverPhoto: String = "",
    val latestToken: String = "",
    val userName: String = "",
    val token: String? = null,
    val couponIsUsed: Boolean = false,
    val location: String = "",
    val Id: String = "",
    val modificationData: String = "",
    val status: AppointmentStatus
)


data class AppointmentPriceItem(
    val price: Int = 0,
    val time: String = "",
    val Id: String = ""
)


data class AppointmentAvailaibilityItem(
    val startTime: String = "",
    val end: String = "",
    val isBooked: Boolean = false,
    val endTime: String = "",
    val Id: String = "",
    val day: List<String>?
)


data class AppointmentIfid(
    val isSocial: Boolean = false,
    val role: String = "",
    val availaibility: List<AppointmentAvailaibilityItem>?,
    val isOnline: Boolean = false,
    val isActive: Boolean = false,
    val password: String = "",
    val lname: String = "",
    val profilePhoto: String = "",
    val interest: List<AppointmentInterestItem>?,
    val price: List<AppointmentPriceItem>?,
    val V: Int = 0,
    val aboutYou: String = "",
    val registrationDate: String = "",
    val customerId: String = "",
    val email: String = "",
    val fname: String = "",
    val coverPhoto: String? = null,
    val latestToken: String = "",
    val userName: String = "",
    val token: String? = null,
    val couponIsUsed: Boolean = false,
    val location: String = "",
    val Id: String = "",
    val _id: String = "",
    val socialNetwork: List<SocialNetworkItem>?,
    val modificationData: String = "",
    val status: AppointmentStatus
)


data class AppointmentPayload(
    val interest: ArrayList<AppointmentInterestItem>?,
    val count: Int = 0
)


data class GetAllCalenderAppoinment(
    val result: Int = 0,
    val payload: AppointmentPayload,
    val message: String = ""
)


data class AppointmentInterestItem(
    val date: String = "",
    val isDelete: Boolean = false,
    val ifid: AppointmentIfid,
    val duration: Int = 0,
    val uid: AppointmentUid,
    val createdAt: String = "",
    val price: Int = 0,
    val V: Int = 0,
    val startTime: String = "",
    val _id: String = "",
    val endTime: String = "",
    val status: String = "",
    val updatedAt: String = ""
)


