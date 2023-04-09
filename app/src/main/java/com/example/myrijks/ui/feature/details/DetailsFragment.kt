package com.example.myrijks.ui.feature.details

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import coil.load
import com.example.myrijks.R
import com.example.myrijks.databinding.FragmentDetailsBinding
import com.example.myrijks.ui.util.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment : Fragment(R.layout.fragment_details) {

    private val viewModel: DetailsViewModel by viewModels()
    private val binding by viewBinding(FragmentDetailsBinding::bind)
    private val args: DetailsFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getArtObjectDetails(args.artObjectId)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.artDetailsLiveData.observe(viewLifecycleOwner) { viewData ->
            with(binding) {
                viewData.imageUrl.let { imageUrl ->
                    if (imageUrl.isNullOrBlank()) {
                        imageViewArt.isVisible = false
                    } else {
                        imageViewArt.isVisible = true
                        imageViewArt.load(imageUrl)
                    }
                }
                textViewTitle.text = viewData.title
                textViewSubtitle.text = viewData.subTitle
                textViewScLabelLine.text = viewData.scLabelLine

                fun createSection(context: Context, header: String, description: String): View {
                    val sectionView = SectionView(context)
                    sectionView.layoutParams = LinearLayoutCompat.LayoutParams(
                        LinearLayoutCompat.LayoutParams.MATCH_PARENT,
                        LinearLayoutCompat.LayoutParams.WRAP_CONTENT
                    )
                    sectionView.setSectionData(header, description)
                    return sectionView
                }

                fun addSection(context: Context, header: String, description: String) {
                    linearLayoutSections.addView(createSection(context, header, description))
                }

                if (viewData.description.isNotBlank()) {
                    addSection(view.context, "Description", viewData.description)
                }

                if (viewData.principalMakers.isNotEmpty()) {
                    addSection(
                        view.context,
                        "Maker(s)",
                        viewData.principalMakers.joinToString("\n")
                    )
                }

                if (viewData.physicalMedium.isNotBlank()) {
                    addSection(view.context, "Physical Medium", viewData.physicalMedium)
                }

                if (viewData.dimensionDescription.isNotBlank()) {
                    addSection(view.context, "Dimensions", viewData.dimensionDescription)
                }

                if (viewData.dating.isNotBlank()) {
                    addSection(view.context, "Dating", viewData.dating)
                }

                if (viewData.productionPlacesDescription.isNotBlank()) {
                    addSection(
                        view.context,
                        "Production Place(s)",
                        viewData.productionPlacesDescription
                    )
                }
            }
        }
    }
}