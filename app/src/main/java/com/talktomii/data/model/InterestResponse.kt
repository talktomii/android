package com.talktomii.data.model

import androidx.databinding.ObservableBoolean
import com.talktomii.recycleradapter.AbstractModel
import com.talktomii.ui.tellusmore.ItemsViewModel

data class InterestResponse(
    val count: Int,
    val interest: List<ItemsViewModel>
)