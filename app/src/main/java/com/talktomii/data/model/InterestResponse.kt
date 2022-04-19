package com.talktomii.data.model

import com.talktomii.adapter.TopicsAdapter


data class InterestResponse(
    val count: Int,
    val interest: List<TopicsAdapter.ItemsViewModel>
)