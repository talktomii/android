package com.talktomii.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductsModel(
    val id: Int? = 0,
    val name: String? = null,
    val name_arabic: String? = null,
    val description_arabic: String? = null,
    val product: ProductsModel? = null,
    val description: String? = null,
    val url: String? = null,
    val created_at: String? = null,
    val updated_at: String? = null,
    val product_types_count: Int? = 0,
    val hours: Int? = 0,
    val price: Int? = 0,
    val product_id: Int? = 0,
    val customer_favorite: Int? = 0,
    val trending_product: Int? = 0,
    val apply_offer: OffersData? = null,
    val full_image: String? = null,
    var isSelected: Boolean? = false,
) : Parcelable

@Parcelize
data class OffersData(
    val id: Int? = 0,
    val service_id: Int? = 0,
    val product_id: Int? = 0,
    val product_type_id: Int? = 0,
    val product_category_id: Int? = 0,
    val offer_id: Int? = 0,
    val value: String? = null,
    val title: String? = null,
    val type: String? = null,
    val symbol: String? = null,
    val offer: OffersData? = null,
) : Parcelable