package com.talktomii.viewmodel

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.google.android.gms.common.api.ApiException
import com.talktomii.data.apis.WebService
import com.talktomii.data.model.currentwallet.WalletData
import com.talktomii.data.network.Coroutines
import com.talktomii.interfaces.CommonInterface
import com.talktomii.interfaces.OnSlotSelectedInterface
import com.talktomii.utlis.listner.InfulancerCalenderListner
import com.talktomii.utlis.listner.InfulancerListner
import javax.inject.Inject

class InfluenceHomeViewModel @Inject constructor(private val webService: WebService) : ViewModel() {
    var commonInterface: CommonInterface? = null
    var infulancerListner: InfulancerListner? = null
    var infulancerCalenderListner: InfulancerCalenderListner? = null
    var walletData = ObservableField<WalletData>()
    var onSlotSelectedInterface: OnSlotSelectedInterface? = null


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
                    commonInterface!!.onFailure(authResponse.message())
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

    fun getAllAppoinemnt(id: String) {
        commonInterface!!.onStarted()
        Coroutines.main {
            try {
                val authResponse = webService.getAllAppointment(id)
                if (authResponse.isSuccessful) {
                    authResponse.body().let {
//                        walletData.set(authResponse.body()!!.payload.walletData)
                        infulancerListner?.infulancerList(authResponse.body()!!.payload)
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

    fun getAllAppointmentByCalender(id: String) {
        commonInterface!!.onStarted()
        Coroutines.main {
            try {
                val authResponse = webService.getCalenderAllAppointment(id)
                if (authResponse.isSuccessful) {
                    authResponse.body().let {
//                        walletData.set(authResponse.body()!!.payload.walletData)
                        infulancerCalenderListner?.infulancerCalenderList(authResponse.body()!!.payload)
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

    fun updateAppointment(id: String, status: String, isDelete: Boolean, endTime: String) {
        commonInterface!!.onStarted()
        Coroutines.main {
            try {
                val authResponse = webService.updateAppointment(id, status, isDelete, endTime)
                if (authResponse.isSuccessful) {
                    authResponse.body().let {
//                        walletData.set(authResponse.body()!!.payload.walletData)
//                        infulancerCalenderListner?.infulancerCalenderList(authResponse.body()!!.payload)
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
