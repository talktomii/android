package com.furniture.data.apis


import com.example.example.ExampleJson2KtKotlin
import com.furniture.adapter.MyCardAdapter
import com.furniture.data.model.*
import com.furniture.data.network.responseUtil.ApiResponse
import com.furniture.ui.mycards.data.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*


interface WebService {
    companion object {
        private const val SEND_OTP = "user/sendOTP"
        private const val ADD_PHONE = "user/add_phone_number"
        private const val SEND_OTP_SOCIAL = "user/verify_phone_after_social_login"
        private const val VERIFY_OTP = "user/verify_phone_with_otp"
        private const val LOGOUT = "user/logout"
        private const val GET_USER = "user/getuser"
        private const val EDIT_PROFILE = "user/edit_profile"
        private const val HOME = "home"
        private const val GET_PRODUCTS = "product/basedOnServiceId"
        private const val GET_SUB_PRODUCTS = "product/product_type"
        private const val GET_PRODUCTS_SERVICES = "product/product_categories"
        private const val ADD_ADDRESS = "address/add"
        private const val GET_ADDRESS = "address/my_addresses"
        private const val UPLOAD_IMAGE = "product/upload_image"
        private const val ADD_TO_CART = "product/add_to_cart"
        private const val PLACE_ORDER = "order/place_order"
        private const val REMOVE_SERVICE_FROM_CART = "product/deleteCartItem"
        private const val MY_CART = "product/my_cart"
        private const val MY_ORDERS = "order/listing"
        private const val ORDER_DETAIL = "order/detail"
        private const val NOTIFICATION_LIST = "notification/my_notification"
        private const val PAYMENT_STATUS = "payment-status"
        private const val DATETIME_BY_ORDER = "order/detail_with_availabledate"
        private const val TIME_SLOTS_API = "order/available_timings_based_on_date"
        private const val MAKE_PAYMENT_OF_ORDER = "order/make_payment"
        private const val ADD_RATING_REVIEW = "rating/provide_rating"
        private const val OFFERED_SERVICES = "offer/services_based_on_offer_id"
        private const val OFFERED_PRODUCTS = "offer/product_based_on_offer_id"
        private const val ALL_ZONES = "address/all_zones"
        private const val ALL_CITIES = "address/all_cities"
        private const val UPDATE_ORDER_STATUS = "vendor/order/change_status"
        private const val VALIDATE_COUPON_CODE = "order/validate_coupon"
        private const val ADD_CARD = "card/addCard"
        const val UPDATE_CARD = "card/updateCard/"
        val NEW_UPDATE_CARD : String = MyCardAdapter.update_url.toString()
        private const val DELETE_CARD_ITEM = "card/deleteCard/"
        private const val PAYMENT_PROCCESS = "fatoorah"
        private const val GET_CARDS = "card/getCardByuid/623dac1b6ee1ef298842b9ad"
        private const val DELETE_CARD = "delete-card"
        private const val SOCIAL_LOGIN = "check_social_id"

    }


    @FormUrlEncoded
    @POST(SOCIAL_LOGIN)
    fun socialLogin(@FieldMap hashMap: HashMap<String, String>): Call<ApiResponse<UserData>>

    @FormUrlEncoded
    @POST("https://api.instagram.com/oauth/access_token")
    fun instaUserApis(
        @FieldMap hashMap: HashMap<String, String>
    ): Call<UserData>

    @GET("https://graph.instagram.com/{user_id}")
    fun getInstaUserApis(
        @Path("user_id") user_id: String,
        @Query("fields") fields: String,
        @Query("access_token") access_token: String
    ): Call<UserData>



    @FormUrlEncoded
    @POST(SEND_OTP)
    fun sendOTP(@FieldMap hashMap: HashMap<String, String>): Call<ApiResponse<Any>>


    @POST(DELETE_CARD)
    fun deleteCard(@Query("card_id")id: Int?): Call<ApiResponse<Any>>

    @FormUrlEncoded
    @POST(ADD_PHONE)
    fun addPhoneApi(@FieldMap hashMap: HashMap<String, String>): Call<ApiResponse<Any>>

    @FormUrlEncoded
    @POST(SEND_OTP_SOCIAL)
    fun verifyPhone(@FieldMap hashMap: HashMap<String, Any>): Call<ApiResponse<UserData>>

    @FormUrlEncoded
    @POST(VERIFY_OTP)
    fun verifyOTP(@FieldMap hashMap: HashMap<String, Any>): Call<ApiResponse<UserData>>

    @GET(LOGOUT)
    fun logout(): Call<ApiResponse<Any>>

    @GET(GET_USER)
    fun getUser(): Call<ApiResponse<UserData>>

    @GET(GET_CARDS)
    fun getCards(
        @Header("Authorization") auth : String
    ): Call<ExampleJson2KtKotlin>

    @Multipart
    @POST(EDIT_PROFILE)
    fun editProfile(
        @PartMap map: HashMap<String, RequestBody>
    ): Call<ApiResponse<UserData>>

    @GET(HOME)
    fun getHomeData(): Call<ApiResponse<HomeModel>>

    @GET(ALL_ZONES)
    fun getAllZonesApi(
    ): Call<ApiResponse<HomeModel>>

