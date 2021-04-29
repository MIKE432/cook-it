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
import androidx.core.widget.doOnTextChanged
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
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
    private var onCloseEventListener: ((View) -> Unit)? = null

    private fun isOpen(): Boolean {
        return mView.header_title.isVisible.not()
    }

    var text = ""
        set(value) {
            header_title.text = value
            field = value
        }

    init {
        addView(mView)
        setOnLoupeClickListener {
            mView.search_input.requestFocusFromTouch()
            showKeyboardFrom(context, it)
        }

        setOnCloseClickListener {}
        setOnEnterClickListener { v, text ->
            Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
            hideKeyboardFrom(context, v)
        }
    }

    fun triggerOnCloseEvent(): Boolean {
        if (isOpen()) {
            onCloseEventListener?.invoke(mView)
        }

        return !isOpen()
    }

    fun setOnLoupeClickListener(f: (View) -> Unit) {
        mView.loupe_container.setOnClickListener {
            f(it)
            showKeyboardFrom(context, mView.search_input)
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
        mView.header_title.isVisible = false
    }

    private fun hideInput() {
        val params = mView.loupe_container.layoutParams as ConstraintLayout.LayoutParams
        params.horizontalBias = 1f
        mView.header_title.isVisible = true
        mView.loupe_container.layoutParams = params
        mView.search_input.isVisible = false
        mView.clear_input.isVisible = false
        mView.loupe_container.isClickable = true
    }

    fun setOnCloseClickListener(f: (View) -> Unit) {
        onCloseEventListener = closeOrClear(f)
        mView.clear_input.setOnClickListener(onCloseEventListener)
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

    fun setOnEnterClickListener(f: (View, String) -> Unit) {
        mView.search_input.setOnKeyListener { view, i, _ ->
            if (i == KeyEvent.KEYCODE_ENTER) {
                f(view, mView.search_input.text.toString())
                mView.search_input.clearFocus()
                return@setOnKeyListener true
            }
            return@setOnKeyListener false
        }
    }

    fun getSearchText(): String {
        return mView.search_input.text.toString()
    }

    fun setSearchText(text: String) {
        return mView.search_input.setText(text)
    }

    fun clearInput() {
        mView.search_input.text.clear()
    }
}
