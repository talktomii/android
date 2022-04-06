package com.furniture.viewmodel

import androidx.lifecycle.ViewModel
import com.furniture.data.apis.WebService
import com.furniture.data.network.Coroutines
import com.furniture.interfaces.CommonInterface
import com.furniture.interfaces.SearchInterface
import com.google.android.gms.common.api.ApiException
import javax.inject.Inject

class SearchViewModel @Inject constructor(private val webService: WebService) : ViewModel() {
    var searchInterface: SearchInterface? = null
    var commonInterface: CommonInterface? = null


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


}