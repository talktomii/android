package com.talktomii.data.model

data class PaymentResponse(
    val invoice_id: Int,
    val link: String,
    val is_paid: Int,
    val payment_id: String
)