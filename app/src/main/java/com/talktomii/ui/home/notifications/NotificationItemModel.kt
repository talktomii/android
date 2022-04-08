package com.talktomii.ui.home.notifications

import com.talktomii.recycleradapter.AbstractModel

data class NotificationItemModel(
    val n_id: String,
    val n_title: String,
    val n_name : String,
    val n_image : String,
    val n_duration : String
) : AbstractModel()