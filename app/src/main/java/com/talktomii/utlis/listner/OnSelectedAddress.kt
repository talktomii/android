package com.talktomii.utlis.listner

import com.talktomii.data.model.AddressModel

interface OnSelectedAddress {
    fun onItemSelect(address:AddressModel)
}