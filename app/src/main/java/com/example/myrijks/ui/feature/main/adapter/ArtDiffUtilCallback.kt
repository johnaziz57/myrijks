package com.example.myrijks.ui.feature.main.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.myrijks.ui.feature.main.model.ItemWrapper

class ArtDiffUtilCallback(
    private val oldList: List<ItemWrapper<*>>,
    private val newList: List<ItemWrapper<*>>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        return newItem.type == oldItem.type && oldItem.item == newItem.item
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        return oldItem.item == newItem.item
    }
}