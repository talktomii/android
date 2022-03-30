package com.talktomii.ui.mycards.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CardDetail(
    val _id: String,
    val uid: String,
    val cardNumber: String,
    val expiryDate: String,
    val cvv: String,
    val holderName: String,
    val __v: String
) : Parcelable

data class getCardDetail(
    val message : String,
    val payload : getAllData? = null
)
data class getAllData(
    val card : Array<lList>?=null
)
data class lList(
    val data : List<internalData> ?= null
)
data class internalData(
    val card : ArrayList<allcards> ?= null
)
data class allcards(
    val last4 : String ?= null
)
data class addCardData(
    val message : String
)


