package com.talktomii.viewmodel

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.google.android.gms.common.api.ApiException
import com.google.gson.GsonBuilder
import com.talktomii.data.apis.WebService
import com.talktomii.data.model.ErrorModelClass
import com.talktomii.data.model.currentwallet.WalletData
import com.talktomii.data.network.Coroutines
import com.talktomii.interfaces.*
import com.talktomii.utlis.listner.*
import java.io.IOException
import javax.inject.Inject

class InfluenceHomeViewModel @Inject constructor(private val webService: WebService) : ViewModel() {
    var commonInterface: CommonInterface? = null
    var infulancerListner: InfluenceListener? = null
    var callHistoryListener: CallHistory? = null
    var infulancerCalenderListner: InfluenceCalenderListener? = null
    var addInfluncerItem: AddInfluncerItem? = null
    var influncerItem: InfluncerItem? = null
    var walletData = ObservableField<WalletData>()
    var onSlotSelectedInterface: OnSlotSelectedInterface? = null
    var deleteAppointmentListener: DeleteAppointmentListener? = null
    var rescheduleAppointmentListener: RescheduleAppointmentListener? = null
    var influencerDashboardInterface: InfluencerDashboardInterface? = null

    fun getCurrentWallet(id: String) {
        commonInterface!!.onStarted()
        Coroutines.main {
            try {
                val authResponse =
                    webService.getCurrentAmountFromWallet(id)
                if (authResponse.isSuccessful) {
                    authResponse.body().let {
                        walletData.set(authResponse.body()!!.payload.walletData)
//                        searchInterface?.onSearchAllInstruction(authResponse.body()!!.payload)
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

    fun getAllSlotByDate(date: String, _id: String) {
        commonInterface!!.onStarted()
        Coroutines.main {
            try {
                val authResponse = webService.getAllSlotByDate(date, _id)
                if (authResponse.isSuccessful) {
                    if (authResponse.body()!!.result == 0) {
                        authResponse.body().let {
                            onSlotSelectedInterface!!.onSlotTimesList(authResponse.body()!!.payload)
                        }
                    } else {
                        commonInterface!!.onFailureAPI(
                            authResponse.message(),
                            authResponse.code(),
                            authResponse.errorBody()
                        )
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

    fun getAllAppoinemntByUserId(id: String) {
        commonInterface!!.onStarted()
        Coroutines.main {
            try {
                val authResponse = webService.getCalenderAllAppointment(id)
                if (authResponse.isSuccessful) {
                    authResponse.body().let {
//                        walletData.set(authResponse.body()!!.payload.walletData)
                        infulancerCalenderListner?.influenceCalenderList(authResponse.body()!!.payload)
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

    fun getUsersCallHistory(id: String) {
        commonInterface!!.onStarted()
        Coroutines.main {
            try {
                val authResponse = webService.getCallUsersCallHistory(id)
                if (authResponse.isSuccessful) {
                    authResponse.body().let {
                        callHistoryListener?.getCallHistory(authResponse.body()!!.payload)
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

    fun getAllAppointmentByCalender(id: String) {
        commonInterface!!.onStarted()
        Coroutines.main {
            try {
                val authResponse = webService.getCalenderAllAppointment(id)
                if (authResponse.isSuccessful) {
                    authResponse.body().let {
//                        walletData.set(authResponse.body()!!.payload.walletData)
                        infulancerCalenderListner?.influenceCalenderList(authResponse.body()!!.payload)
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

    fun getAppointmentById(id: String) {
        commonInterface!!.onStarted()
        Coroutines.main {
            try {
                val authResponse = webService.getAppointmentById(id)
                if (authResponse.isSuccessful) {
                    authResponse.body().let {
//                        walletData.set(authResponse.body()!!.payload.walletData)
                        addInfluncerItem?.addInfluenceItem(authResponse.body()!!.payload)
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

    fun getAllAppointmentByDate(date: String, _id: String) {
        commonInterface!!.onStarted()
        Coroutines.main {
            try {
                val authResponse = webService.getAppointmentByDate(date, _id)
                if (authResponse.isSuccessful) {
                    authResponse.body().let {
//                        walletData.set(authResponse.body()!!.payload.walletData)
                        influncerItem?.influenceItem(authResponse.body()!!.payload)
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
    fun getAllAppointmentByDateWithStatus(date: String, _id: String) {
        commonInterface!!.onStarted()
        Coroutines.main {
            try {
                val authResponse = webService.getAppointmentByDateWithStatus(date, _id,"booked")
                if (authResponse.isSuccessful) {
                    authResponse.body().let {
//                        walletData.set(authResponse.body()!!.payload.walletData)
                        influncerItem?.influenceItem(authResponse.body()!!.payload)
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
    fun updateAppointment(id: String, hashMap: java.util.HashMap<String, Any>) {
        commonInterface!!.onStarted()
        Coroutines.main {
            try {
                val authResponse = webService.updateAppointment(id, hashMap)
                if (authResponse.isSuccessful) {
                    authResponse.body().let {
//                        walletData.set(authResponse.body()!!.payload.walletData)
                        rescheduleAppointmentListener?.onRescheduleAppointment(authResponse.body()!!.payload)
                    }
                } else {
                    if (authResponse.code() === 400) {
                        val gson = GsonBuilder().create()
                        var mError = ErrorModelClass()
                        try {
                            mError = gson.fromJson(
                                authResponse.errorBody()!!.string(),
                                ErrorModelClass::class.java
                            )
                            commonInterface!!.onFailureAPI(
                                mError.message,
                                authResponse.code(),
                                authResponse.errorBody()
                            )

                        } catch (e: IOException) {
                            // handle failure to read error
                        }
                    } else {
                        commonInterface!!.onFailureAPI(
                            authResponse.message(),
                            authResponse.code(),
                            authResponse.errorBody()
                        )
                    }
                }
            } catch (e: ApiException) {
                e.message?.let { commonInterface!!.onFailure(it) }
            } catch (ex: Exception) {
                ex.message?.let { commonInterface!!.onFailure(it) }
            }
        }
    }

    fun deleteAppointment(id: String, hashMap: HashMap<String, Any>, position: Int) {
        commonInterface!!.onStarted()
        Coroutines.main {
            try {
                val authResponse = webService.deleteAppointment(id, hashMap)
                if (authResponse.isSuccessful) {
                    authResponse.body().let {
                        authResponse.body()
                            ?.let { it1 ->
                                deleteAppointmentListener?.onDeleteAppointment(
                                    it1.payload,
                                    position
                                )
                            }
//                        infulancerCalenderListner?.infulancerCalenderList(authResponse.body()!!.payload)
                    }
                } else {
                    if (authResponse.code() === 400) {
                        val gson = GsonBuilder().create()
                        var mError = ErrorModelClass()
                        try {
                            mError = gson.fromJson(
                                authResponse.errorBody()!!.string(),
                                ErrorModelClass::class.java
                            )
                            commonInterface!!.onFailureAPI(
                                mError.message,
                                authResponse.code(),
                                authResponse.errorBody()
                            )

                        } catch (e: IOException) {
                            // handle failure to read error
                        }
                    } else {
                        commonInterface!!.onFailureAPI(
                            authResponse.message(),
                            authResponse.code(),
                            authResponse.errorBody()
                        )
                    }
                }
            } catch (e: ApiException) {
                e.message?.let { commonInterface!!.onFailure(it) }
            } catch (ex: Exception) {
                ex.message?.let { commonInterface!!.onFailure(it) }
            }
        }
    }

    fun getInfluencerDashboard(type: String, id: String) {
        commonInterface!!.onStarted()
        Coroutines.main {
            try {
                val authResponse = webService.getInfluencerDashboard(type, id)
                if (authResponse.isSuccessful) {
                    influencerDashboardInterface?.onData(authResponse.body()!!.payload)
                } else {
                    if (authResponse.code() === 400) {
                        val gson = GsonBuilder().create()
                        var mError = ErrorModelClass()
                        try {
                            mError = gson.fromJson(
                                authResponse.errorBody()!!.string(),
                                ErrorModelClass::class.java
                            )
                            commonInterface!!.onFailureAPI(
                                mError.message,
                                authResponse.code(),
                                authResponse.errorBody()
                            )
                        } catch (e: IOException) {
                        }
                    } else {
                        commonInterface!!.onFailureAPI(
                            authResponse.message(),
                            authResponse.code(),
                            authResponse.errorBody()
                        )
                    }
                }
            } catch (e: ApiException) {
                e.message?.let { commonInterface!!.onFailure(it) }
            } catch (ex: Exception) {
                ex.message?.let { commonInterface!!.onFailure(it) }
            }
        }
    }

}

//object Converter {
//    @InverseMethod("stringToDate")
//    @JvmStatic
//    fun dateToString(
//        view: TextView, oldValue: Long,
//        value: Long
//    ): String {
//        // Converts long to String.
//    }
//
//}
