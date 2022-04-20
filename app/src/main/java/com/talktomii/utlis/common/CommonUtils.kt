package com.talktomii.utlis.common

import android.content.Context
import android.widget.Toast

class CommonUtils {
    companion object {
        fun showToastMessage(context: Context, message: String) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }
}