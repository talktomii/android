package com.talktomii.data.model

data class HomeModel(
    val banner: ArrayList<BannerModel>? = null,
    val all_zones: ArrayList<ZonesData>? = null,
    val all_cities: ArrayList<ZonesData>? = null,
    val most_popular:ArrayList<MostPopularModel>?=null,
    val all_services:ArrayList<MostPopularModel>?=null,
    val offerServices:ArrayList<MostPopularModel>?=null,
    val offerProducts:ArrayList<ProductsModel>?=null,
    val latest_reviews:ArrayList<CustomerReviewModel>?=null
)