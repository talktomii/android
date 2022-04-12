package com.talktomii.data.model

import androidx.databinding.ObservableBoolean
import com.talktomii.recycleradapter.AbstractModel

data class AllInterst(
    val message: String,
    val payload: Payload,
    val result: Int
)
data class Interest(
    val __v: Int,
    val _id: String,
    val createdAt: String,
    val description: String,
    val image: String,
    val name: String,
    var isClicked: ObservableBoolean = ObservableBoolean(false),
    val updatedAt: String
): AbstractModel()
data class Payload(
    val count: Int,
    val interest: List<Interest>
)