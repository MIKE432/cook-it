package com.apusart.cookit.api

import com.apollographql.apollo.api.Error

data class Resource<out T>(val status: Status, val data: T?, val errors: List<Error>?) {
    enum class Status {
        SUCCESS,
        ERROR,
        PENDING
    }

    companion object {
        fun<T> success(data: T?): Resource<T> {
            return Resource(Status.SUCCESS, data, null)
        }

        fun<T> error(errors: List<Error>?, data: T? = null): Resource<T> {
            return Resource(Status.ERROR, data, errors)
        }

        fun<T> pending(data: T? = null): Resource<T> {
            return Resource(Status.PENDING, data, null)
        }
    }
}