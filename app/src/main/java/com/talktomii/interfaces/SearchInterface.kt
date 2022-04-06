package com.talktomii.interfaces

import com.talktomii.data.model.Interest
import com.talktomii.data.model.Payload

interface SearchInterface {
    fun onSearchAllInstruction(data: Payload)
}
interface SearchItemClick{
    fun onViewSearchClick(interest: Interest)
}