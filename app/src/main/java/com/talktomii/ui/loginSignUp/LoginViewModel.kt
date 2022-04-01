package com.talktomii.ui.loginSignUp


import androidx.lifecycle.ViewModel
import com.talktomii.data.apis.WebService
import com.talktomii.data.model.UserData
import com.talktomii.data.network.responseUtil.ApiResponse
import com.talktomii.data.network.responseUtil.ApiUtils
import com.talktomii.data.network.responseUtil.Resource
import com.talktomii.di.SingleLiveEvent
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class LoginViewModel @Inject constructor(private val webService: WebService) : ViewModel() {

    val getSendOtp by lazy { SingleLiveEvent<Resource<Any>>() }
    val createProfile by lazy { SingleLiveEvent<Resource<Any>>() }

    fun createProfile(map: HashMap<String, RequestBody>) {
        createProfile.value = Resource.loading()
        webService.createProfile(map)
            .enqueue(object : Callback<ApiResponse<Any>> {
                override fun onResponse(
                    call: Call<ApiResponse<Any>>,
                    response: Response<ApiResponse<Any>>
                ) {
                    if (response.isSuccessful) {
                        if (response.body()?.status == 200)
                            createProfile.value = Resource.success(response.body()?.detail)
                        else createProfile.value = Resource.error(
                            ApiUtils.getError(
                                response.code(),
                                response.body()?.message
                            )
                        )

                    } else {
                        createProfile.value = Resource.error(
                            ApiUtils.getError(
                                response.code(),
                                response.errorBody()?.string()
                            )
                        )
                    }
                }

                override fun onFailure(call: Call<ApiResponse<Any>>, t: Throwable) {
                    createProfile.value = Resource.error(ApiUtils.failure(t))
                }

            })
    }

// fun verifyPhone(hashMap: HashMap<String, Any>) {
// verifyOTP.value = Resource.loading()
// webService.verifyPhone(hashMap).enqueue(object : Callback<ApiResponse<UserData>> {
// override fun onResponse(
// call: Call<ApiResponse<UserData>>,
// response: Response<ApiResponse<UserData>>
// ) {
// if (response.isSuccessful) {
// if (response.body()?.status == 200)
// verifyOTP.value = Resource.success(response.body()?.detail)
// else verifyOTP.value = Resource.error(
// ApiUtils.getError(
// response.code(),
// response.body()?.message
// )
// )
// } else {
// verifyOTP.value = Resource.error(
// ApiUtils.getError(
// response.code(),
// response.errorBody()?.string()
// )
// )
// }
// }
//
// override fun onFailure(call: Call<ApiResponse<UserData>>, t: Throwable) {
// verifyOTP.value = Resource.error(ApiUtils.failure(t))
// }
//
// })
// }
}