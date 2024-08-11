package com.orryfrasetyo.suitmediainterntestapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.orryfrasetyo.suitmediainterntestapp.databinding.ItemListBinding
import com.orryfrasetyo.suitmediainterntestapp.response.DataItem

class UserAdapter(
    private val onItemClick: (DataItem) -> Unit
): ListAdapter<DataItem, UserAdapter.MyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user)
        holder.itemView.setOnClickListener {
            onItemClick.invoke(user)
        }
    }

    class MyViewHolder(private val binding: ItemListBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: DataItem) {
            binding.tvFirstName.text = item.firstName
            binding.tvLastName.text = item.lastName
            binding.tvEmail.text = item.email
            Glide.with(itemView.context)
                .load(item.avatar)
                .circleCrop()
                .into(binding.ivItem)
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DataItem>() {
            override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
                return oldItem == newItem
            }

        }
    }
}