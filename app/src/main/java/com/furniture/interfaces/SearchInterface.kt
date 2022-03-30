package com.furniture.interfaces

import com.furniture.data.model.Interest
import com.furniture.data.model.Payload

interface SearchInterface {
    fun onSearchAllInstruction(data: Payload)
}
interface SearchItemClick{
    fun onViewSearchClick(interest: Interest)
}