package com.talktomii.viewmodel

import androidx.lifecycle.ViewModel
import com.google.android.gms.common.api.ApiException
import com.talktomii.data.apis.WebService
import com.talktomii.data.model.InterestResponse
import com.talktomii.data.model.RegisterModel
import com.talktomii.data.network.Coroutines
import com.talktomii.data.network.responseUtil.ApiResponse
import com.talktomii.data.network.responseUtil.ApiUtils
import com.talktomii.data.network.responseUtil.Resource
import com.talktomii.di.SingleLiveEvent
import com.talktomii.interfaces.CommonInterface
import com.talktomii.interfaces.OnAdminSearchInterface
import com.talktomii.interfaces.SearchInterface
import com.talktomii.ui.tellusmore.RequestAdminModel
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class SearchViewModel @Inject constructor(private val webService: WebService) : ViewModel() {
    var searchInterface: SearchInterface? = null
    var commonInterface: CommonInterface? = null
    val interests by lazy { SingleLiveEvent<Resource<InterestResponse>>() }
    val updateData by lazy { SingleLiveEvent<Resource<Any>>() }
    val uploadMedia by lazy { SingleLiveEvent<Resource<Any>>() }
    var searchAdminsInterface: OnAdminSearchInterface? = null

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

    fun updateData(id: String, requestAdminModel: RequestAdminModel) {
        updateData.value = Resource.loading()
        webService.updateData(id, requestAdminModel)
            .enqueue(object : Callback<ApiResponse<Any>> {
                override fun onResponse(
                    call: Call<ApiResponse<Any>>,
                    response: Response<ApiResponse<Any>>
                ) {
                    if (response.isSuccessful) {
                        updateData.value = Resource.success(response.body()?.payload)

                    } else {
                        updateData.value = Resource.error(
                            ApiUtils.getError(
                                response.code(),
                                response.errorBody()?.string()
                            )
                        )
                    }
                }

                override fun onFailure(call: Call<ApiResponse<Any>>, t: Throwable) {
                    updateData.value = Resource.error(ApiUtils.failure(t))
                }

            })
    }
    fun uploadMedia(map: HashMap<String, RequestBody>,uId:String) {
        uploadMedia.value = Resource.loading()
        webService.uploadMedia(map,uId)
            .enqueue(object : Callback<ApiResponse<Any>> {
                override fun onResponse(
                    call: Call<ApiResponse<Any>>,
                    response: Response<ApiResponse<Any>>
                ) {
                    if (response.isSuccessful) {
                        uploadMedia.value = Resource.success(response.body()?.payload)

                    } else {
                        uploadMedia.value = Resource.error(
                            ApiUtils.getError(
                                response.code(),
                                response.errorBody()?.string()
                            )
                        )
                    }
                }

                override fun onFailure(call: Call<ApiResponse<Any>>, t: Throwable) {
                    uploadMedia.value = Resource.error(ApiUtils.failure(t))
                }

            })
    }
    fun getAdminsBySearch(search: String) {
        commonInterface!!.onStarted()
        Coroutines.main {
            try {
                val authResponse = webService.getAdminsBySearch(search)
                if (authResponse.isSuccessful) {
                    searchAdminsInterface!!.onSearch(authResponse.body()!!.payload)
                } else {
                    commonInterface!!.onFailureAPI(
                        authResponse.message(),
                        authResponse.code(),
                        authResponse.errorBody()
                    )
                }
            } catch (e: ApiException) {
                e.message?.let { commonInterface!!.onFailure(it) }
            } catch (ex: Exception) {
                ex.message?.let { commonInterface!!.onFailure(it) }
            }
        }
    }


}