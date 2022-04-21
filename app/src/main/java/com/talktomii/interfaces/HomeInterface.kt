package com.talktomii.interfaces

import com.talktomii.data.model.admin.Availaibility
import com.talktomii.data.model.admin1.Admin1
import com.talktomii.data.model.appointment.PayloadUpdate
import com.talktomii.data.model.appointment.UpdateAppointmentPayload
import com.talktomii.data.model.drawer.bookmark.BookMarkResponse
import com.talktomii.data.model.drawer.bookmark.Payload
import com.talktomii.data.photo.Admin

interface HomeInterface {
    fun onHomeAdmins(payload: com.talktomii.data.model.admin.Payload)
}

interface AdminDetailInterface {
    fun onAdminDetails(admin1: Admin1)
}


interface UpdateProfileInterface {
    fun onUpdateProfileDetails(admin1: com.talktomii.data.model.Admin)
}

interface UpdateAvaibilityInterface {
    fun onUpdateAvibility(admin1: Int, model: Availaibility)
}

interface OnSlotSelectedInterface {
    fun onSlotTimesList(list: com.talktomii.data.model.getallslotbydate.Payload)
}

interface UpdatePhotoInterface {
    fun onUpdatePhoto(admin: Admin)

}

interface AddAppointmentInterface {
    fun onAddAppointment(admin: BookMarkResponse?)

}


interface DeleteAppointmentListener {
    fun onDeleteAppointment(admin: UpdateAppointmentPayload, position: Int)
}

interface RescheduleAppointmentListener {
    fun onRescheduleAppointment(admin: UpdateAppointmentPayload)
}