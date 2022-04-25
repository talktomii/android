package com.talktomii.interfaces

import com.talktomii.data.model.admin1.PayloadDashBoard


interface InfluencerDashboardInterface {
    fun onData(payload: PayloadDashBoard)
}
