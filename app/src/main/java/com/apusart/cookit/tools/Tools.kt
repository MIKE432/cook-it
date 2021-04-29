package com.apusart.cookit.tools

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import androidx.annotation.IdRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Operation
import com.apollographql.apollo.api.Query
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.coroutines.await
import com.apusart.cookit.api.Resource


class Tools {}

fun ImageView.setDrawableTint(color: Int) {
    this.setColorFilter(ContextCompat.getColor(context, color))
}

fun hideKeyboardFrom(context: Context, view: View) {
    val imm = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}

fun showKeyboardFrom(context: Context, view: View) {
    val imm = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.showSoftInput(view, 0)
}

suspend inline fun <D : Operation.Data, T, V : Operation.Variables, reified X : Query<D, T, V>> performRequest(
    apolloClient: ApolloClient,
    clazz: Class<X>
): Resource<T> {
    val resource = apolloClient.query(clazz.newInstance()).await()
    return resolveRequest(resource)
}


fun <T> resolveRequest(response: Response<out T>): Resource<T> {
    if (response.hasErrors())
        return Resource.error(response.errors, response.data)
    return Resource.success(response.data)
}