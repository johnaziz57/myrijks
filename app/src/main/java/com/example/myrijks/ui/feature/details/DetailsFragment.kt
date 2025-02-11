package com.example.myrijks.ui.feature.details

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import coil.compose.AsyncImage
import com.example.myrijks.R
import com.example.myrijks.databinding.FragmentDetailsBinding
import com.example.myrijks.ui.model.ResultStatus
import com.example.myrijks.ui.theme.MyRijksTypography
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
                    showLoadingIndicator()
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
            binding.composeContent.setContent {
                DetailsView(viewData)
            }
        }
    }

    private fun loadData() {
        viewModel.getArtObjectDetails(args.artObjectId)
    }

    private fun showLoadingIndicator() {
        binding.composeContent.setContent {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                CircularProgressIndicator()
            }
        }
    }
}