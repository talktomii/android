package com.talktomii.ui.home.profile.editinterest

import androidx.lifecycle.ViewModel
import com.google.android.gms.common.api.ApiException
import com.talktomii.data.apis.WebService
import com.talktomii.data.network.Coroutines
import com.talktomii.interfaces.CommonInterface
import javax.inject.Inject

class EditInterestVM @Inject constructor(private val webService: WebService) : ViewModel() {

    var commonInterface: CommonInterface? = null
    var onGetItems: GetItemsInterface? = null

    fun getAllInterest() {
        commonInterface!!.onStarted()
        Coroutines.main {
            try {
                val authResponse = webService.getAllInterest()
                if (authResponse.isSuccessful) {
                    if (authResponse.body()!!.result == 0) {
                        authResponse.body().let {
                            onGetItems!!.onItems(authResponse.body()!!.payload.interest)
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
