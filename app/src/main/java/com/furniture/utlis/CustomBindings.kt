package com.furniture.utlis

import android.app.Activity
import android.content.Context
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.furniture.R
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager


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