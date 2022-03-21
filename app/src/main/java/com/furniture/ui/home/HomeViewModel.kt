package com.furniture.ui.home

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.furniture.data.apis.WebService
import com.furniture.data.model.*
import com.furniture.data.network.responseUtil.ApiResponse
import com.furniture.data.network.responseUtil.ApiUtils
import com.furniture.data.network.responseUtil.Resource
import com.furniture.data.repos.UserRepository
import com.furniture.di.SingleLiveEvent
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class HomeViewModel @Inject constructor(private val webService: WebService) : ViewModel() {
    @Inject
    lateinit var userRepository: UserRepository

    val logout by lazy { SingleLiveEvent<Resource<Any>>() }
    val langugeUpdate by lazy { SingleLiveEvent<Resource<Any>>() }
    val deleteCard by lazy { SingleLiveEvent<Resource<Any>>() }
    val getHomeData by lazy { SingleLiveEvent<Resource<HomeModel>>() }
    val citiesData by lazy { SingleLiveEvent<Resource<HomeModel>>() }
    val getProfile by lazy { SingleLiveEvent<Resource<UserData>>() }
    val cards by lazy { SingleLiveEvent<Resource<List<CardData>>>() }
    val getProducts by lazy { SingleLiveEvent<Resource<List<ProductsModel>>>() }
    val address by lazy { SingleLiveEvent<Resource<List<AddressModel>>>() }
    val addaddress by lazy { SingleLiveEvent<Resource<AddressModel>>() }
    val imageData by lazy { SingleLiveEvent<Resource<String>>() }
    val addToCart by lazy { SingleLiveEvent<Resource<CartResponseModel>>() }
    val couponApply by lazy { SingleLiveEvent<Resource<CartResponseModel>>() }
    val removeCart by lazy { SingleLiveEvent<Resource<List<CartResponseModel>>>() }
    val myCartList by lazy { SingleLiveEvent<Resource<CartResponse>>() }
    val timeSlots by lazy { SingleLiveEvent<Resource<List<TimeSlotModel>>>() }

    val notifications by lazy { SingleLiveEvent<Resource<NotificationResponse>>() }
    val ratingReview by lazy { SingleLiveEvent<Resource<RatingReviewModel>>() }

    val updateStatus by lazy { SingleLiveEvent<Resource<CartResponseModel>>() }
    val addCard by lazy { SingleLiveEvent<Resource<Any>>() }
    val paymentProccess by lazy { SingleLiveEvent<Resource<PaymentResponse>>() }
    val paymentStatus by lazy { SingleLiveEvent<Resource<Any>>() }


    fun validateCouponCode(hashMap: HashMap<String, String>) {
        couponApply.value = Resource.loading()
        webService.validateCouponCodeApi(hashMap)
            .enqueue(object : Callback<ApiResponse<CartResponseModel>> {
                override fun onResponse(
                    call: Call<ApiResponse<CartResponseModel>>,
                    response: Response<ApiResponse<CartResponseModel>>
                ) {

                    if (response.isSuccessful) {
                        if (response.body()?.status == 200)
                            couponApply.value = Resource.success(response.body()?.detail)
                        else couponApply.value = Resource.error(
                            ApiUtils.getError(
                                response.code(),
                                response.body()?.message
                            )
                        )


                    } else {
                        couponApply.value = Resource.error(
                            ApiUtils.getError(
                                response.code(),
                                response.errorBody()?.string()
                            )
                        )
                    }
                }

                override fun onFailure(call: Call<ApiResponse<CartResponseModel>>, t: Throwable) {
                    updateStatus.value = Resource.error(ApiUtils.failure(t))
                }
            })
    }

    fun addCard(hashMap: HashMap<String, String>) {
        addCard.value = Resource.loading()
        webService.addCard(hashMap)
            .enqueue(object : Callback<ApiResponse<Any>> {
                override fun onResponse(
                    call: Call<ApiResponse<Any>>,
                    response: Response<ApiResponse<Any>>
                ) {
                    if (response.isSuccessful) {
                        if (response.body()?.status == 200) {
                            Log.d("Response ------", response.body()!!.data.toString())
                            addCard.value = Resource.success(response.body()?.detail)
                        } else addCard.value = Resource.error(
                            ApiUtils.getError(
                                response.code(),
                                response.body()?.message
                            )
                        )


                    } else {
                        addCard.value = Resource.error(
                            ApiUtils.getError(
                                response.code(),
                                response.errorBody()?.string()
                            )
                        )
                    }
                }

                override fun onFailure(call: Call<ApiResponse<Any>>, t: Throwable) {
                    addCard.value = Resource.error(ApiUtils.failure(t))
                }

            })
    }

    fun updateOrderStatusApi(hashMap: HashMap<String, String>) {
        updateStatus.value = Resource.loading()
        webService.updateOrderStatusApi(hashMap)
            .enqueue(object : Callback<ApiResponse<CartResponseModel>> {
                override fun onResponse(
                    call: Call<ApiResponse<CartResponseModel>>,
                    response: Response<ApiResponse<CartResponseModel>>
                ) {

                    if (response.isSuccessful) {
                        if (response.body()?.status == 200)
                            updateStatus.value = Resource.success(response.body()?.detail)
                        else updateStatus.value = Resource.error(
                            ApiUtils.getError(
                                response.code(),
                                response.body()?.message
                            )
                        )

                    } else {
                        updateStatus.value = Resource.error(
                            ApiUtils.getError(
                                response.code(),
                                response.errorBody()?.string()
                            )
                        )
                    }
                }

                override fun onFailure(call: Call<ApiResponse<CartResponseModel>>, t: Throwable) {
                    updateStatus.value = Resource.error(ApiUtils.failure(t))
                }

            })
    }

    fun addRatingReview(data: HashMap<String, String>) {
        ratingReview.value = Resource.loading()
        webService.addRatingReview(data)
            .enqueue(object : Callback<ApiResponse<RatingReviewModel>> {
                override fun onResponse(
                    call: Call<ApiResponse<RatingReviewModel>>,
                    response: Response<ApiResponse<RatingReviewModel>>
                ) {

                    if (response.isSuccessful) {
                        if (response.body()?.status == 200)
                            ratingReview.value = Resource.success(response.body()?.detail)
                        else ratingReview.value = Resource.error(
                            ApiUtils.getError(
                                response.code(),
                                response.body()?.message
                            )
                        )

                    } else {
                        ratingReview.value = Resource.error(
                            ApiUtils.getError(
                                response.code(),
                                response.errorBody()?.string()
                            )
                        )
                    }
                }

                override fun onFailure(call: Call<ApiResponse<RatingReviewModel>>, t: Throwable) {
                    ratingReview.value = Resource.error(ApiUtils.failure(t))
                }

            })
    }

    fun getTimeSlotsApi(date: String, order_id: Int?) {
        timeSlots.value = Resource.loading()
        webService.getTimeSlotsApi(date, order_id)
            .enqueue(object : Callback<ApiResponse<List<TimeSlotModel>>> {
                override fun onResponse(
                    call: Call<ApiResponse<List<TimeSlotModel>>>,
                    response: Response<ApiResponse<List<TimeSlotModel>>>
                ) {

                    if (response.isSuccessful) {
                        if (response.body()?.status == 200)
                            timeSlots.value = Resource.success(response.body()?.detail)
                        else timeSlots.value = Resource.error(
                            ApiUtils.getError(
                                response.code(),
                                response.body()?.message
                            )
                        )

                    } else {
                        timeSlots.value = Resource.error(
                            ApiUtils.getError(
                                response.code(),
                                response.errorBody()?.string()
                            )
                        )
                    }
                }

                override fun onFailure(call: Call<ApiResponse<List<TimeSlotModel>>>, t: Throwable) {
                    timeSlots.value = Resource.error(ApiUtils.failure(t))
                }

            })
    }

    fun getNotificationListApi() {
        notifications.value = Resource.loading()
        webService.getNotificationListApi()
            .enqueue(object : Callback<ApiResponse<NotificationResponse>> {
                override fun onResponse(
                    call: Call<ApiResponse<NotificationResponse>>,
                    response: Response<ApiResponse<NotificationResponse>>
                ) {
                    if (response.isSuccessful) {
                        if (response.body()?.status == 200)
                            notifications.value = Resource.success(response.body()?.detail)
                        else notifications.value = Resource.error(
                            ApiUtils.getError(
                                response.code(),
                                response.body()?.message
                            )
                        )
                    } else {
                        notifications.value = Resource.error(
                            ApiUtils.getError(
                                response.code(),
                                response.errorBody()?.string()
                            )
                        )
                    }
                }

                override fun onFailure(
                    call: Call<ApiResponse<NotificationResponse>>,
                    t: Throwable
                ) {
                    notifications.value = Resource.error(ApiUtils.failure(t))
                }

            })
    }

    fun orderDetailApi(id: Int?) {
        addToCart.value = Resource.loading()
        webService.orderDetailApi(id)
            .enqueue(object : Callback<ApiResponse<CartResponseModel>> {
                override fun onResponse(
                    call: Call<ApiResponse<CartResponseModel>>,
                    response: Response<ApiResponse<CartResponseModel>>
                ) {
                    if (response.isSuccessful) {
                        if (response.body()?.status == 200)
                            addToCart.value = Resource.success(response.body()?.detail)
                        else addToCart.value = Resource.error(
                            ApiUtils.getError(
                                response.code(),
                                response.body()?.message
                            )
                        )
                    } else {
                        addToCart.value = Resource.error(
                            ApiUtils.getError(
                                response.code(),
                                response.errorBody()?.string()
                            )
                        )
                    }
                }

                override fun onFailure(call: Call<ApiResponse<CartResponseModel>>, t: Throwable) {
                    addToCart.value = Resource.error(ApiUtils.failure(t))
                }

            })
    }

    fun makePaymentForOrder(data: HashMap<String, String>) {
        addToCart.value = Resource.loading()
        webService.makePaymentForOrder(data)
            .enqueue(object : Callback<ApiResponse<CartResponseModel>> {
                override fun onResponse(
                    call: Call<ApiResponse<CartResponseModel>>,
                    response: Response<ApiResponse<CartResponseModel>>
                ) {

                    if (response.isSuccessful) {
                        if (response.body()?.status == 200)
                            addToCart.value = Resource.success(response.body()?.detail)
                        else addToCart.value = Resource.error(
                            ApiUtils.getError(
                                response.code(),
                                response.body()?.message
                            )
                        )

                    } else {
                        addToCart.value = Resource.error(
                            ApiUtils.getError(
                                response.code(),
                                response.errorBody()?.string()
                            )
                        )
                    }
                }

                override fun onFailure(call: Call<ApiResponse<CartResponseModel>>, t: Throwable) {
                    addToCart.value = Resource.error(ApiUtils.failure(t))
                }

            })
    }

    fun getOrderDateTimeApi(id: Int?) {
        addToCart.value = Resource.loading()
        webService.getOrderDateTimeApi(id)
            .enqueue(object : Callback<ApiResponse<CartResponseModel>> {
                override fun onResponse(
                    call: Call<ApiResponse<CartResponseModel>>,
                    response: Response<ApiResponse<CartResponseModel>>
                ) {
                    if (response.isSuccessful) {
                        if (response.body()?.status == 200)
                            addToCart.value = Resource.success(response.body()?.detail)
                        else addToCart.value = Resource.error(
                            ApiUtils.getError(
                                response.code(),
                                response.body()?.message
                            )
                        )
                    } else {
                        addToCart.value = Resource.error(
                            ApiUtils.getError(
                                response.code(),
                                response.errorBody()?.string()
                            )
                        )
                    }
                }

                override fun onFailure(call: Call<ApiResponse<CartResponseModel>>, t: Throwable) {
                    addToCart.value = Resource.error(ApiUtils.failure(t))
                }

            })
    }

    fun orderListApi(type: String) {
        myCartList.value = Resource.loading()
        webService.orderListApi(type)
            .enqueue(object : Callback<ApiResponse<CartResponse>> {
                override fun onResponse(
                    call: Call<ApiResponse<CartResponse>>,
                    response: Response<ApiResponse<CartResponse>>
                ) {
                    if (response.isSuccessful) {
                        if (response.body()?.status == 200)
                            myCartList.value = Resource.success(response.body()?.detail)
                        else myCartList.value = Resource.error(
                            ApiUtils.getError(
                                response.code(),
                                response.body()?.message
                            )
                        )
                    } else {
                        myCartList.value = Resource.error(
                            ApiUtils.getError(
                                response.code(),
                                response.errorBody()?.string()
                            )
                        )
                    }
                }

                override fun onFailure(call: Call<ApiResponse<CartResponse>>, t: Throwable) {
                    myCartList.value = Resource.error(ApiUtils.failure(t))
                }

            })
    }

    fun cartListApi() {
        myCartList.value = Resource.loading()
        webService.cartListApi()
            .enqueue(object : Callback<ApiResponse<CartResponse>> {
                override fun onResponse(
                    call: Call<ApiResponse<CartResponse>>,
                    response: Response<ApiResponse<CartResponse>>
                ) {
                    if (response.isSuccessful) {
                        if (response.body()?.status == 200)
                            myCartList.value = Resource.success(response.body()?.detail)
                        else myCartList.value = Resource.error(
                            ApiUtils.getError(
                                response.code(),
                                response.body()?.message
                            )
                        )
                    } else {
                        myCartList.value = Resource.error(
                            ApiUtils.getError(
                                response.code(),
                                response.errorBody()?.string()
                            )
                        )
                    }
                }

                override fun onFailure(call: Call<ApiResponse<CartResponse>>, t: Throwable) {
                    myCartList.value = Resource.error(ApiUtils.failure(t))
                }

            })
    }

    fun removeServiceFromCart(cart_id: Int?) {
        removeCart.value = Resource.loading()
        webService.removeServiceFromCart(cart_id)
            .enqueue(object : Callback<ApiResponse<List<CartResponseModel>>> {
                override fun onResponse(
                    call: Call<ApiResponse<List<CartResponseModel>>>,
                    response: Response<ApiResponse<List<CartResponseModel>>>
                ) {
                    if (response.isSuccessful) {
                        if (response.body()?.status == 200)
                            removeCart.value = Resource.success(message = response.body()?.message)
                        else removeCart.value = Resource.error(
                            ApiUtils.getError(
                                response.code(),
                                response.body()?.message
                            )
                        )
                    } else {
                        removeCart.value = Resource.error(
                            ApiUtils.getError(
                                response.code(),
                                response.errorBody()?.string()
                            )
                        )
                    }
                }

                override fun onFailure(
                    call: Call<ApiResponse<List<CartResponseModel>>>,
                    t: Throwable
                ) {
                    removeCart.value = Resource.error(ApiUtils.failure(t))
                }
            })
    }

    fun addToCart(body: CartModel) {
        addToCart.value = Resource.loading()
        webService.addToCart(body)
            .enqueue(object : Callback<ApiResponse<CartResponseModel>> {
                override fun onResponse(
                    call: Call<ApiResponse<CartResponseModel>>,
                    response: Response<ApiResponse<CartResponseModel>>
                ) {

                    if (response.isSuccessful) {
                        if (response.body()?.status == 200)
                            addToCart.value = Resource.success(response.body()?.detail)
                        else addToCart.value = Resource.error(
                            ApiUtils.getError(
                                response.code(),
                                response.body()?.message
                            )
                        )

                    } else {
                        addToCart.value = Resource.error(
                            ApiUtils.getError(
                                response.code(),
                                response.errorBody()?.string()
                            )
                        )
                    }
                }

                override fun onFailure(call: Call<ApiResponse<CartResponseModel>>, t: Throwable) {
                    addToCart.value = Resource.error(ApiUtils.failure(t))
                }

            })
    }

    fun placeOrder(body: HashMap<String, String>) {
        addToCart.value = Resource.loading()
        webService.placeOrder(body)
            .enqueue(object : Callback<ApiResponse<CartResponseModel>> {
                override fun onResponse(
                    call: Call<ApiResponse<CartResponseModel>>,
                    response: Response<ApiResponse<CartResponseModel>>
                ) {
                    if (response.isSuccessful) {
                        if (response.body()?.status == 200)
                            addToCart.value = Resource.success(response.body()?.detail)
                        else addToCart.value = Resource.error(
                            ApiUtils.getError(
                                response.code(),
                                response.body()?.message
                            )
                        )
                    } else {
                        addToCart.value = Resource.error(
                            ApiUtils.getError(
                                response.code(),
                                response.errorBody()?.string()
                            )
                        )
                    }
                }

                override fun onFailure(call: Call<ApiResponse<CartResponseModel>>, t: Throwable) {
                    addToCart.value = Resource.error(ApiUtils.failure(t))
                }

            })
    }

    fun uploadImage(body: MultipartBody.Part) {
        imageData.value = Resource.loading()
        webService.uploadImage(body)
            .enqueue(object : Callback<ApiResponse<String>> {
                override fun onResponse(
                    call: Call<ApiResponse<String>>,
                    response: Response<ApiResponse<String>>
                ) {
                    if (response.isSuccessful) {
                        if (response.body()?.status == 200)
                            imageData.value = Resource.success(response.body()?.detail)
                        else imageData.value = Resource.error(
                            ApiUtils.getError(
                                response.code(),
                                response.body()?.message
                            )
                        )
                    } else {
                        imageData.value = Resource.error(
                            ApiUtils.getError(
                                response.code(),
                                response.errorBody()?.string()
                            )
                        )
                    }
                }

                override fun onFailure(call: Call<ApiResponse<String>>, t: Throwable) {
                    imageData.value = Resource.error(ApiUtils.failure(t))
                }

            })
    }

    fun addAddress(hashMap: HashMap<String, String>) {
        addaddress.value = Resource.loading()
        webService.addAddress(hashMap)
            .enqueue(object : Callback<ApiResponse<AddressModel>> {
                override fun onResponse(
                    call: Call<ApiResponse<AddressModel>>,
                    response: Response<ApiResponse<AddressModel>>
                ) {
                    if (response.isSuccessful) {
                        if (response.body()?.status == 200)
                            addaddress.value = Resource.success(response.body()?.detail)
                        else addaddress.value = Resource.error(
                            ApiUtils.getError(
                                response.code(),
                                response.body()?.message
                            )
                        )
                    } else {
                        addaddress.value = Resource.error(
                            ApiUtils.getError(
                                response.code(),
                                response.errorBody()?.string()
                            )
                        )
                    }
                }

                override fun onFailure(call: Call<ApiResponse<AddressModel>>, t: Throwable) {
                    addaddress.value = Resource.error(ApiUtils.failure(t))
                }

            })
    }

    fun getAddresses() {
        address.value = Resource.loading()
        webService.getAddresses()
            .enqueue(object : Callback<ApiResponse<List<AddressModel>>> {
                override fun onResponse(
                    call: Call<ApiResponse<List<AddressModel>>>,
                    response: Response<ApiResponse<List<AddressModel>>>
                ) {
                    if (response.isSuccessful) {
                        if (response.body()?.status == 200)
                            address.value = Resource.success(response.body()?.detail)
                        else address.value = Resource.error(
                            ApiUtils.getError(
                                response.code(),
                                response.body()?.message
                            )
                        )
                    } else {
                        address.value = Resource.error(
                            ApiUtils.getError(
                                response.code(),
                                response.errorBody()?.string()
                            )
                        )
                    }
                }

                override fun onFailure(call: Call<ApiResponse<List<AddressModel>>>, t: Throwable) {
                    address.value = Resource.error(ApiUtils.failure(t))
                }

            })
    }

    fun logoutApi() {
        logout.value = Resource.loading()
        webService.logout()
            .enqueue(object : Callback<ApiResponse<Any>> {
                override fun onResponse(
                    call: Call<ApiResponse<Any>>,
                    response: Response<ApiResponse<Any>>

                ) {
                    if (response.isSuccessful) {
                        if (response.body()?.status == 200)
                            logout.value = Resource.success(response.body()?.detail)
                        else logout.value = Resource.error(
                            ApiUtils.getError(
                                response.code(),
                                response.body()?.message
                            )
                        )

                    } else {
                        logout.value = Resource.error(
                            ApiUtils.getError(
                                response.code(),
                                response.errorBody()?.string()
                            )
                        )
                    }
                }

                override fun onFailure(call: Call<ApiResponse<Any>>, t: Throwable) {
                    logout.value = Resource.error(ApiUtils.failure(t))
                }

            })
    }

    fun getProductsApi(service_id: String, offer_id: String) {
        getProducts.value = Resource.loading()
        webService.getProductsData(service_id, offer_id)
            .enqueue(object : Callback<ApiResponse<List<ProductsModel>>> {
                override fun onResponse(
                    call: Call<ApiResponse<List<ProductsModel>>>,
                    response: Response<ApiResponse<List<ProductsModel>>>
                ) {
                    if (response.isSuccessful) {
                        if (response.body()?.status == 200)
                            getProducts.value = Resource.success(response.body()?.detail)
                        else getProducts.value = Resource.error(
                            ApiUtils.getError(
                                response.code(),
                                response.body()?.message
                            )
                        )
                    } else {
                        getProducts.value = Resource.error(
                            ApiUtils.getError(
                                response.code(),
                                response.errorBody()?.string()
                            )
                        )
                    }
                }

                override fun onFailure(call: Call<ApiResponse<List<ProductsModel>>>, t: Throwable) {
                    getProducts.value = Resource.error(ApiUtils.failure(t))
                }

            })
    }

    fun getSubProductsApi(product_id: String, service_id: String, offer_id: String) {
        getProducts.value = Resource.loading()
        webService.getSubProductsData(

            product_id,
            service_id,
            offer_id
        )
            .enqueue(object : Callback<ApiResponse<List<ProductsModel>>> {
                override fun onResponse(
                    call: Call<ApiResponse<List<ProductsModel>>>,
                    response: Response<ApiResponse<List<ProductsModel>>>
                ) {
                    if (response.isSuccessful) {
                        if (response.body()?.status == 200)
                            getProducts.value = Resource.success(response.body()?.detail)
                        else getProducts.value = Resource.error(
                            ApiUtils.getError(
                                response.code(),
                                response.body()?.message
                            )
                        )
                    } else {
                        getProducts.value = Resource.error(
                            ApiUtils.getError(
                                response.code(),
                                response.errorBody()?.string()
                            )
                        )
                    }
                }


                override fun onFailure(call: Call<ApiResponse<List<ProductsModel>>>, t: Throwable) {
                    getProducts.value = Resource.error(ApiUtils.failure(t))
                }

            })
    }

    fun getProductsServicesApi(
        product_id: String,
        service_id: String,
        product_type_id: String,
        offer_id: String
    ) {

        getProducts.value = Resource.loading()
        webService.getProductsServicesApi(product_id, service_id, product_type_id, offer_id)
            .enqueue(object : Callback<ApiResponse<List<ProductsModel>>> {
                override fun onResponse(
                    call: Call<ApiResponse<List<ProductsModel>>>,
                    response: Response<ApiResponse<List<ProductsModel>>>
                ) {
                    if (response.isSuccessful) {
                        if (response.body()?.status == 200)
                            getProducts.value = Resource.success(response.body()?.detail)
                        else getProducts.value = Resource.error(
                            ApiUtils.getError(
                                response.code(),
                                response.body()?.message
                            )
                        )
                    } else {
                        getProducts.value = Resource.error(
                            ApiUtils.getError(
                                response.code(),
                                response.errorBody()?.string()
                            )
                        )
                    }
                }

                override fun onFailure(call: Call<ApiResponse<List<ProductsModel>>>, t: Throwable) {
                    getProducts.value = Resource.error(ApiUtils.failure(t))
                }

            })
    }

    fun getHomeApi() {
        getHomeData.value = Resource.loading()
        //
        webService.getHomeData()
            .enqueue(object : Callback<ApiResponse<HomeModel>> {
                override fun onResponse(
                    call: Call<ApiResponse<HomeModel>>,
                    response: Response<ApiResponse<HomeModel>>
                ) {
                    if (response.isSuccessful) {
                        if (response.body()?.status == 200)
                            getHomeData.value = Resource.success(response.body()?.detail)
                        else getHomeData.value = Resource.error(
                            ApiUtils.getError(
                                response.code(),
                                response.body()?.message
                            )
                        )
                    } else {
                        getHomeData.value = Resource.error(
                            ApiUtils.getError(
                                response.code(),
                                response.errorBody()?.string()
                            )
                        )
                    }
                }

                override fun onFailure(call: Call<ApiResponse<HomeModel>>, t: Throwable) {
                    getHomeData.value = Resource.error(ApiUtils.failure(t))
                }

            })
    }

    fun getAllZonesApi() {
        getHomeData.value = Resource.loading()
        webService.getAllZonesApi()
            .enqueue(object : Callback<ApiResponse<HomeModel>> {
                override fun onResponse(
                    call: Call<ApiResponse<HomeModel>>,
                    response: Response<ApiResponse<HomeModel>>
                ) {
                    if (response.isSuccessful) {
                        if (response.body()?.status == 200)
                            getHomeData.value = Resource.success(response.body()?.detail)
                        else getHomeData.value = Resource.error(
                            ApiUtils.getError(
                                response.code(),
                                response.body()?.message
                            )
                        )
                    } else {
                        getHomeData.value = Resource.error(
                            ApiUtils.getError(
                                response.code(),
                                response.errorBody()?.string()
                            )
                        )
                    }
                }

                override fun onFailure(call: Call<ApiResponse<HomeModel>>, t: Throwable) {
                    getHomeData.value = Resource.error(ApiUtils.failure(t))
                }

            })
    }


    fun getAllCitiesApi() {
        citiesData.value = Resource.loading()
        webService.getAllCitiesApi()
            .enqueue(object : Callback<ApiResponse<HomeModel>> {
                override fun onResponse(
                    call: Call<ApiResponse<HomeModel>>,
                    response: Response<ApiResponse<HomeModel>>
                ) {
                    if (response.isSuccessful) {
                        if (response.body()?.status == 200)
                            citiesData.value = Resource.success(response.body()?.detail)
                        else citiesData.value = Resource.error(
                            ApiUtils.getError(
                                response.code(),
                                response.body()?.message
                            )
                        )
                    } else {
                        citiesData.value = Resource.error(
                            ApiUtils.getError(
                                response.code(),
                                response.errorBody()?.string()
                            )
                        )
                    }
                }

                override fun onFailure(call: Call<ApiResponse<HomeModel>>, t: Throwable) {
                    citiesData.value = Resource.error(ApiUtils.failure(t))
                }

            })
    }

    fun deleteCard(id: Int?) {
        deleteCard.value = Resource.loading()
        webService.deleteCard(id).enqueue(object : Callback<ApiResponse<Any>> {
            override fun onResponse(
                call: Call<ApiResponse<Any>>,
                response: Response<ApiResponse<Any>>
            ) {
                if (response.isSuccessful) {
                    if (response.body()?.status == 200)
                        deleteCard.value = Resource.success(response.body()?.detail)
                    else deleteCard.value = Resource.error(
                        ApiUtils.getError(
                            response.code(),
                            response.body()?.message
                        )
                    )

                } else {
                    deleteCard.value = Resource.error(
                        ApiUtils.getError(
                            response.code(),
                            response.errorBody()?.string()
                        )
                    )
                }
            }

            override fun onFailure(call: Call<ApiResponse<Any>>, t: Throwable) {
                deleteCard.value = Resource.error(ApiUtils.failure(t))
            }

        })
    }


    fun getOfferedServices(offer_id: String) {
        getHomeData.value = Resource.loading()
        webService.getOfferedServices(offer_id)
            .enqueue(object : Callback<ApiResponse<HomeModel>> {
                override fun onResponse(
                    call: Call<ApiResponse<HomeModel>>,
                    response: Response<ApiResponse<HomeModel>>
                ) {
                    if (response.isSuccessful) {
                        if (response.body()?.status == 200)
                            getHomeData.value = Resource.success(response.body()?.detail)
                        else getHomeData.value = Resource.error(
                            ApiUtils.getError(
                                response.code(),
                                response.body()?.message
                            )
                        )

                    } else {
                        getHomeData.value = Resource.error(
                            ApiUtils.getError(
                                response.code(),
                                response.errorBody()?.string()
                            )
                        )
                    }
                }

                override fun onFailure(call: Call<ApiResponse<HomeModel>>, t: Throwable) {
                    getHomeData.value = Resource.error(ApiUtils.failure(t))
                }

            })
    }


    fun getProfileApi() {
        getProfile.value = Resource.loading()
        webService.getUser()
            .enqueue(object : Callback<ApiResponse<UserData>> {
                override fun onResponse(
                    call: Call<ApiResponse<UserData>>,
                    response: Response<ApiResponse<UserData>>
                ) {
                    if (response.isSuccessful) {
                        if (response.body()?.status == 200)
                            getProfile.value = Resource.success(response.body()?.detail)
                        else getProfile.value = Resource.error(
                            ApiUtils.getError(
                                response.code(),
                                response.body()?.message
                            )
                        )
                    } else {
                        getProfile.value = Resource.error(
                            ApiUtils.getError(
                                response.code(),
                                response.errorBody()?.string()
                            )
                        )
                    }
                }

                override fun onFailure(call: Call<ApiResponse<UserData>>, t: Throwable) {

                    getProfile.value = Resource.error(ApiUtils.failure(t))
                }

            })
    }

    fun editProfileApi(map: HashMap<String, RequestBody>) {
        getProfile.value = Resource.loading()
        webService.editProfile(map)
            .enqueue(object : Callback<ApiResponse<UserData>> {
                override fun onResponse(
                    call: Call<ApiResponse<UserData>>,
                    response: Response<ApiResponse<UserData>>
                ) {
                    if (response.isSuccessful) {
                        if (response.body()?.status == 200)
                            getProfile.value = Resource.success(response.body()?.detail)
                        else getProfile.value = Resource.error(
                            ApiUtils.getError(
                                response.code(),
                                response.body()?.message
                            )
                        )

                    } else {
                        getProfile.value = Resource.error(
                            ApiUtils.getError(
                                response.code(),
                                response.errorBody()?.string()
                            )
                        )
                    }
                }

                override fun onFailure(call: Call<ApiResponse<UserData>>, t: Throwable) {
                    getProfile.value = Resource.error(ApiUtils.failure(t))
                }

            })
    }

//    fun getCards() {
//        cards.value = Resource.loading()
//        webService.getCards()
//            .enqueue(object : Callback<ApiResponse<List<CardData>>> {
//                override fun onResponse(
//                    call: Call<ApiResponse<List<CardData>>>,
//                    response: Response<ApiResponse<List<CardData>>>
//                ) {
//
//                    if (response.isSuccessful) {
//                        if (response.body()?.status == 200)
//                            cards.value = Resource.success(response.body()?.detail)
//                        else cards.value = Resource.error(
//                            ApiUtils.getError(
//                                response.code(),
//                                response.body()?.message
//                            )
//                        )
//
//                    } else {
//                        cards.value = Resource.error(
//                            ApiUtils.getError(
//                                response.code(),
//                                response.errorBody()?.string()
//                            )
//                        )
//                    }
//                }
//
//                override fun onFailure(call: Call<ApiResponse<List<CardData>>>, t: Throwable) {
//                    cards.value = Resource.error(ApiUtils.failure(t))
//                }
//
//            })
//    }


    fun processPayment(hashMap: java.util.HashMap<String, String>) {
        paymentProccess.value = Resource.loading()
        webService.processPayment(hashMap)
            .enqueue(object : Callback<ApiResponse<PaymentResponse>> {
                override fun onResponse(
                    call: Call<ApiResponse<PaymentResponse>>,
                    response: Response<ApiResponse<PaymentResponse>>

                ) {
                    if (response.isSuccessful) {
                        if (response.body()?.status == 200)
                            paymentProccess.value = Resource.success(response.body()?.detail)
                        else paymentProccess.value = Resource.error(
                            ApiUtils.getError(
                                response.code(),
                                response.body()?.message
                            )
                        )

                    } else {
                        paymentProccess.value = Resource.error(
                            ApiUtils.getError(
                                response.code(),
                                response.errorBody()?.string()
                            )
                        )
                    }
                }

                override fun onFailure(call: Call<ApiResponse<PaymentResponse>>, t: Throwable) {
                    paymentProccess.value = Resource.error(ApiUtils.failure(t))
                }

            })
    }


    fun getPaymentStatus(hashMap: HashMap<String, String>) {
        paymentStatus.value = Resource.loading()
        webService.getPaymentStatus(hashMap)
            .enqueue(object : Callback<ApiResponse<Any>> {
                override fun onResponse(
                    call: Call<ApiResponse<Any>>,
                    response: Response<ApiResponse<Any>>
                ) {

                    if (response.isSuccessful) {
                        if (response.body()?.status == 200)
                            paymentStatus.value = Resource.success(response.body()?.detail)
                        else paymentStatus.value = Resource.error(
                            ApiUtils.getError(
                                response.code(),
                                response.body()?.message
                            )
                        )

                    } else {
                        paymentStatus.value = Resource.error(
                            ApiUtils.getError(
                                response.code(),
                                response.errorBody()?.string()
                            )
                        )
                    }
                }


                override fun onFailure(call: Call<ApiResponse<Any>>, t: Throwable) {
                    paymentStatus.value = Resource.error(ApiUtils.failure(t))
                }

            })
    }

}