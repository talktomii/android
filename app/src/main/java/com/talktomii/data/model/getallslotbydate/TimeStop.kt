package com.talktomii.data.model.getallslotbydate

data class TimeStop(
    val slot: ArrayList<String>,
    val time: String,
    var isSelected : Boolean
)