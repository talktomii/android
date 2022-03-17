package com.furniture.utlis.listner

import com.furniture.data.model.CartResponseModel

interface CartItemClickedCallback {
    fun onCartItemClicked(cartItem: CartResponseModel)
    fun onCartItemRemoved(cartItem: CartResponseModel)
}