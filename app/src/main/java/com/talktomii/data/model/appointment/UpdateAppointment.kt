package com.talktomii.data.model.appointment

data class Item(
    val date: String = "",
    val isDelete: Boolean = false,
    val ifid: String = "",
    val duration: Int = 0,
    val uid: String = "",
    val createdAt: String = "",
    val price: Int = 0,
    val V: Int = 0,
    val startTime: String = "",
    val _id: String = "",
    val endTime: String = "",
    val status: String = "",
    val updatedAt: String = ""
)


data class UpdateAppointmentPayload(val item: Item)


data class UpdateAppointment(
    val result: Int = 0,
    val payload: UpdateAppointmentPayload,
    val message: String = ""
)


