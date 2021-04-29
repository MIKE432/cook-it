package com.apusart.cookit.ui.fragments.search

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.apusart.cookit.R
import com.apusart.cookit.ui.fragments.BaseFragment
import com.apusart.cookit.ui.main_activity.MainActivityViewModel
import kotlinx.android.synthetic.main.search.*

class SearchFragment: BaseFragment(R.layout.search) {

    private val viewModel: MainActivityViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
    }
    fun search(searchText: String) {
        search.text = searchText
    }

    private fun setupObservers() {
        viewModel.searchText.observe(viewLifecycleOwner, {
            search(it)
        })
    }
}