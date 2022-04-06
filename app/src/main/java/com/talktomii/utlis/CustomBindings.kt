package com.talktomii.utlis

import android.content.Context
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.talktomii.R


class CustomBindings {
    companion object {


        @BindingAdapter("setImageFromUrl")
        @JvmStatic
        fun bindGlideWithImage(view: View, url: String?) {
            loadCoverImage(view.context, view as ImageView, url)
        }

        private fun loadCoverImage(activity: Context, circleImageView: ImageView, url: String?) {
            Glide.with(activity).load(url).placeholder(R.color.grey_text).into(circleImageView)
        }

    }


}