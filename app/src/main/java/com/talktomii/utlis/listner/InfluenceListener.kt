package com.talktomii.utlis.listner

import com.talktomii.data.model.appointment.AppointmentPayload
import com.talktomii.data.model.getallslotbydate.PayloadAppoinment

interface InfluenceListener {
    fun influenceList(payload: PayloadAppoinment)
}

interface InfluenceCalenderListener {
    fun influenceCalenderList(payload: AppointmentPayload)
}

interface InfluncerItem {
    fun influenceItem(payload: AppointmentPayload)
}