package com.apusart.cookit.ui.main_activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.apusart.cookit.R
import com.apusart.cookit.ui.components.BottomNavBar.Companion.ADD_RECIPE
import com.apusart.cookit.ui.components.BottomNavBar.Companion.CALENDAR
import com.apusart.cookit.ui.components.BottomNavBar.Companion.HOME
import com.apusart.cookit.ui.components.BottomNavBar.Companion.MY_RECIPES
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    val viewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupObservers()
        setupOnClickListeners()
    }

    private fun setupOnClickListeners() {
        bottom_bar.setOnAddRecipeClickListener(getNavigationOnClickFunction(ADD_RECIPE) {

        })

        bottom_bar.setOnCalendarClickListener(getNavigationOnClickFunction(CALENDAR) {

        })

        bottom_bar.setOnMyRecipesClickListener(getNavigationOnClickFunction(MY_RECIPES) {

        })

        bottom_bar.setOnHomeClickListener(getNavigationOnClickFunction(HOME) {

        })
    }

    private fun setupObservers() {
        viewModel.currentItem.observe(this) {
            bottom_bar.selectedItem = it
        }
    }

    private fun getNavigationOnClickFunction(clickedItem: Int, f: (View) -> Unit): (View) -> Unit {
        return {
            f(it)
            viewModel.currentItem.value = clickedItem
        }
    }
}