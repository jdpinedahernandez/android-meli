package com.juanpineda.data.server.result.error.entity

data class ErrorServiceResponse (
    val error: String? = null,
    val message: String? = null,
    val status: Int? = null
)