package com.apusart.cookit.ui.fragments.search

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.apusart.cookit.R
import com.apusart.cookit.extensions.handleIt
import com.apusart.cookit.ui.fragments.BaseFragment
import com.apusart.cookit.ui.main_activity.MainActivityViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.search.*

@AndroidEntryPoint
class SearchFragment: BaseFragment(R.layout.search) {

    private val viewModel: MainActivityViewModel by activityViewModels()
    private val searchFragmentViewModel: SearchFragmentViewModel by viewModels()
    private lateinit var recipeListAdapter: RecipeListAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
        setupProps()
    }

    fun search(searchText: String) {
        searchFragmentViewModel.getRecipesByName(viewModel.searchText.value.toString())
    }

    fun setupProps() {
        recipeListAdapter = RecipeListAdapter()
        recipes_list.apply {
            adapter = recipeListAdapter
        }
    }

    private fun setupObservers() {
        viewModel.searchText.observe(viewLifecycleOwner, {
            search(it)
        })

        searchFragmentViewModel.recipes.observe(viewLifecycleOwner, { res ->
            res.handleIt(
                onSuccess = { data ->
                    recipeListAdapter.submitList(data?.scopedRecipes)
                }
            )
        })
    }
}