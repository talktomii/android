package com.talktomii.ui.mycards.model

import com.google.gson.annotations.SerializedName


data class Payload(

    @SerializedName("card") var card: Card? = Card()

)