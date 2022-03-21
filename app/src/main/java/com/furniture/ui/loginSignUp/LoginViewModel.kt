package com.furniture.ui.loginSignUp


import androidx.lifecycle.ViewModel
import com.furniture.data.apis.WebService
import com.furniture.data.model.UserData
import com.furniture.data.network.responseUtil.ApiResponse
import com.furniture.data.network.responseUtil.ApiUtils
import com.furniture.data.network.responseUtil.Resource
import com.furniture.di.SingleLiveEvent
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class LoginViewModel @Inject constructor(private val webService: WebService) : ViewModel() {

    val getSendOtp by lazy { SingleLiveEvent<Resource<Any>>() }
    val verifyOTP by lazy { SingleLiveEvent<Resource<UserData>>() }
    val insta = SingleLiveEvent<Resource<UserData>>()
    val instaUser = SingleLiveEvent<Resource<UserData>>()


    fun apiInstaUser(hashMap: HashMap<String, String>) {
        insta.value = Resource.loading()
        webService.instaUserApis(hashMap).enqueue(object : Callback<UserData> {
            override fun onResponse(
                call: Call<UserData>,
                response: Response<UserData>
            ) {
                if (response.isSuccessful) {
                    insta.value = Resource.success(response.body())
                } else {
                    insta.value = Resource.error(
                        ApiUtils.getError(
                            response.code(),
                            response.errorBody()?.string()
                        )
                    )
                }
            }

            override fun onFailure(call: Call<UserData>, t: Throwable) {
                insta.value = Resource.error(ApiUtils.failure(t))
            }

        })
    }


    fun apigetInstaUser(user_id: String, fields: String, access_token: String) {
        instaUser.value = Resource.loading()
        webService.getInstaUserApis(user_id, fields, access_token)
            .enqueue(object : Callback<UserData> {
                override fun onResponse(
                    call: Call<UserData>,
                    response: Response<UserData>
                ) {
                    if (response.isSuccessful) {
                        instaUser.value = Resource.success(response.body())
                    } else {
                        instaUser.value = Resource.error(
                            ApiUtils.getError(
                                response.code(),
                                response.errorBody()?.string()
                            )
                        )
                    }
                }

                override fun onFailure(call: Call<UserData>, t: Throwable) {
                    instaUser.value = Resource.error(ApiUtils.failure(t))
                }

            })
    }

    fun sendOtpApi(hashMap: HashMap<String, String>) {
        getSendOtp.value = Resource.loading()
        webService.sendOTP(hashMap).enqueue(object : Callback<ApiResponse<Any>> {
            override fun onResponse(
                call: Call<ApiResponse<Any>>,
                response: Response<ApiResponse<Any>>
            ) {
                if (response.isSuccessful) {
                    if (response.body()?.status == 200)
                        getSendOtp.value = Resource.success(response.body()?.detail)
                    else getSendOtp.value = Resource.error(
                        ApiUtils.getError(
                            response.code(),
                            response.body()?.message
                        )
                    )
                } else {
                    getSendOtp.value = Resource.error(
                        ApiUtils.getError(
                            response.code(),
                            response.errorBody()?.string()
                        )
                    )
                }
            }

            override fun onFailure(call: Call<ApiResponse<Any>>, t: Throwable) {
                getSendOtp.value = Resource.error(ApiUtils.failure(t))
            }

        })
    }

    fun addPhoneApi(hashMap: HashMap<String, String>) {
        getSendOtp.value = Resource.loading()
        webService.addPhoneApi(hashMap).enqueue(object : Callback<ApiResponse<Any>> {
            override fun onResponse(
                call: Call<ApiResponse<Any>>,
                response: Response<ApiResponse<Any>>
            ) {
                if (response.isSuccessful) {
                    if (response.body()?.status == 200)
                        getSendOtp.value = Resource.success(response.body()?.detail)
                    else getSendOtp.value = Resource.error(
                        ApiUtils.getError(
                            response.code(),
                            response.body()?.message
                        )
                    )
                } else {
                    getSendOtp.value = Resource.error(
                        ApiUtils.getError(
                            response.code(),
                            response.errorBody()?.string()
                        )
                    )
                }
            }

            override fun onFailure(call: Call<ApiResponse<Any>>, t: Throwable) {
                getSendOtp.value = Resource.error(ApiUtils.failure(t))
            }

        })
    }

    fun socialLogin(hashMap: HashMap<String, String>) {
        verifyOTP.value = Resource.loading()
        webService.socialLogin(hashMap).enqueue(object : Callback<ApiResponse<UserData>> {
            override fun onResponse(
                call: Call<ApiResponse<UserData>>,
                response: Response<ApiResponse<UserData>>
            ) {
                if (response.isSuccessful) {
                    if (response.body()?.status == 200)
                        verifyOTP.value = Resource.success(response.body()?.detail)
                    else verifyOTP.value = Resource.error(
                        ApiUtils.getError(
                            response.code(),
                            response.body()?.message
                        )
                    )
                } else {
                    verifyOTP.value = Resource.error(
                        ApiUtils.getError(
                            response.code(),
                            response.errorBody()?.string()
                        )
                    )
                }
            }

            override fun onFailure(call: Call<ApiResponse<UserData>>, t: Throwable) {
                verifyOTP.value = Resource.error(ApiUtils.failure(t))
            }

        })
    }

    fun verifyOTP(hashMap: HashMap<String, Any>) {
        verifyOTP.value = Resource.loading()
        webService.verifyOTP(hashMap).enqueue(object : Callback<ApiResponse<UserData>> {
            override fun onResponse(
                call: Call<ApiResponse<UserData>>,
                response: Response<ApiResponse<UserData>>
            ) {
                if (response.isSuccessful) {
                    if (response.body()?.status == 200)
                        verifyOTP.value = Resource.success(response.body()?.detail)
                    else verifyOTP.value = Resource.error(
                        ApiUtils.getError(
                            response.code(),
                            response.body()?.message
                        )
                    )
                } else {
                    verifyOTP.value = Resource.error(
                        ApiUtils.getError(
                            response.code(),
                            response.errorBody()?.string()
                        )
                    )
                }
            }

            override fun onFailure(call: Call<ApiResponse<UserData>>, t: Throwable) {
                verifyOTP.value = Resource.error(ApiUtils.failure(t))
            }

        })
    }

    fun verifyPhone(hashMap: HashMap<String, Any>) {
        verifyOTP.value = Resource.loading()
        webService.verifyPhone(hashMap).enqueue(object : Callback<ApiResponse<UserData>> {
            override fun onResponse(
                call: Call<ApiResponse<UserData>>,
                response: Response<ApiResponse<UserData>>
            ) {
                if (response.isSuccessful) {
                    if (response.body()?.status == 200)
                        verifyOTP.value = Resource.success(response.body()?.detail)
                    else verifyOTP.value = Resource.error(
                        ApiUtils.getError(
                            response.code(),
                            response.body()?.message
                        )
                    )
                } else {
                    verifyOTP.value = Resource.error(
                        ApiUtils.getError(
                            response.code(),
                            response.errorBody()?.string()
                        )
                    )
                }
            }

            override fun onFailure(call: Call<ApiResponse<UserData>>, t: Throwable) {
                verifyOTP.value = Resource.error(ApiUtils.failure(t))
            }

        })
    }

    fun addCard(hashMap: HashMap<String, Any>){

    }
}