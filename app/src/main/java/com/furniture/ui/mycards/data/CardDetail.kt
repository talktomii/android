package com.furniture.ui.mycards.data

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
    val payload : ArrayList<getAllData>? = null
)

data class getAllData(
    val card : List<CardDetail>?=null
)

