package com.furniture.data.network.responseUtil

sealed class AppError {
    data class ApiError(val statusCode: Int, val message: String) : AppError()
    data class ApiUnauthorized(val message: String) : AppError()
    data class ApiAccountBlock(val message: String) : AppError()
    data class ApiAccountRuleChanged(val message: String) : AppError()
    data class ApiFailure(val message: String) : AppError()
    data class ApiPermium(val message: String) : AppError()
    data class ApiAlertPremium(val message: String): AppError()

}