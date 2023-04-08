package com.example.myrijks.ui.feature.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myrijks.R
import com.example.myrijks.databinding.FragmentMainBinding
import com.example.myrijks.ui.feature.main.adapter.ArtAdapter
import com.example.myrijks.ui.util.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment(R.layout.fragment_main) {

    companion object {
        fun newInstance() = MainFragment()
    }

    private val viewModel: MainViewModel by viewModels()
    private val binding by viewBinding(FragmentMainBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.loadNextCollection()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = ArtAdapter()
        binding.recyclerViewCollection.adapter = adapter
        binding.recyclerViewCollection.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val linearLayoutManager =
                    recyclerView.layoutManager as? LinearLayoutManager ?: return
                val lastVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition()
                if (lastVisibleItemPosition > 0 && lastVisibleItemPosition + 5 >= linearLayoutManager.itemCount) {
                    viewModel.loadNextCollection()
                }

            }
        })
        viewModel.collectionLiveData.observe(viewLifecycleOwner) {
            adapter.setItems(it)
        }
    }

}