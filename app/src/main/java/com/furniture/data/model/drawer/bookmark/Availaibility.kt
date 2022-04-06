package com.furniture.data.model.drawer.bookmark

data class Availaibility(
    val _id: String,
    val day: List<String>,
    val end: String,
    val endTime: String,
    val isBooked: Boolean,
    val startTime: String
)