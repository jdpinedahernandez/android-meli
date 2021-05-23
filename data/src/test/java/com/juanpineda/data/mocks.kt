package com.juanpineda.data

import com.google.gson.Gson
import com.juanpineda.data.server.result.error.entity.ErrorServiceResponse
import com.juanpineda.data.server.result.error.entity.ErrorType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.HttpException
import retrofit2.Response

private const val CONTENT_TYPE = "application/json"

fun buildHttpExceptionMock(errorType: ErrorType, status: Int) =
    ErrorServiceResponse(
        error = errorType.name,
        status = status
    ).run {
        HttpException(
            Response.error<Any>(
                status, Gson().toJson(this)
                    .toResponseBody(CONTENT_TYPE.toMediaTypeOrNull())
            )
        )
    }