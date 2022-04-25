package com.talktomii.data.model.admin

data class Payload(
    val admin: ArrayList<Admin> = arrayListOf()     ,
    val count: Int
)