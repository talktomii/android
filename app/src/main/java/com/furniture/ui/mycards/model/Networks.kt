package com.example.example

import com.google.gson.annotations.SerializedName


data class Networks(

    @SerializedName("available") var available: ArrayList<String> = arrayListOf(),
    @SerializedName("preferred") var preferred: String? = null

)