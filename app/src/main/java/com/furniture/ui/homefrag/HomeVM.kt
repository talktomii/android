package com.furniture.ui.homefrag

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.furniture.data.apis.WebService
import com.furniture.data.model.AllInterst
import com.furniture.data.network.Coroutines
import com.furniture.interfaces.CommonInterface
import com.furniture.interfaces.SearchInterface
import com.google.android.gms.common.api.ApiException
import javax.inject.Inject

class HomeVM @Inject constructor() : ViewModel() {
    var searchInterface: SearchInterface? = null
    var commonInterface: CommonInterface? = null
    private var onSearchResponse: LiveData<AllInterst> = MutableLiveData()


//    fun getAllInstruction() {
//        commonInterface!!.onStarted()
//        Coroutines.main {
//            try {
//                val authResponse = webService.getAllInterest()
//                if (authResponse.isSuccessful) {
//                    authResponse.body().let {
//                        searchInterface?.onSearchAllInstruction(authResponse.body()!!.payload)
//                    }
//                } else {
//                    commonInterface!!.onFailure(authResponse.message())
//                }
//            } catch (e: ApiException) {
//                e.message?.let { commonInterface!!.onFailure(it) }
//            } catch (ex: Exception) {
//                ex.message?.let { commonInterface!!.onFailure(it) }
//            }
//        }
//    }

}