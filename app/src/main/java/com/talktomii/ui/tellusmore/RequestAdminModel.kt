package com.talktomii.ui.tellusmore

data class RequestAdminModel(
    val interest: List<String>,
    val location: String,
    val socialNetwork: List<SocialNetwork>
)