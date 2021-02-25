package com.apusart.cookit.ui.components

import android.content.Context
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import com.apusart.cookit.R
import com.apusart.cookit.tools.hideKeyboardFrom
import com.apusart.cookit.tools.showKeyboardFrom
import kotlinx.android.synthetic.main.top_bar.view.*

class TopBarWithSearch @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet,
    defStyle: Int = 0
) : LinearLayout(context, attrs, defStyle) {
    private val mView = LayoutInflater.from(context)
        .inflate(R.layout.top_bar, this, false)

    init {
        addView(mView)
        setOnLoupeClickListener {
            mView.search_input.requestFocusFromTouch()
            showKeyboardFrom(context, it)
        }
        setOnCloseClickListener {}
        setOnEnterClickListener {
            Toast.makeText(context, mView.search_input.text, Toast.LENGTH_SHORT).show()
            hideKeyboardFrom(context, it)
        }
    }


    fun setOnLoupeClickListener(f: (View) -> Unit) {
        mView.loupe_container.setOnClickListener {
            f(it)
            showInput()
        }
    }

    private fun showInput() {
        val params = mView.loupe_container.layoutParams as ConstraintLayout.LayoutParams
        params.horizontalBias = 0f
        mView.loupe_container.layoutParams = params
        mView.search_input.isVisible = true
        mView.clear_input.isVisible = true

        mView.loupe_container.isClickable = false
    }

    private fun hideInput() {
        val params = mView.loupe_container.layoutParams as ConstraintLayout.LayoutParams
        params.horizontalBias = 1f
        mView.loupe_container.layoutParams = params
        mView.search_input.isVisible = false
        mView.clear_input.isVisible = false
        mView.loupe_container.isClickable = true
    }

    fun setOnCloseClickListener(f: (View) -> Unit) {
        mView.clear_input.setOnClickListener(closeOrClear(f))
    }

    private fun closeOrClear(f: (View) -> Unit): (View) -> Unit {
        return {
            if (mView.search_input.text.isNotEmpty())
                mView.search_input.text.clear()
            else {
                hideInput()
                hideKeyboardFrom(context, mView)
                f(it)
            }
        }
    }

    fun setOnEnterClickListener(f: (View) -> Unit) {
        mView.search_input.setOnKeyListener { view, i, _ ->
            if (i == KeyEvent.KEYCODE_ENTER) {
                f(view)
                mView.search_input.clearFocus()
                return@setOnKeyListener true
            }
            return@setOnKeyListener false
        }
    }
}