package com.apusart.cookit.ui.fragments.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.apusart.cookit.R
import com.apusart.cookit.ui.fragments.BaseFragment
import com.apusart.cookit.ui.main_activity.MainActivityViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment(R.layout.home_fragment) {
    override val title: String = "Home"
    val viewModel: MainActivityViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getRecipes()
    }
}