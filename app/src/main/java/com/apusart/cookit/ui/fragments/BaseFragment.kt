package com.apusart.cookit.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.apusart.cookit.R
import com.apusart.cookit.ui.fragments.calendar.CalendarFragment
import com.apusart.cookit.ui.fragments.home.HomeFragment
import com.apusart.cookit.ui.fragments.my_recipes.MyRecipesFragment
import com.apusart.cookit.ui.fragments.search.SearchFragment
import com.apusart.cookit.ui.main_activity.MainActivity
import java.lang.IllegalArgumentException

open class BaseFragment(@LayoutRes val layout: Int) : Fragment() {
    open val title: String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(layout, container, false)
        view.background =
            ContextCompat.getDrawable(requireContext(), R.drawable.every_fragment_background)
        return view
    }

    override fun onResume() {
        super.onResume()
        if (requireActivity() is MainActivity)
            (requireActivity() as MainActivity).setTitle(title)
    }
}

fun <T : BaseFragment> createFragment(c: Class<T>): T {
    return when (c) {
        SearchFragment::class.java -> SearchFragment() as T
        CalendarFragment::class.java -> CalendarFragment() as T
        HomeFragment::class.java -> HomeFragment() as T
        MyRecipesFragment::class.java -> MyRecipesFragment() as T
        else -> throw IllegalArgumentException()
    }
}