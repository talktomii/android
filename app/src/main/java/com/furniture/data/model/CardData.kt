package com.furniture.data.model

data class CardData(
    val card_no: String,
    val created_at: String,
    val expiry_month: String,
    val expiry_year: String,
    val id: Int,
    val security_code: String,
    val input_type: String,
    val updated_at: String
)