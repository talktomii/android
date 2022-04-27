package com.talktomii.ui.home.profile.editinterest

import com.talktomii.data.model.Interest

interface GetItemsInterface {
    fun onItems(list: ArrayList<Interest>)
}