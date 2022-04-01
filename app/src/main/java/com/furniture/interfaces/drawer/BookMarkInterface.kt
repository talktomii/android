package com.furniture.interfaces.drawer

import com.furniture.data.model.admin1.Admin1
import com.furniture.data.model.drawer.bookmark.Payload

interface BookMarkInterface {
    fun onBookmarkAdmins(payload: Payload)
}

interface AdminDetailInterface{
    fun onAdminDetails(admin1: Admin1)

}