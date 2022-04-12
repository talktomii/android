package com.talktomii.utlis.listner

import com.talktomii.data.model.appointment.AppointmentPayload
import com.talktomii.data.model.getallslotbydate.PayloadAppoinment

interface InfulancerListner {
    fun infulancerList(payload: PayloadAppoinment)
}

interface InfulancerCalenderListner {
    fun infulancerCalenderList(payload: AppointmentPayload)
}