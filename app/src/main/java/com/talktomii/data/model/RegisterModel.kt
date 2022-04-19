package com.talktomii.data.model

data class RegisterModel(
    var admin: Admin,
    val token: String,
    val token_type: String
)