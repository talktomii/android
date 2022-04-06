package com.furniture.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TimeSlotModel(
    val from_datetime: String? = null,
    val to_datetime: String? = null,
) : Parcelable