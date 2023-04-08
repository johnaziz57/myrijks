package com.example.myrijks.ui.feature.details

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.myrijks.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment : Fragment(R.layout.fragment_details) {

    private val viewModel: DetailsViewModel by viewModels()
}