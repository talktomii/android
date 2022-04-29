package com.talktomii.data.model.admin

data class Availaibility(
    var _id: String = "",
    var day: List<String> = arrayListOf(),
    var end: String ="",
    var endTime: String ="",
    val isBooked: Boolean = false,
    var startTime: String = ""
)

data class SendAvailaibility(
    var day: List<String> = arrayListOf(),
    var end: String ="",
    var endTime: String ="",
    val isBooked: Boolean = false,
    var startTime: String =""
)