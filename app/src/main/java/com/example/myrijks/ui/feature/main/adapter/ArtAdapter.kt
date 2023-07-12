package com.example.myrijks.ui.feature.main.adapter

import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.myrijks.databinding.ItemArtBinding
import com.example.myrijks.databinding.ItemMakerBinding
import com.example.myrijks.domain.model.main.ArtEntity
import com.example.myrijks.domain.model.main.MakerEntity
import com.example.myrijks.ui.feature.main.model.ArtItemWrapper
import com.example.myrijks.ui.feature.main.model.ItemWrapper
import com.example.myrijks.ui.feature.main.model.MakerItemWrapper
import com.example.myrijks.ui.util.viewBinding

class ArtAdapter(private val itemClickListener: ItemClickListener) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val ART = 1
        private const val MAKER = 2
    }

    private var items = emptyList<ItemWrapper<*>>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            ART -> {
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

            MAKER -> {
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
        return when (items[position]) {
            is ArtItemWrapper -> ART
            is MakerItemWrapper -> MAKER
        }
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
        fun bind(artEntity: ArtEntity) {
            with(itemArtBinding) {
                root.setOnClickListener { itemClickListener.onItemClicked(artEntity.objectNumber) }
                textViewTitle.text = artEntity.title
                textViewObjectNumber.text = artEntity.objectNumber
                textViewMaker.text = artEntity.principalOrFirstMaker
                artEntity.imageUrl.let {
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
        fun bind(makerEntity: MakerEntity) {
            with(itemMakerBinding) {
                textViewMaker.text = makerEntity.maker
            }
        }
    }
}