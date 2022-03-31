package com.talktomii.data.model

import java.io.Serializable

data class PushData(
    val msg: String,
    var title: String,
    val sound: String
) : Serializable