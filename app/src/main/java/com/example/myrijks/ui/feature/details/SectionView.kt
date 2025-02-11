package com.example.myrijks.ui.feature.details

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import com.example.myrijks.databinding.ViewSectionBinding
import com.example.myrijks.ui.util.viewBinding

class SectionView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {

    private val binding: ViewSectionBinding =
        viewBinding { layoutInflater, viewGroup, _ ->
            ViewSectionBinding.inflate(layoutInflater, viewGroup)
        }


    fun setSectionData(header: String, description: String) {
        with(binding) {
            composeViewContent.setContent {
                SectionView(header, description)
            }
        }
    }
}