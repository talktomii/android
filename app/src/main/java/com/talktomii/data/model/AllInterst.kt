package com.talktomii.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class AllInterst(
    val message: String,
    val payload: Payload,
    val result: Int
)

@Parcelize
data class Interest(
    val __v: Int,
    val _id: String,
    val createdAt: String,
    val description: String,
    val image: String,
    val name: String,
    val updatedAt: String,
    var isClicked: Boolean
) : Parcelable

data class Payload(
    val count: Int,
    val interest: ArrayList<Interest>
)