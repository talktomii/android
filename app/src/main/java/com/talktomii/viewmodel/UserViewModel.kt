package com.talktomii.viewmodel

import androidx.databinding.ObservableField
import com.talktomii.data.model.admin1.Admin1

class UserViewModel() {
    private val userField = ObservableField<Admin1>()

    fun setUserViewModel(user: Admin1) {
        userField.set(user)
    }

    fun getUser(): ObservableField<Admin1> {
        return userField
    }
}