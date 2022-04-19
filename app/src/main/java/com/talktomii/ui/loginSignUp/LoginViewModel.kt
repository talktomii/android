package com.talktomii.ui.loginSignUp


import androidx.lifecycle.ViewModel
import com.talktomii.data.apis.WebService
import com.talktomii.data.model.RegisterModel
import com.talktomii.data.model.Role
import com.talktomii.data.model.RolesModel
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
    val login by lazy { SingleLiveEvent<Resource<RegisterModel>>() }
    val role by lazy { SingleLiveEvent<Resource<RolesModel>>() }
    val verify by lazy { SingleLiveEvent<Resource<Any>>() }
    val createProfile by lazy { SingleLiveEvent<Resource<RegisterModel>>() }

    fun createProfile(map: HashMap<String, RequestBody>) {
        createProfile.value = Resource.loading()
        webService.createProfile(map)
            .enqueue(object : Callback<ApiResponse<RegisterModel>> {
                override fun onResponse(
                    call: Call<ApiResponse<RegisterModel>>,
                    response: Response<ApiResponse<RegisterModel>>
                ) {
                    if (response.isSuccessful) {
                        createProfile.value = Resource.success(response.body()?.payload)

                    } else {
                        createProfile.value = Resource.error(
                            ApiUtils.getError(
                                response.code(),
                                response.errorBody()?.string()
                            )
                        )
                    }
                }

                override fun onFailure(call: Call<ApiResponse<RegisterModel>>, t: Throwable) {
                    createProfile.value = Resource.error(ApiUtils.failure(t))
                }

            })
    }

    fun loginApi(map: HashMap<String, String>) {
        login.value = Resource.loading()
        webService.loginApi(map)
            .enqueue(object : Callback<ApiResponse<RegisterModel>> {
                override fun onResponse(
                    call: Call<ApiResponse<RegisterModel>>,
                    response: Response<ApiResponse<RegisterModel>>
                ) {
                    if (response.isSuccessful) {

                        login.value = Resource.success(response.body()?.payload)

                    } else {
                        login.value = Resource.error(
                            ApiUtils.getError(
                                response.code(),
                                response.errorBody()?.string()
                            )
                        )
                    }
                }

                override fun onFailure(call: Call<ApiResponse<RegisterModel>>, t: Throwable) {
                    login.value = Resource.error(ApiUtils.failure(t))
                }

            })
    }


    fun getRoles() {
        role.value = Resource.loading()
        webService.getRole()
            .enqueue(object : Callback<ApiResponse<RolesModel>> {
                override fun onResponse(
                    call: Call<ApiResponse<RolesModel>>,
                    response: Response<ApiResponse<RolesModel>>
                ) {
                    if (response.isSuccessful) {
                        role.value = Resource.success(response.body()?.payload)

                    } else {
                        role.value = Resource.error(
                            ApiUtils.getError(
                                response.code(),
                                response.errorBody()?.string()
                            )
                        )
                    }
                }

                override fun onFailure(call: Call<ApiResponse<RolesModel>>, t: Throwable) {
                    role.value = Resource.error(ApiUtils.failure(t))
                }

            })

    }
//
//    fun verifyEmail(map: HashMap<String, Any>) {
//        verify.value = Resource.loading()
//        webService.verifyEmail(map)
//            .enqueue(object : Callback<ApiResponse<Any>> {
//                override fun onResponse(
//                    call: Call<ApiResponse<Any>>,
//                    response: Response<ApiResponse<Any>>
//                ) {
//                    if (response.isSuccessful) {
//
//                        verify.value = Resource.success(response.body()?.payload)
//
//                    } else {
//                        verify.value = Resource.error(
//                            ApiUtils.getError(
//                                response.code(),
//                                response.errorBody()?.string()
//                            )
//                        )
//                    }
//                }
//
//                override fun onFailure(call: Call<ApiResponse<Any>>, t: Throwable) {
//                    verify.value = Resource.error(ApiUtils.failure(t))
//                }
//
//            })
//    }

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