package com.example.dietapp.api

import com.example.dietapp.api.exceptions.ApiException
import com.example.dietapp.api.exceptions.BadRequestException
import com.example.dietapp.api.exceptions.InternalServerErrorException
import com.example.dietapp.api.exceptions.NotAuthorizedException
import retrofit2.Response
import javax.inject.Inject

class ApiRequestHandler @Inject constructor() {
    suspend fun <T> executeRequest(request: suspend () -> Response<T>): ApiResponse<T> {
        val response = request.invoke()
        return if (response.isSuccessful)
            ApiResponse(data = response.body(), isSuccessful = true)
        else
            throw when (response.code()) {
                401 -> NotAuthorizedException()
                500 -> InternalServerErrorException()
                400 -> BadRequestException()
                else -> ApiException()
            }
    }

    suspend fun <T, P> executeRequest(
        request: suspend (P) -> Response<T>,
        param: P
    ): ApiResponse<T> {
        val response = request.invoke(param)

        return if (response.isSuccessful)
            ApiResponse(data = response.body(), isSuccessful = true)
        else
            throw when (response.code()) {
                401 -> NotAuthorizedException()
                500 -> InternalServerErrorException()
                400 -> BadRequestException()
                else -> ApiException()
            }
    }
}