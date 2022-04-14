package com.talktomii.data.model.appointment

data class ItemUpdate(
    val date: String = "",
    val isDelete: Any? = null,
    val ifid: String = "",
    val duration: Int = 0,
    val uid: String = "",
    val createdAt: String = "",
    val price: Int = 0,
    val V: Int = 0,
    val startTime: String = "",
    val _id: String = "",
    val endTime: String = "",
    val status: String = "",
    val updatedAt: String = ""
)


data class PayloadUpdate(val Item: ItemUpdate)


data class UpdateAppontmentResponse(
    val result: Int = 0,
    val payload: PayloadUpdate,
    val message: String = ""
)


