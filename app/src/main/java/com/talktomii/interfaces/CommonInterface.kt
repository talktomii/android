package com.talktomii.interfaces

interface CommonInterface {
    fun onFailure(message: String)
    fun onFailureAPI(message: String)
    fun onStarted()
}
interface FailureAPI400{
    fun onFailureAPI400(message: String)
}

interface onStopProgress{
    fun  onStopProgress()
}