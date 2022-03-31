package com.talktomii.ui.callhistory.models

import com.talktomii.recycleradapter.AbstractModel

data class CallHistoryItemModel(
    val call_Img: Int,
    val call_name: String,
    val call_date: String,
    val call_rs : String,
    val call_time : String
) : AbstractModel()