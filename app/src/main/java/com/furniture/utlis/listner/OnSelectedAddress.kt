package com.furniture.utlis.listner

import com.furniture.data.model.AddressModel

interface OnSelectedAddress {
    fun onItemSelect(address:AddressModel)
}