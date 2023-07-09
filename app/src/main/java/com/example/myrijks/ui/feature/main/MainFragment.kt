package com.example.myrijks.ui.feature.main

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.myrijks.R
import com.example.myrijks.databinding.FragmentMainBinding
import com.example.myrijks.ui.feature.main.adapter.ArtAdapter
import com.example.myrijks.ui.feature.main.components.ArtList
import com.example.myrijks.ui.model.ResultStatus
import com.example.myrijks.ui.theme.AppTheme
import com.example.myrijks.ui.util.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment(R.layout.fragment_main) {

    private val viewModel: MainViewModel by viewModels()
    private val binding by viewBinding(FragmentMainBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.loadNextCollection()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val adapter =
            ArtAdapter { artObjectId ->
                val action = MainFragmentDirections.actionMainFragmentToDetailsFragment(artObjectId)
                findNavController().navigate(action)
            }

        viewModel.collectionLiveData.observe(viewLifecycleOwner) {
            binding.composeViewResults.isVisible = true
            binding.composeViewResults.setContent {
                AppTheme {
                    ArtList(items = it)
                }
            }
        }
        viewModel.resultStatusLiveData.observe(viewLifecycleOwner) {
            when (it) {
                ResultStatus.ERROR -> {
                    Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show()
                    binding.progressBar.isVisible = false
                }

                ResultStatus.LOADING, ResultStatus.DEFAULT -> {
                    binding.progressBar.isVisible = true
                }

                else -> {
                    binding.progressBar.isVisible = false
                }
            }
        }
    }

}