package com.talktomii.viewmodel

import androidx.lifecycle.ViewModel
import com.talktomii.data.apis.WebService
import com.talktomii.data.network.Coroutines
import com.talktomii.interfaces.CommonInterface
import com.talktomii.interfaces.SearchInterface
import com.google.android.gms.common.api.ApiException
import com.talktomii.data.model.InterestResponse
import com.talktomii.data.model.RegisterModel
import com.talktomii.data.network.responseUtil.ApiResponse
import com.talktomii.data.network.responseUtil.ApiUtils
import com.talktomii.data.network.responseUtil.Resource
import com.talktomii.di.SingleLiveEvent
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class SearchViewModel @Inject constructor(private val webService: WebService) : ViewModel() {
    var searchInterface: SearchInterface? = null
    var commonInterface: CommonInterface? = null
    val interests by lazy { SingleLiveEvent<Resource<InterestResponse>>() }

    fun getAllInstruction(search: String) {
        commonInterface!!.onStarted()
        Coroutines.main {
            try {
                val authResponse =
                    if (search.isEmpty()) {
                        webService.getAllInterest()
                    } else {
                        webService.getSearchInterest(search)
                    }
                if (authResponse.isSuccessful) {
                    authResponse.body().let {
                        searchInterface?.onSearchAllInstruction(authResponse.body()!!.payload)
                    }
                } else {
                    commonInterface!!.onFailure(authResponse.message())
                }
            } catch (e: ApiException) {
                e.message?.let { commonInterface!!.onFailure(it) }
            } catch (ex: Exception) {
                ex.message?.let { commonInterface!!.onFailure(it) }
            }
        }
    }
    fun getAllInterests(search: String) {
        interests.value = Resource.loading()
        webService.getInterests()
            .enqueue(object : Callback<ApiResponse<InterestResponse>> {
                override fun onResponse(
                    call: Call<ApiResponse<InterestResponse>>,
                    response: Response<ApiResponse<InterestResponse>>
                ) {
                    if (response.isSuccessful) {
                        interests.value = Resource.success(response.body()?.payload)

                    } else {
                        interests.value = Resource.error(
                            ApiUtils.getError(
                                response.code(),
                                response.errorBody()?.string()
                            )
                        )
                    }
                }

                override fun onFailure(call: Call<ApiResponse<InterestResponse>>, t: Throwable) {
                    interests.value = Resource.error(ApiUtils.failure(t))
                }

            })
    }


}