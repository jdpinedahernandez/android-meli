package com.juanpineda.data.server.result.error

import com.juanpineda.data.server.result.error.Failure.FeatureFailure
import com.juanpineda.data.server.result.error.ServerFailure.Companion.serverFailure
import retrofit2.HttpException
import java.net.ConnectException

/**
 * Base Class for handling errors/failures/exceptions.
 * Every feature specific failure should extend [FeatureFailure] class.
 */
sealed class Failure(val error: Exception? = null) {
    class NetworkConnection(error: Exception? = null) : Failure(error)
    abstract class FeatureFailure : Failure()
    object UnknownException : Failure()

    companion object {
        fun analyzeException(exception: Exception?) =
            when (exception) {
                is ConnectException -> NetworkConnection(exception)
                is HttpException -> serverFailure(exception)
                else -> UnknownException
            }
    }
}