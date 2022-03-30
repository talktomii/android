package com.example.example

import com.google.gson.annotations.SerializedName


data class PayloadCards (

  @SerializedName("result"  ) var result  : Int?     = null,
  @SerializedName("message" ) var message : String?  = null,
  @SerializedName("payload" ) var payload : Payload? = Payload()

)