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
}