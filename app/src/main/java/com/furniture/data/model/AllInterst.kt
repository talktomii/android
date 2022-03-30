package com.furniture.data.model

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
    val updatedAt: String
)
data class Payload(
    val count: Int,
    val interest: ArrayList<Interest>
)