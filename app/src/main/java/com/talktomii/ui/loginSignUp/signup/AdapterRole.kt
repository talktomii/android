package com.talktomii.ui.loginSignUp.signup

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.talktomii.data.model.Role
import com.talktomii.databinding.ItemRoleBinding

class AdapterRole(var roles:List<Role>,var fragment:CreateProfileFragment) : RecyclerView.Adapter<AdapterRole.ViewHolder>(){
    var selectedPos=0

    class ViewHolder(val binding: ItemRoleBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder  {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemRoleBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }


    override fun getItemCount(): Int {
        return roles.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.radioUser.isChecked = selectedPos==position
        holder.binding.radioUser.text = roles[position].roleName
        holder.binding.radioUser.setOnClickListener {
            selectedPos=position
            notifyDataSetChanged()
        }
        fragment.onRoleChanged(roles[selectedPos])

    }
}