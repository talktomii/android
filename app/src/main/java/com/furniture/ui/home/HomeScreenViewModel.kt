package com.furniture.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.furniture.data.apis.WebService
import com.furniture.data.model.AllInterst
import com.furniture.data.network.Coroutines
import com.furniture.interfaces.CommonInterface
import com.furniture.interfaces.HomeInterface
import com.furniture.utlis.AUTHORIZATION
import com.google.android.gms.common.api.ApiException
import javax.inject.Inject

class HomeScreenViewModel @Inject constructor(private val webService: WebService) : ViewModel() {
    var homeInterface: HomeInterface? = null
    var commonInterface: CommonInterface? = null
    private var onSearchResponse: LiveData<AllInterst> = MutableLiveData()


    fun getInfluence(string: String) {
        commonInterface!!.onStarted()
        Coroutines.main {
            try {
                var authResponse = if (string.isEmpty()) {
                    webService.getAllAdmin(AUTHORIZATION)
                } else {
                    webService.getAdminByInterest(AUTHORIZATION, string)
                }
                if (authResponse.isSuccessful) {
                    authResponse.body().let {
                        homeInterface?.onHomeAdmins(authResponse.body()!!.payload)
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