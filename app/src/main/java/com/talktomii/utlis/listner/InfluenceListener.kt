package com.talktomii.utlis.listner

import com.talktomii.data.model.appointment.AppointmentByIdPayload
import com.talktomii.data.model.appointment.AppointmentPayload
import com.talktomii.data.model.appointment.PayloadUpdate
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

interface AddInfluncerItem {
    fun addInfluenceItem(payload: AppointmentByIdPayload)
}