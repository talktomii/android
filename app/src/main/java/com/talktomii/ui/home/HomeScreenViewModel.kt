package com.talktomii.ui.home

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.google.android.gms.common.api.ApiException
import com.talktomii.data.apis.WebService
import com.talktomii.data.model.admin1.Admin1
import com.talktomii.data.network.Coroutines
import com.talktomii.interfaces.*
import javax.inject.Inject


class HomeScreenViewModel @Inject constructor(private val webService: WebService) : ViewModel() {
    var homeInterface: HomeInterface? = null
    var adminDetailInterface: AdminDetailInterface? = null
    var commonInterface: CommonInterface? = null
    var onStopProgress: onStopProgress? = null
    var userField = ObservableField<Admin1>()
    var bookMark = ObservableField<Boolean>()
    var onSlotSelectedInterface: OnSlotSelectedInterface? = null
    fun getInfluence(string: String) {
        commonInterface!!.onStarted()
        Coroutines.main {
            try {
                val authResponse = if (string.isEmpty()) {
                    webService.getAllAdmin()
                } else {
                    webService.getAdminByInterest(string)
                }
                if (authResponse.isSuccessful) {
                    authResponse.body().let {
                        homeInterface?.onHomeAdmins(authResponse.body()!!.payload)
                    }
                } else {
                    commonInterface!!.onFailureAPI(
                        authResponse.message(), authResponse.code(), authResponse.errorBody()
                    )

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
                val authResponse = webService.getAdminByID(string)
                if (authResponse.isSuccessful) {
                    authResponse.body().let {
                        adminDetailInterface?.onAdminDetails(authResponse.body()!!.payload.admin[0])
                        bookMark.set(authResponse.body()!!.payload.admin[0].bookmark)
                        userField.set(authResponse.body()!!.payload.admin[0])
                    }
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

    private fun addBookmark() {

        var hashMap: HashMap<String, Any> = hashMapOf()
        hashMap.put("ifid", userField.get()?._id?:"")

        commonInterface!!.onStarted()
        Coroutines.main {
            try {
                val authResponse = webService.addFavourite(hashMap)
                if (authResponse.isSuccessful) {
                    if (authResponse.body()!!.result == 0) {
                        authResponse.body().let {
                            bookMark.set(true)
                            onStopProgress!!.onStopProgress()
                        }
                    }
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

    fun removeBookmark() {
        commonInterface!!.onStarted()
        Coroutines.main {
            try {
                val authResponse = webService.removeBookmark(userField.get()!!._id)
                if (authResponse.isSuccessful) {
                    if (authResponse.body()!!.result == 0) {
                        authResponse.body().let {
                            onStopProgress!!.onStopProgress()
                            bookMark.set(false)
                        }
                    }
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
                            onSlotSelectedInterface!!.onSlotTimesList(authResponse.body()!!.payload)
                        }
                    }
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


