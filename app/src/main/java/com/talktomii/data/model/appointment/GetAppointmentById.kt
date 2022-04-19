package com.talktomii.data.model.appointment

data class AppointmentByIdPayload(
    val Appointment: AppointmentInterestItem,
)


data class GetAppointmentById(
    val result: Int = 0,
    val payload: AppointmentByIdPayload,
    val message: String = ""
)


