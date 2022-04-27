package com.talktomii.data.model

class TimeSlotSpinner(val time: String, val position: Int) {
    override fun toString(): String {
        return "$time min"
    }
}