package com.talktomii.data.network.responseUtil

import org.json.JSONObject
import retrofit2.Response

object ApiUtils {
    private fun getErrorMessage(errorJson: String?): String {
        if (errorJson.isNullOrBlank()) {
            return ""
        }

        return try {
            val errorJsonObject = JSONObject(errorJson)
            errorJsonObject.getString("message")
        } catch (exception: Exception) {
            ""
        }
    }

    fun getError(statusCode: Int, errorJson: String?): AppError {
        val message = getErrorMessage(errorJson)
        return when (statusCode) {
            401 -> {
                AppError.ApiUnauthorized(message)
            }
            402 -> {
                AppError.ApiAccountBlock(message)
            }
            403 -> {
                AppError.ApiAccountRuleChanged(message)
            }
            400 -> {
                AppError.ApiPermium(message)
            }
            404 -> {
                AppError.ApiAlertPremium(message)
            }
            200 -> {
                AppError.ApiAlertPremium(errorJson ?: "")
            }
            else -> {
                AppError.ApiError(statusCode, message)
            }
        }
    }

    fun failure(throwable: Throwable): AppError {
        return AppError.ApiFailure(throwable.localizedMessage ?: "")
    }


    fun imageToRequestBodyKey(parameterName: String, fileName: String): String =
        "$parameterName\"; filename=\"$fileName"
}

fun <T> Response<T>.getAppError(): AppError {
    return ApiUtils.getError(code(), errorBody()?.string())
}