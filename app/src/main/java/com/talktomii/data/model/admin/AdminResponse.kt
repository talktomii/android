package com.talktomii.data.model.admin

data class AdminResponse(
    val message: String,
    val payload: Payload,
    val result: Int
)