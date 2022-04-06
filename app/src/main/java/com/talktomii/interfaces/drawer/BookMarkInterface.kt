package com.talktomii.interfaces.drawer

import com.talktomii.data.model.admin1.Admin1
import com.talktomii.data.model.drawer.bookmark.Payload

interface BookMarkInterface {
    fun onBookmarkAdmins(payload: Payload)
}

interface AdminDetailInterface{
    fun onAdminDetails(admin1: Admin1)

}