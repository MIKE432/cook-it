package com.apusart.cookit.extensions

import com.apollographql.apollo.api.Error
import com.apusart.cookit.api.Resource

fun<T> Resource<T>.handleIt(
    onSuccess: (data: T?) -> Unit = { _ -> },
    onPending: (data: T?) -> Unit = { _ -> },
    onError: (errors: List<Error>?, data: T?) -> Unit = { _, _ -> }
) {
    when(status) {
        Resource.Status.PENDING -> onPending(data)
        Resource.Status.SUCCESS -> onSuccess(data)
        Resource.Status.ERROR -> onError(errors, data)
    }
}