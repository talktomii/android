package com.talktomii.interfaces

import com.talktomii.data.model.admin.Admin
import com.talktomii.data.model.admin.Payload
import com.talktomii.data.model.admin1.Admin1
import com.talktomii.data.model.getallslotbydate.TimeStop

interface HomeInterface {
    fun onHomeAdmins(payload: Payload)
}

interface AdminDetailInterface{
    fun onAdminDetails(admin1: Admin1)
}

interface UpdatePhotoInterface{
    fun onUpdatePhoto(admin: Admin)
}
interface OnSlotSelectedInterface {
    fun onslotselect(list: TimeStop)
}