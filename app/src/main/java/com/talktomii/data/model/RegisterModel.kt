package com.talktomii.data.model

data class RegisterModel(
    val admin: Admin,
    val token: String,
    val token_type: String
)