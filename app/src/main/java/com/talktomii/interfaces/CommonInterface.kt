package com.talktomii.interfaces

import com.talktomii.data.model.getallslotbydate.GetAllSlotByDateResponse
import okhttp3.ResponseBody
import retrofit2.Response

interface CommonInterface {
    fun onFailure(message: String)
    fun onFailureAPI(message: String, code: Int, errorBody: ResponseBody?)
    fun onStarted()
}
interface FailureAPI400{
    fun onFailureAPI400(message: String)
}

interface onStopProgress{
    fun  onStopProgress()
}