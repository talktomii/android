package com.furniture.interfaces

import com.furniture.data.model.Payload

interface SearchInterface {
    fun onSearchAllInstruction(data: Payload)
}