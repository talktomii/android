package com.talktomii.ui.callhistory.models

import com.talktomii.recycleradapter.AbstractModel

data class CallHistoryItemModel(
    val ifid : String,
    val if_Username : String,
    val if_profile : String,
    val id : String,
    val call_Img: String,
    val call_name: String,
    val call_date: String,
    val call_rs : Int,
    val call_time : Int
) : AbstractModel()