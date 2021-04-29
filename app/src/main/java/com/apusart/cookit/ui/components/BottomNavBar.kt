package com.apusart.cookit.ui.components

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.apusart.cookit.R
import com.apusart.cookit.tools.setDrawableTint
import kotlinx.android.synthetic.main.bottom_navigation.view.*

class BottomNavBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet,
    defStyle: Int = 0
) : LinearLayout(context, attrs, defStyle) {

    companion object {
        const val HOME = 0
        const val MY_RECIPES = 1
        const val CALENDAR = 2
        const val SELECTED_ICON_TINT_COLOR = R.color.black
        const val NOT_SELECTED_ICON_TINT_COLOR = R.color.primary_50
    }

    private val mView = LayoutInflater.from(context)
        .inflate(R.layout.bottom_navigation, this, false)

    private var mHeight = 0

    var selectedItem = 0
        set(value) {
            deselectCurrentItem()
            field = value
            selectCurrentItem()
        }

    init {
        addView(mView)
    }

    fun setOnHomeClickListener(f: (View) -> Unit) {
        mView.nav_home.setOnClickListener {
            if (selectedItem != HOME)
                f(it)
        }
    }

    fun setOnMyRecipesClickListener(f: (View) -> Unit) {
        mView.nav_my_recipes.setOnClickListener {
            if (selectedItem != MY_RECIPES)
                f(it)
        }
    }

    fun setOnCalendarClickListener(f: (View) -> Unit) {
        mView.nav_calendar.setOnClickListener {
            if (selectedItem != CALENDAR)
                f(it)
        }
    }

    private fun selectCurrentItem() {
        when (selectedItem) {
            0 -> animateSelect(mView.nav_home)
            1 -> animateSelect(mView.nav_my_recipes)
            2 -> animateSelect(mView.nav_calendar)
        }
    }

    private fun deselectCurrentItem() {
        when (selectedItem) {
            0 -> animateDeselect(mView.nav_home)
            1 -> animateDeselect(mView.nav_my_recipes)
            2 -> animateDeselect(mView.nav_calendar)
        }
    }

    private fun animateDeselect(view: View) {
        //maybe some animation in the future
        view.background = null
        changeDeselectedItem()
    }

    private fun animateSelect(view: View) {
        //maybe some animation in the future
        view.background = ContextCompat.getDrawable(context, R.drawable.selected_nav_item)
        changeSelectedItem()
    }

    private fun changeDeselectedItem() {
        when (selectedItem) {
            0 -> {
                mView.nav_home_text.isVisible = false
                mView.nav_home_image.setDrawableTint(NOT_SELECTED_ICON_TINT_COLOR)
            }
            1 -> {
                mView.nav_my_recipes_text.isVisible = false
                mView.nav_my_recipes_image.setDrawableTint(NOT_SELECTED_ICON_TINT_COLOR)
            }
            2 -> {
                mView.nav_calendar_text.isVisible = false
                mView.nav_calendar_image.setDrawableTint(NOT_SELECTED_ICON_TINT_COLOR)
            }
        }
    }

    private fun changeSelectedItem() {
        when (selectedItem) {
            0 -> {
                mView.nav_home_text.isVisible = true
                mView.nav_home_image.setDrawableTint(SELECTED_ICON_TINT_COLOR)
            }
            1 -> {
                mView.nav_my_recipes_text.isVisible = true
                mView.nav_my_recipes_image.setDrawableTint(SELECTED_ICON_TINT_COLOR)
            }
            2 -> {
                mView.nav_calendar_text.isVisible = true
                mView.nav_calendar_image.setDrawableTint(SELECTED_ICON_TINT_COLOR)
            }
        }
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b)

        mHeight = b - t
    }
}