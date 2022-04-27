package com.talktomii.data.model.admin

data class Availaibility(
    val _id: String,
    val day: List<String>,
    var end: String,
    val endTime: String,
    val isBooked: Boolean,
    val startTime: String
)

data class SendAvailaibility(
    var day: List<String> = arrayListOf(),
    var end: String ="",
    var endTime: String ="",
    val isBooked: Boolean = false,
    var startTime: String =""
)