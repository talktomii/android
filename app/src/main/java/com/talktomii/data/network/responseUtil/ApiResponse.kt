package com.talktomii.data.network.responseUtil

data class ApiResponse<out T>(
    val message: String? = null,
    val status: Int? = 0,
    /*val data: T? = null*/
    val detail: T? = null,
    val data: T? = null
)

