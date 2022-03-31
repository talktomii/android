package com.furniture.ui.home

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.furniture.data.apis.WebService
import com.furniture.data.model.admin1.Admin1
import com.furniture.data.network.Coroutines
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
    var userField = ObservableField<Admin1>()
    var username: ObservableField<String> = ObservableField()

    fun getInfluence(string: String) {
        commonInterface!!.onStarted()
        Coroutines.main {
            try {
                val authResponse = if (string.isEmpty()) {
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
                        userField.set(authResponse.body()!!.payload.admin[0])
                        username.set(authResponse.body()?.payload?.admin?.get(0)?.userName)
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


