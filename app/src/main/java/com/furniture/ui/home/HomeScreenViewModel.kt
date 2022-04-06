package com.furniture.ui.home

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.furniture.data.apis.WebService
import com.furniture.data.model.admin1.Admin1
import com.furniture.data.network.Coroutines
import com.furniture.interfaces.AdminDetailInterface
import com.furniture.interfaces.CommonInterface
import com.furniture.interfaces.HomeInterface
import com.furniture.interfaces.OnSlotSelectedInterface
import com.furniture.utlis.AUTHORIZATION
import com.google.android.gms.common.api.ApiException
import javax.inject.Inject


class HomeScreenViewModel @Inject constructor(private val webService: WebService) : ViewModel() {
    var homeInterface: HomeInterface? = null
    var adminDetailInterface: AdminDetailInterface? = null
    var commonInterface: CommonInterface? = null
    var userField = ObservableField<Admin1>()
    var bookMark = ObservableField<Boolean>()
    var onSlotSelectedInterface: OnSlotSelectedInterface? = null
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
                    commonInterface!!.onFailureAPI(authResponse.message())
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
                        bookMark.set(authResponse.body()!!.payload.admin[0].bookmark)
                        userField.set(authResponse.body()!!.payload.admin[0])
                    }
                } else {
                    commonInterface!!.onFailureAPI(authResponse.message())
                }
            } catch (e: ApiException) {
                e.message?.let { commonInterface!!.onFailure(it) }
            } catch (ex: Exception) {
                ex.message?.let { commonInterface!!.onFailure(it) }
            }
        }
    }

    fun addBookmark() {

        var hashMap: HashMap<String, Any> = hashMapOf()
        hashMap.put("ifid", userField.get()!!._id)

//        commonInterface!!.onStarted()
        Coroutines.main {
            try {
                val authResponse = webService.addFavourite(hashMap, AUTHORIZATION)
                if (authResponse.isSuccessful) {
                    if (authResponse.body()!!.result == 0) {
                        authResponse.body().let {
                            bookMark.set(true)
                        }
                    }
                } else {
//                    commonInterface!!.onFailureAPI(authResponse.message())
                }
            } catch (e: ApiException) {
                e.message?.let { commonInterface!!.onFailure(it) }
            } catch (ex: Exception) {
                ex.message?.let { commonInterface!!.onFailure(it) }
            }
        }
    }

    fun removeBookmark() {
//        commonInterface!!.onStarted()
        Coroutines.main {
            try {
                val authResponse = webService.removeBookmark(userField.get()!!._id, AUTHORIZATION)
                if (authResponse.isSuccessful) {
                    if (authResponse.body()!!.result == 0) {
                        authResponse.body().let {
                            bookMark.set(false)
                        }
                    }
                } else {
                    commonInterface!!.onFailureAPI(authResponse.message())
                }
            } catch (e: ApiException) {
                e.message?.let { commonInterface!!.onFailure(it) }
            } catch (ex: Exception) {
                ex.message?.let { commonInterface!!.onFailure(it) }
            }
        }
    }

    fun checkAndSetBookMark() {
        if (bookMark.get() == true) {
            removeBookmark()
        } else {
            addBookmark()
        }
    }

    fun getAllSlotByDate(date: String) {
        commonInterface!!.onStarted()
        Coroutines.main {
            try {
                val authResponse = webService.getAllSlotByDate(date, userField.get()!!._id)
                if (authResponse.isSuccessful) {
                    if (authResponse.body()!!.result == 0) {
                        authResponse.body().let {
                            onSlotSelectedInterface!!.onslotselect(authResponse.body()!!.payload.timeStops[0])
                        }
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


