package com.furniture.interfaces

import com.furniture.data.model.admin.Payload

interface HomeInterface {
    fun onHomeAdmins(payload: Payload)
}