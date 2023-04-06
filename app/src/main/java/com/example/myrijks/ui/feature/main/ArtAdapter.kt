package com.example.myrijks.ui.feature.main

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.myrijks.databinding.ArtItemBinding
import com.example.myrijks.ui.feature.main.model.ArtViewData
import com.example.myrijks.ui.util.viewBinding

class ArtAdapter : RecyclerView.Adapter<ArtAdapter.ArtViewHolder>() {

    private var items = emptyList<ArtViewData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtViewHolder {
        val itemBinding = parent.viewBinding { layoutInflater, viewGroup, isAttachToParent ->
            ArtItemBinding.inflate(
                layoutInflater,
                viewGroup,
                isAttachToParent
            )
        }
        return ArtViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ArtViewHolder, position: Int) {
        val artItem = items[position]
        with(holder.artItemBinding) {
            textViewTitle.text = artItem.title
            textViewObjectNumber.text = artItem.objectNumber
            textViewMaker.text = artItem.principalOrFirstMaker
            imageViewArt.load(artItem.imageUrl)
        }
    }

    override fun getItemCount(): Int = items.size

    fun setItems(artList: List<ArtViewData>) {
        this.items = artList
        notifyDataSetChanged() //TODO change to diffutil
    }

    class ArtViewHolder(val artItemBinding: ArtItemBinding) :
        RecyclerView.ViewHolder(artItemBinding.root)
}