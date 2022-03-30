package com.talktomii.data.network.responseUtil


/**
 * A generic class that holds a value with its loading status.
 * @param <T>
</T> */
data class Resource<out T>(
    val status: Status,
    val data: T?,
    val error: AppError?,
    val message: String? = null
) {
    companion object {
        fun <T> success(data: T? = null, message: String? = null): Resource<T> {
            return Resource(Status.SUCCESS, data, null, message = message)
        }

        fun <T> error(error: AppError): Resource<T> {
            return Resource(Status.ERROR, null, error)
        }

        fun <T> loading(): Resource<T> {
            return Resource(Status.LOADING, null, null)
        }
    }
}