package com.talktomii.data.model.getallslotbydate

data class TimeStop(
    val slot: ArrayList<String>,
    val slotSelectedTimeSlots: ArrayList<TimeSlotsWithData>,
    val time: String,
)

data class TimeSlotsWithData(
    var slot: String,
    var isSelected: Boolean
)