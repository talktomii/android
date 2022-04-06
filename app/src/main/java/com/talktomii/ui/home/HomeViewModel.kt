package com.talktomii.ui.home

import androidx.lifecycle.ViewModel
import com.talktomii.data.apis.WebService
import com.talktomii.data.model.*
import com.talktomii.data.network.responseUtil.Resource
import com.talktomii.data.repos.UserRepository
import com.talktomii.di.SingleLiveEvent
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



//    fun validateCouponCode(hashMap: HashMap<String, String>) {
//        couponApply.value = Resource.loading()
//        webService.validateCouponCodeApi(hashMap)
//            .enqueue(object : Callback<ApiResponse<CartResponseModel>> {
//                override fun onResponse(
//                    call: Call<ApiResponse<CartResponseModel>>,
//                    response: Response<ApiResponse<CartResponseModel>>
//                ) {
//
//                    if (response.isSuccessful) {
//                        if (response.body()?.status == 200)
//                            couponApply.value = Resource.success(response.body()?.detail)
//                        else couponApply.value = Resource.error(
//                            ApiUtils.getError(
//                                response.code(),
//                                response.body()?.message
//                            )
//                        )
//
//
//                    } else {
//                        couponApply.value = Resource.error(
//                            ApiUtils.getError(
//                                response.code(),
//                                response.errorBody()?.string()
//                            )
//                        )
//                    }
//                }
//
//                override fun onFailure(call: Call<ApiResponse<CartResponseModel>>, t: Throwable) {
//                    updateStatus.value = Resource.error(ApiUtils.failure(t))
//                }
//            })
//    }


//    fun getNotificationListApi() {
//        notifications.value = Resource.loading()
//        webService.getNotificationListApi()
//            .enqueue(object : Callback<ApiResponse<NotificationResponse>> {
//                override fun onResponse(
//                    call: Call<ApiResponse<NotificationResponse>>,
//                    response: Response<ApiResponse<NotificationResponse>>
//                ) {
//                    if (response.isSuccessful) {
//                        if (response.body()?.status == 200)
//                            notifications.value = Resource.success(response.body()?.detail)
//                        else notifications.value = Resource.error(
//                            ApiUtils.getError(
//                                response.code(),
//                                response.body()?.message
//                            )
//                        )
//                    } else {
//                        notifications.value = Resource.error(
//                            ApiUtils.getError(
//                                response.code(),
//                                response.errorBody()?.string()
//                            )
//                        )
//                    }
//                }
//
//                override fun onFailure(
//                    call: Call<ApiResponse<NotificationResponse>>,
//                    t: Throwable
//                ) {
//                    notifications.value = Resource.error(ApiUtils.failure(t))
//                }
//
//            })
//    }







}