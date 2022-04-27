package com.talktomii.ui.home.profile.editinterest

import android.content.Context
import android.content.res.ColorStateList
import android.content.res.Configuration
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.talktomii.R
import com.talktomii.data.model.Interest
import com.talktomii.databinding.ItemEditInterestBinding

class AdapterEditInterest(context: Context) :
    RecyclerView.Adapter<AdapterEditInterest.ViewHolder>() {

    var context: Context
    var isWhich = 1

    init {
        this.context = context
    }

    private var arraylist: ArrayList<Interest> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemEditInterestBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    class ViewHolder(val binding: ItemEditInterestBinding) : RecyclerView.ViewHolder(binding.root)

    override fun getItemCount(): Int {
//        return if (arraylist.size > 3)
//            3
//        else
        return arraylist.size
    }

    fun setItemList(list: ArrayList<Interest>, which: Int) {
        isWhich = which
        if (arraylist.isNotEmpty()) {
            arraylist.clear()
        }
        arraylist.addAll(list)
        notifyDataSetChanged()
    }

    fun getArrayList(): ArrayList<Interest> {
        return arraylist
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var model = arraylist.get(position)
        holder.binding.tvItemName.text = model.name
        when (context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
            Configuration.UI_MODE_NIGHT_YES -> {
                if (isWhich == 1) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        holder.binding.tvItemName.backgroundTintList =
                            ColorStateList.valueOf(context.getColor(R.color.blue))
                        holder.binding.tvItemName.setTextColor(context.getColor(R.color.white))
                    }
                } else {
                    if (model.isClicked) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            holder.binding.tvItemName.backgroundTintList =
                                ColorStateList.valueOf(context.getColor(R.color.blue))
                            holder.binding.tvItemName.setTextColor(context.getColor(R.color.white))
                        }
                    } else {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            holder.binding.tvItemName.backgroundTintList =
                                ColorStateList.valueOf(context.getColor(android.R.color.transparent))
                            holder.binding.tvItemName.setTextColor(context.getColor(R.color.white))
                        }
                    }

                }

                if (isWhich == 2) {
                    holder.binding.clItems.setOnClickListener {
                        if (model.isClicked) {
                            model.isClicked = false
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                holder.binding.tvItemName.backgroundTintList =
                                    ColorStateList.valueOf(context.getColor(android.R.color.transparent))
                                holder.binding.tvItemName.setTextColor(context.getColor(R.color.white))
                            }
                        } else {
                            model.isClicked = true
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                holder.binding.tvItemName.backgroundTintList =
                                    ColorStateList.valueOf(context.getColor(R.color.blue))
                                holder.binding.tvItemName.setTextColor(context.getColor(R.color.white))
                            }
                        }
                    }
                }
            }
            Configuration.UI_MODE_NIGHT_NO -> {
                if (isWhich == 1) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        holder.binding.tvItemName.backgroundTintList =
                            ColorStateList.valueOf(context.getColor(R.color.blue))
                        holder.binding.tvItemName.setTextColor(context.getColor(R.color.white))
                    }
                } else {
                    if (model.isClicked) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            holder.binding.tvItemName.backgroundTintList =
                                ColorStateList.valueOf(context.getColor(R.color.blue))
                            holder.binding.tvItemName.setTextColor(context.getColor(R.color.white))
                        }
                    } else {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            holder.binding.tvItemName.backgroundTintList =
                                ColorStateList.valueOf(context.getColor(R.color.white))
                            holder.binding.tvItemName.setTextColor(context.getColor(R.color.black))
                        }
                    }

                }

                if (isWhich == 2) {
                    holder.binding.clItems.setOnClickListener {
                        if (model.isClicked) {
                            model.isClicked = false
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                holder.binding.tvItemName.backgroundTintList =
                                    ColorStateList.valueOf(context.getColor(R.color.white))
                                holder.binding.tvItemName.setTextColor(context.getColor(R.color.black))
                            }
                        } else {
                            model.isClicked = true
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                holder.binding.tvItemName.backgroundTintList =
                                    ColorStateList.valueOf(context.getColor(R.color.blue))
                                holder.binding.tvItemName.setTextColor(context.getColor(R.color.white))
                            }
                        }
                    }
                }
            }
        }
    }

}