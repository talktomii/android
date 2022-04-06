package com.talktomii.ui.loginSignUp


import androidx.lifecycle.ViewModel
import com.talktomii.data.apis.WebService
import com.talktomii.data.model.UserData
import com.talktomii.data.network.responseUtil.Resource
import com.talktomii.di.SingleLiveEvent
import javax.inject.Inject

class LoginViewModel @Inject constructor(private val webService: WebService) : ViewModel() {

    val getSendOtp by lazy { SingleLiveEvent<Resource<Any>>() }
    val verifyOTP by lazy { SingleLiveEvent<Resource<UserData>>() }
    val insta = SingleLiveEvent<Resource<UserData>>()
    val instaUser = SingleLiveEvent<Resource<UserData>>()

//    fun verifyPhone(hashMap: HashMap<String, Any>) {
//        verifyOTP.value = Resource.loading()
//        webService.verifyPhone(hashMap).enqueue(object : Callback<ApiResponse<UserData>> {
//            override fun onResponse(
//                call: Call<ApiResponse<UserData>>,
//                response: Response<ApiResponse<UserData>>
//            ) {
//                if (response.isSuccessful) {
//                    if (response.body()?.status == 200)
//                        verifyOTP.value = Resource.success(response.body()?.detail)
//                    else verifyOTP.value = Resource.error(
//                        ApiUtils.getError(
//                            response.code(),
//                            response.body()?.message
//                        )
//                    )
//                } else {
//                    verifyOTP.value = Resource.error(
//                        ApiUtils.getError(
//                            response.code(),
//                            response.errorBody()?.string()
//                        )
//                    )
//                }
//            }
//
//            override fun onFailure(call: Call<ApiResponse<UserData>>, t: Throwable) {
//                verifyOTP.value = Resource.error(ApiUtils.failure(t))
//            }
//
//        })
//    }
}