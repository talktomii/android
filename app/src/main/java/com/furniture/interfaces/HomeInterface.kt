package com.furniture.interfaces

import com.furniture.data.model.admin.Payload
import com.furniture.data.model.admin1.Admin1

interface HomeInterface {
    fun onHomeAdmins(payload: Payload)
}

interface AdminDetailInterface{
    fun onAdminDetails(admin1: Admin1)

}