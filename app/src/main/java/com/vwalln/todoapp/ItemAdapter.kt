package com.vwalln.todoapp

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.vwalln.todoapp.databinding.ListItemBinding

class ItemAdapter(private val items: MutableList<Item>) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {
    inner class ItemViewHolder(val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root){
        private val onItemClicked: MutableList<Item> = items
        fun bind(item: Item){
            binding.itemName.text = item.name
            if (item.isDone){
                binding.itemName.paintFlags = binding.itemName.paintFlags or android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
                binding.itemName.setTextColor(binding.root.context.getColor(R.color.gray))
            } else{
                binding.itemName.paintFlags = binding.itemName.paintFlags or android.graphics.Paint.STRIKE_THRU_TEXT_FLAG.inv()
                binding.itemName.setTextColor(binding.root.context.getColor(android.R.color.black))
            }


           binding.itemName.setOnClickListener{
                val position = bindingAdapterPosition
                if(position != RecyclerView.NO_POSITION){
                    if (item.isDone == true){
                        item.isDone = false
                    }else{
                        item.isDone = true
                    }
                    notifyItemChanged(position)
                }
            }

            binding.deleteIcon.setOnClickListener {
                val position = bindingAdapterPosition
                if(position != RecyclerView.NO_POSITION){
                    removeItem(position)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size



    //fügt item hinzu
    fun addItem(item: Item) {
        items.add(item)
        notifyItemInserted(items.size - 1)
    }

    //löscht item
    fun removeItem(position: Int){
        items.removeAt(position)
        notifyItemRemoved(position)
    }
}