    @GET(ALL_CITIES)
    fun getAllCitiesApi(
    ): Call<ApiResponse<HomeModel>>

    @GET(OFFERED_SERVICES)
    fun getOfferedServices(
        @Query("offer_id") offer_id: String
    ): Call<ApiResponse<HomeModel>>

    @POST(GET_PRODUCTS)
    fun getProductsData(
        @Query("service_id") service_id: String,
        @Query("offer_id") offer_id: String
    ): Call<ApiResponse<List<ProductsModel>>>

    @POST(OFFERED_PRODUCTS)
    fun getOfferProductsApi(
        @Query("service_id") service_id: String,
        @Query("offer_id") offer_id: String
    ): Call<ApiResponse<HomeModel>>

    @POST(GET_SUB_PRODUCTS)
    fun getSubProductsData(
        @Query("product_id") product_id: String,
        @Query("service_id") service_id: String,
        @Query("offer_id") offer_id: String
    ): Call<ApiResponse<List<ProductsModel>>>

    @POST(GET_PRODUCTS_SERVICES)
    fun getProductsServicesApi(
        @Query("product_id") product_id: String,
        @Query("service_id") service_id: String,
        @Query("product_type_id") product_type_id: String,
        @Query("offer_id") offer_id: String
    ): Call<ApiResponse<List<ProductsModel>>>


    @POST(ADD_ADDRESS)
    @FormUrlEncoded
    fun addAddress(
        @FieldMap addressData: HashMap<String, String>
    ): Call<ApiResponse<AddressModel>>

    @POST(UPLOAD_IMAGE)
    @Multipart
    fun uploadImage(
        @Part body: MultipartBody.Part?
    ): Call<ApiResponse<String>>

    @GET(GET_ADDRESS)
    fun getAddresses(): Call<ApiResponse<List<AddressModel>>>

    @POST(ADD_TO_CART)
    fun addToCart(
        @Body body: CartModel
    ): Call<ApiResponse<CartResponseModel>>

    @POST(PLACE_ORDER)
    fun placeOrder(
        @Body body: HashMap<String, String>
    ): Call<ApiResponse<CartResponseModel>>

    @DELETE(REMOVE_SERVICE_FROM_CART)
    fun removeServiceFromCart(
        @Query("cart_id") id: Int?
    ): Call<ApiResponse<List<CartResponseModel>>>

    @GET(MY_CART)
    fun cartListApi(
    ): Call<ApiResponse<CartResponse>>

    @GET(MY_ORDERS)
    fun orderListApi(
        @Query("order_type") type: String
    ): Call<ApiResponse<CartResponse>>

    @GET(ORDER_DETAIL)
    fun orderDetailApi(
        @Query("order_id") order_id: Int?
    ): Call<ApiResponse<CartResponseModel>>

    @GET(NOTIFICATION_LIST)
    fun getNotificationListApi(
    ): Call<ApiResponse<NotificationResponse>>


    @GET(DATETIME_BY_ORDER)
    fun getOrderDateTimeApi(
        @Query("order_id") order_id: Int?
    ): Call<ApiResponse<CartResponseModel>>

    @POST(TIME_SLOTS_API)
    @FormUrlEncoded
    fun getTimeSlotsApi(
        @Field("date") date: String?,
        @Field("order_id") order_id: Int?
    ): Call<ApiResponse<List<TimeSlotModel>>>

    @POST(MAKE_PAYMENT_OF_ORDER)
    @FormUrlEncoded
    fun makePaymentForOrder(
        @FieldMap data: HashMap<String, String>
    ): Call<ApiResponse<CartResponseModel>>

    @POST(ADD_RATING_REVIEW)
    @FormUrlEncoded
    fun addRatingReview(
        @FieldMap data: HashMap<String, String>
    ): Call<ApiResponse<RatingReviewModel>>


    @POST(UPDATE_ORDER_STATUS)
    @FormUrlEncoded
    fun updateOrderStatusApi(
        @FieldMap map: HashMap<String, String>
    ): Call<ApiResponse<CartResponseModel>>


    @POST(VALIDATE_COUPON_CODE)
    @FormUrlEncoded
    fun validateCouponCodeApi(
        @FieldMap map: HashMap<String, String>
    ): Call<ApiResponse<CartResponseModel>>

    @POST(ADD_CARD)
    @FormUrlEncoded
    fun addCard(
        @Header("Authorization") auth : String,
        @FieldMap map: HashMap<String, String>
    ): Call<ApiResponse<addCardData>>

    @POST("card/updateCard/{id}")
    @FormUrlEncoded
    fun updateCard(
        @Path("id") id: String?,
        @FieldMap map: HashMap<String, Any>
    ): Call<ApiResponse<Any>>


    @DELETE(DELETE_CARD_ITEM)
    fun deleteCard( @Path("id") id: String?,  @Header("Authorization") auth : String): Call<ApiResponse<Any>>

    @POST(PAYMENT_STATUS)
    @FormUrlEncoded
    fun getPaymentStatus(
        @FieldMap map: HashMap<String, String>
    ): Call<ApiResponse<Any>>


    @POST(PAYMENT_PROCCESS)
    @FormUrlEncoded
    fun processPayment(
        @FieldMap map: HashMap<String, String>
    ): Call<ApiResponse<PaymentResponse>>



}