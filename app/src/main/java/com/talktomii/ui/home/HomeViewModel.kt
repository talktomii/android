package com.talktomii.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import com.talktomii.data.apis.WebService
import com.talktomii.data.model.*
import com.talktomii.data.network.responseUtil.ApiResponse
import com.talktomii.data.network.responseUtil.ApiUtils
import com.talktomii.data.network.responseUtil.Resource
import com.talktomii.data.repos.UserRepository
import com.talktomii.di.SingleLiveEvent
import com.talktomii.ui.loginSignUp.MainActivity
import com.talktomii.ui.mycards.data.addCardData
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
    val agoraToken by lazy { SingleLiveEvent<Resource<RegisterModel>>() }


    fun addCard(hashMap: HashMap<String, String>) {
        addCard.value = Resource.loading()
        webService.addCard(hashMap)
            .enqueue(object : Callback<ApiResponse<addCardData>> {
                override fun onResponse(
                    call: Call<ApiResponse<addCardData>>,
                    response: Response<ApiResponse<addCardData>>
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

                override fun onFailure(call: Call<ApiResponse<addCardData>>, t: Throwable) {
                    addCard.value = Resource.error(ApiUtils.failure(t))
                }

            })
    }

    fun initCall(channelName: String) {
        agoraToken.value = Resource.loading()
        webService.initCall(channelName)
            .enqueue(object : Callback<ApiResponse<RegisterModel>> {
                override fun onResponse(
                    call: Call<ApiResponse<RegisterModel>>,
                    response: Response<ApiResponse<RegisterModel>>
                ) {
                    if (response.isSuccessful) {
                        Log.d("Response ------", response.body()!!.payload.toString())
                        agoraToken.value = Resource.success(response.body()?.payload)
                    } else {
                        agoraToken.value = Resource.error(
                            ApiUtils.getError(
                                response.code(),
                                response.errorBody()?.string()
                            )
                        )
                    }
                }

                override fun onFailure(call: Call<ApiResponse<RegisterModel>>, t: Throwable) {
                    agoraToken.value = Resource.error(ApiUtils.failure(t))
                }

            })
    }


}