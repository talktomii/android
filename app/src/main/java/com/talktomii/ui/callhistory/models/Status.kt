package com.talktomii.ui.callhistory.models

import com.google.gson.annotations.SerializedName


data class Status (

  @SerializedName("name"             ) var name             : String? = null,
  @SerializedName("modificationDate" ) var modificationDate : String? = null

)