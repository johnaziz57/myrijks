package com.example.myrijks.ui.feature.main.adapter

import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.myrijks.databinding.ItemArtBinding
import com.example.myrijks.databinding.ItemMakerBinding
import com.example.myrijks.ui.feature.main.model.ArtItemWrapper
import com.example.myrijks.ui.feature.main.model.ArtViewData
import com.example.myrijks.ui.feature.main.model.ItemWrapper
import com.example.myrijks.ui.feature.main.model.MakerItemWrapper
import com.example.myrijks.ui.feature.main.model.MakerViewData
import com.example.myrijks.ui.util.viewBinding

class ArtAdapter(private val itemClickListener: ItemClickListener) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items = emptyList<ItemWrapper<*>>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            ItemWrapper.ItemWrapperType.ART.ordinal -> {
                val itemBinding =
                    parent.viewBinding { layoutInflater, viewGroup, isAttachToParent ->
                        ItemArtBinding.inflate(
                            layoutInflater,
                            viewGroup,
                            isAttachToParent
                        )
                    }
                return ArtViewHolder(itemBinding, itemClickListener)
            }
            ItemWrapper.ItemWrapperType.MAKER.ordinal -> {
                val itemBinding =
                    parent.viewBinding { layoutInflater, viewGroup, isAttachToParent ->
                        ItemMakerBinding.inflate(
                            layoutInflater,
                            viewGroup,
                            isAttachToParent
                        )
                    }
                return MakerViewHolder(itemBinding)
            }
            else -> {
                throw IllegalArgumentException(
                    "Unable to create ViewHolder of type '%s'".format(
                        viewType
                    )
                )
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = items[position]) {
            is ArtItemWrapper -> {
                (holder as ArtViewHolder).bind(item.item)
            }
            is MakerItemWrapper -> {
                (holder as MakerViewHolder).bind(item.item)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return items[position].type.ordinal
    }

    override fun getItemCount(): Int = items.size

    fun setItems(items: List<ItemWrapper<*>>) {
        val result = DiffUtil.calculateDiff(ArtDiffUtilCallback(this.items, items), false)
        this.items = items
        result.dispatchUpdatesTo(this)
    }

    class ArtViewHolder(
        private val itemArtBinding: ItemArtBinding,
        private val itemClickListener: ItemClickListener
    ) :
        RecyclerView.ViewHolder(itemArtBinding.root) {
        fun bind(artViewData: ArtViewData) {
            with(itemArtBinding) {
                root.setOnClickListener { itemClickListener.onItemClicked(artViewData.objectNumber) }
                textViewTitle.text = artViewData.title
                textViewObjectNumber.text = artViewData.objectNumber
                textViewMaker.text = artViewData.principalOrFirstMaker
                artViewData.imageUrl.let {
                    if (it.isNullOrBlank()) {
                        imageViewArt.isVisible = false
                    } else {
                        imageViewArt.isVisible = true
                        imageViewArt.load(it)
                    }
                }
            }
        }
    }

    class MakerViewHolder(private val itemMakerBinding: ItemMakerBinding) :
        RecyclerView.ViewHolder(itemMakerBinding.root) {
        fun bind(makerViewData: MakerViewData) {
            with(itemMakerBinding) {
                textViewMaker.text = makerViewData.maker
            }
        }
    }
}