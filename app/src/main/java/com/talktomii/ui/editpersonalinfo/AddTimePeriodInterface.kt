package com.talktomii.ui.editpersonalinfo

import com.talktomii.data.model.admin.Availaibility

interface AddTimePeriodInterface {
    fun addTimePeriod(model: Availaibility, isEdit: Boolean, position: Int)
}