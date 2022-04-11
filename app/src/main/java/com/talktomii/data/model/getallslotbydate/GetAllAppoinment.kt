package com.talktomii.data.model.getallslotbydate

data class Status(
    val modificationDate: String = "",
    val name: String = ""
)


data class SocialNetworkItem(
    val name: String = "",
    val link: String = "",
    val Id: String = ""
)


data class Uid(
    val isSocial: Boolean = false,
    val role: String = "",
    val isOnline: Boolean = false,
    val isActive: Boolean = false,
    val password: String = "",
    val profilePhoto: String = "",
    val blocked: List<String>?,
    val interest: List<InterestItem>?,
    val price: List<PriceItem>?,
    val V: Int = 0,
    val aboutYou: String = "",
    val registrationDate: String = "",
    val customerId: String = "",
    val email: String = "",
    val coverPhoto: String = "",
    val latestToken: String = "",
    val userName: String = "",
    val token: String = "",
    val couponIsUsed: Boolean = false,
    val name: String = "",
    val location: String = "",
    val Id: String = "",
    val socialNetwork: List<SocialNetworkItem>?,
    val modificationData: String = "",
    val status: Status
)


data class GetAllAppoinments(
    val result: Int = 0,
    val payload: PayloadAppoinment,
    val message: String = ""
)


data class PriceItem(
    val price: Int = 0,
    val time: String = "",
    val Id: String = ""
)


data class AvailaibilityItem(
    val startTime: String = "",
    val end: String = "",
    val isBooked: Boolean = false,
    val endTime: String = "",
    val Id: String = "",
    val day: List<String>?
)


data class Ifid(
    val isSocial: Boolean = false,
    val role: String = "",
    val availaibility: List<AvailaibilityItem>?,
    val isOnline: Boolean = false,
    val isActive: Boolean = false,
    val password: String = "",
    val profilePhoto: String = "",
    val interest: List<InterestItem>?,
    val price: List<Integer>?,
    val V: Int = 0,
    val aboutYou: String = "",
    val registrationDate: String = "",
    val customerId: String = "",
    val email: String = "",
    val coverPhoto: String = "",
    val latestToken: String = "",
    val userName: String = "",
    val token: String = "",
    val couponIsUsed: Boolean = false,
    val name: String = "",
    val location: String = "",
    val Id: String = "",
    val modificationData: String = "",
    val status: Status
)


data class PayloadAppoinment(
    val interest: List<InterestItem>?,
    val count: Int = 0
)


data class InterestItem(
    val date: String = "",
    val isDelete: Boolean = false,
    val ifid: Ifid,
    val duration: Int = 0,
    val uid: Uid,
    val createdAt: String = "",
    val price: Int = 0,
    val V: Int = 0,
    val startTime: String = "",
    val Id: String = "",
    val endTime: String = "",
    val status: String = "",
    val updatedAt: String = ""
)


