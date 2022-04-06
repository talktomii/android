package com.talktomii.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class CartModel(
    val id: Int? = 0,
    var service_id: Int? = 0,
    var product_id: Int? = 0,
    var product_type_id: Int? = 0,
    var product_category_ids: String? = null,
    var purchased_from: String? = null,
    var purchased_date: String? = null,
    var images: ArrayList<String>? = null
) : Parcelable

@Parcelize
data class JobStatus(
    val id: Int? = 0,
    val status: String? = null,
) : Parcelable


@Parcelize
class CartResponseModel(
    val vendor_availability: CartResponseModel? = null,
    val progress_status: JobStatus? = null,
    val next_progress_status: JobStatus? = null,
    val vendor: UserModel? = null,
    val rating: RatingReviewModel? = null,
    val id: Int? = 0,
    val amount: Float? = 0F,
    val tax: Float? = 0F,
    val delivery_charge: Float? = 0F,
    val net_amount: Float? = 0F,
    val coupon_code: String? = null,
    val reference_no: String? = null,
    val is_completed: Int? = 0,
    val is_completed_by_agent: Int? = 0,
    val order_id: Int? = 0,
    val from_datetime: String? = null,
    val to_datetime: String? = null,
    val address: String? = null,
    val status: Int? = 0,
    val latitude: String? = null,
    val longitude: String? = null,
    val block_no: String? = null,
    val city: String? = null,
    val zipcode: String? = null,
    val created_at: String? = null,
    var service_id: Int? = 0,
    var product_id: Int? = 0,
    var product_type_id: Int? = 0,
    var product_categories: ArrayList<ProductsModel>? = null,
    var service: ProductsModel? = null,
    var product: ProductsModel? = null,
    var product_type: ProductsModel? = null,
    var images: ArrayList<ImageModel>? = null,
    var cart: ArrayList<CartResponseModel>? = null,
    var available_dates: ArrayList<String>? = null,
    var order: CartResponseModel? = null
) : Parcelable

@Parcelize
data class CartResponse(
    val my_cart: ArrayList<CartResponseModel>? = null,
    val data: ArrayList<CartResponseModel>? = null,
    val defaultAddress: AddressModel? = null,
    val current_page: Int? = 0
) : Parcelable

