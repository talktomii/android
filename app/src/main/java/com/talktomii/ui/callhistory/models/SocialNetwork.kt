package com.talktomii.ui.callhistory.models

import com.google.gson.annotations.SerializedName


data class SocialNetwork(

    @SerializedName("name") var name: String? = null,
    @SerializedName("link") var link: String? = null,
    @SerializedName("_id") var Id: String? = null

)