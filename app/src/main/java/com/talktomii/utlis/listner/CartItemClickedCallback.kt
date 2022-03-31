package com.talktomii.utlis.listner

import com.talktomii.data.model.CartResponseModel

interface CartItemClickedCallback {
    fun onCartItemClicked(cartItem: CartResponseModel)
    fun onCartItemRemoved(cartItem: CartResponseModel)
}