package com.talktomii.data.model

data class MostPopularModel(
    val id: Int? = null,
    val offer_id: Int? = null,
    val service_id: Int? = null,
    val product_id: Int? = null,
    val product_type_id: Int? = null,
    val product_category_id: Int? = null,
    val name: String? = null,
    val name_arabic: String? = null,
    val url: String? = null,
    val full_image: String? = null,
    val created_at: String? = null,
    val service: MostPopularModel? = null,
    val updated_at: String? = null
)

