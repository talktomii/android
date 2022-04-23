package com.talktomii.data.model.admin1

import com.talktomii.data.model.drawer.bookmark.Ifid
import com.talktomii.data.model.drawer.bookmark.Uid


data class NearestAppointmentItem(
    val __v: Int,
    val _id: String,
    val createdAt: String,
    val date: String,
    val duration: Int,
    val endTime: String,
    val ifid: Ifid,
    val isDelete: Boolean,
    val isDeleteFor: List<Any>,
    val price: Int,
    val startTime: String,
    val status: String,
    val uid: Uid,
    val updatedAt: String
)


data class AvailaibilityItem(
    val _id: String,
    val day: List<String>,
    val end: String,
    val endTime: String,
    val isBooked: Boolean,
    val startTime: String
)


data class UsersDataItem(
    val __v: Int,
    val _id: String,
    val aboutYou: String,
    val appleId: Any,
    val availaibility: List<AvailaibilityItem>,
    val blocked: List<Any>,
    val couponIsUsed: Boolean,
    val coverPhoto: String,
    val email: String,
    val fname: String,
    val interest: List<String>,
    val isActive: Boolean,
    val isOnline: Boolean,
    val isSocial: Boolean,
    val latestToken: String,
    val lname: String,
    val location: String,
    val modificationData: String,
    val name: String,
    val password: String,
    val permission: List<Any>,
    val price: Any,
    val profilePhoto: String,
    val registrationDate: String,
    val role: String,
    val socialNetwork: Any,
    val status: Status,
    val token: String,
    val userName: String
)

data class ResponseInfluencerDashboard(
    val result: Int = 0,
    val payload: PayloadDashBoard,
    val message: String = ""
)

data class PayloadDashBoard(
    val usersData: ArrayList<UsersDataItem>?,
    val totalHours: String = "",
    val nearestAppointment: ArrayList<NearestAppointmentItem>?,
    val earning: Int = 0
)


