package com.furniture.ui.home

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.furniture.data.apis.WebService
import com.furniture.data.model.admin.Admin
import com.furniture.data.model.admin1.Admin1
import com.furniture.data.network.Coroutines
import com.furniture.data.network.responseUtil.Resource
import com.furniture.di.SingleLiveEvent
import com.furniture.interfaces.AdminDetailInterface
import com.furniture.interfaces.CommonInterface
import com.furniture.interfaces.HomeInterface
import com.furniture.utlis.AUTHORIZATION
import com.google.android.gms.common.api.ApiException
import javax.inject.Inject


class HomeScreenViewModel @Inject constructor(private val webService: WebService) : ViewModel() {
    var homeInterface: HomeInterface? = null
    var adminDetailInterface: AdminDetailInterface? = null
    var commonInterface: CommonInterface? = null
    private val adminData = ObservableField<Admin1>()
    val adminProfile by lazy { SingleLiveEvent<Resource<Admin>>() }
    val takePhoto = MutableLiveData<Admin1>()


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

    fun getAdminById(string: String) {
        commonInterface!!.onStarted()
        Coroutines.main {
            try {
                val authResponse = webService.getAdminByID(string, AUTHORIZATION)
                if (authResponse.isSuccessful) {
                    authResponse.body().let {
                        adminDetailInterface?.onAdminDetails(authResponse.body()!!.payload.admin[0])
//                        takePhoto.value =
//                        UserViewModel().setUserViewModel(authResponse.body()!!.payload.admin[0])

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


