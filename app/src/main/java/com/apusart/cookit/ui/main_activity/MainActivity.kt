package com.apusart.cookit.ui.main_activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.navigation.findNavController
import com.apusart.cookit.R
import com.apusart.cookit.extensions.handleIt
import com.apusart.cookit.tools.hideKeyboardFrom
import com.apusart.cookit.tools.showKeyboardFrom
import com.apusart.cookit.ui.components.BottomNavBar.Companion.CALENDAR
import com.apusart.cookit.ui.components.BottomNavBar.Companion.HOME
import com.apusart.cookit.ui.components.BottomNavBar.Companion.MY_RECIPES
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main) {

    companion object {
        private const val SEARCH_FRAGMENT_LABEL = "SearchFragment"
        private const val HOME_FRAGMENT_LABEL = "HomeFragment"
        private const val MY_RECIPES_FRAGMENT_LABEL = "MyRecipesFragment"
        private const val CALENDAR_FRAGMENT_LABEL = "CalendarFragment"
    }

    private val viewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupObservers()
        setupOnClickListeners()
        viewModel.recipes.observe(this, { res ->
            res.handleIt(
                onSuccess = {
                },
                onPending = {

                },
                onError = { errors, data ->

                }
            )
        })
    }

    override fun onBackPressed() {
        val navController = findNavController(R.id.fragment_container)
        if (navController.currentDestination?.label == SEARCH_FRAGMENT_LABEL) {
            header.triggerOnCloseEvent()
        } else {
            super.onBackPressed()
        }

    }

    private fun setupOnClickListeners() {
        val navController = findNavController(R.id.fragment_container)

        bottom_bar.setOnCalendarClickListener(getNavigationOnClickFunction(CALENDAR) {
            navController.navigate(R.id.calendar_fragment)
        })

        bottom_bar.setOnMyRecipesClickListener(getNavigationOnClickFunction(MY_RECIPES) {
            navController.navigate(R.id.my_recipes_fragment)
        })

        bottom_bar.setOnHomeClickListener(getNavigationOnClickFunction(HOME) {
            navController.navigate(R.id.home_fragment)
        })

        header.setOnLoupeClickListener {
            hideBottomBar()
            showKeyboardFrom(this, it)
            navController.navigate(R.id.search_fragment)
        }

        header.setOnCloseClickListener {
            super.onBackPressed()
            showBottomBar()
        }

        header.setOnEnterClickListener { v, str ->
            viewModel.searchText.value = str
            hideKeyboardFrom(this, v)
        }
    }

    private fun setupObservers() {
        val navController = findNavController(R.id.fragment_container)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            viewModel.currentItem.value =
                when (destination.label) {
                    HOME_FRAGMENT_LABEL -> HOME
                    MY_RECIPES_FRAGMENT_LABEL -> MY_RECIPES
                    CALENDAR_FRAGMENT_LABEL -> CALENDAR
                    else -> -1
                }

        }
        viewModel.currentItem.observe(this) {
            if (it != -1)
                bottom_bar.selectedItem = it
        }

        viewModel.isSearchOpen.observe(this) {
            if (it)
                root_view.transitionToEnd()
            else
                root_view.transitionToStart()
        }
    }

    private fun getNavigationOnClickFunction(clickedItem: Int, f: (View) -> Unit): (View) -> Unit {
        return {
            f(it)
            viewModel.currentItem.value = clickedItem
        }
    }

    private fun hideBottomBar() {
        viewModel.isSearchOpen.value = true
    }

    private fun showBottomBar() {
        viewModel.isSearchOpen.value = false
    }

    fun setTitle(title: String) {
        header.text = title
    }
}