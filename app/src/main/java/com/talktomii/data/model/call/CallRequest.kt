package com.talktomii.data.model.call

import com.talktomii.data.model.admin1.Admin1

data class CallRequest(
    val channelName: String,
    val isForVideoCall: Boolean,
    val loginUser: CallUser,
    val msg: String,
    val otherId: String,
    val otherUser: CallUser,
    val token: String
)