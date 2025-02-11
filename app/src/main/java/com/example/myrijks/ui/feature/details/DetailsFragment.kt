package com.example.myrijks.ui.feature.details

import android.os.Bundle
import android.view.View
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import coil.load
import com.example.myrijks.R
import com.example.myrijks.databinding.FragmentDetailsBinding
import com.example.myrijks.ui.model.ResultStatus
import com.example.myrijks.ui.util.showSnackBar
import com.example.myrijks.ui.util.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment : Fragment(R.layout.fragment_details) {

    private val viewModel: DetailsViewModel by viewModels()
    private val binding by viewBinding(FragmentDetailsBinding::bind)
    private val args: DetailsFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadData()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.resultStatusLiveData.observe(viewLifecycleOwner) {
            when (it) {
                ResultStatus.LOADING, ResultStatus.DEFAULT -> {
                    binding.progressBar.isVisible = true
                }

                ResultStatus.ERROR -> {
                    binding.progressBar.isVisible = false
                }

                ResultStatus.SUCCESS -> {
                    binding.progressBar.isVisible = false
                    binding.scrollViewDetailsContainer.isVisible = true
                }

                else -> {}
            }
        }
        viewModel.errorLiveData.observe(viewLifecycleOwner) {
            it?.showSnackBar(
                this.requireView()
            ) { loadData() }
        }
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

                composeVerticalSections.setContent {
                    Column(Modifier
                        .fillMaxWidth()
                        .padding(4.dp)) {
                        if (viewData.description.isNotBlank()) {
                            SectionView("Description", viewData.description)
                            SectionDividerView()
                        }

                        if (viewData.principalMakers.isNotEmpty()) {
                            SectionView(
                                "Maker(s)",
                                viewData.principalMakers.joinToString("\n")
                            )
                            SectionDividerView()
                        }

                        if (viewData.physicalMedium.isNotBlank()) {
                            SectionView(
                                "Physical Medium",
                                viewData.physicalMedium
                            )
                            SectionDividerView()
                        }

                        if (viewData.dimensionDescription.isNotBlank()) {
                            SectionView(
                                "Dimensions",
                                viewData.dimensionDescription
                            )
                            SectionDividerView()
                        }

                        if (viewData.dating.isNotBlank()) {
                            SectionView("Dating", viewData.dating)
                            SectionDividerView()
                        }

                        if (viewData.productionPlacesDescription.isNotBlank()) {
                            SectionView(
                                "Production Place(s)",
                                viewData.productionPlacesDescription
                            )
                            SectionDividerView()
                        }
                    }
                }
            }
        }
    }

    private fun loadData() {
        viewModel.getArtObjectDetails(args.artObjectId)
    }
}