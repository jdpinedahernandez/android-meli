package com.juanpineda.result

import com.juanpineda.result.Failure.FeatureFailure
import java.net.ConnectException

/**
 * Base Class for handling errors/failures/exceptions.
 * Every feature specific failure should extend [FeatureFailure] class.
 */
sealed class Failure(val error: Exception? = null) {
    object UnknownException : Failure()
    class NetworkConnection(error: Exception? = null) : Failure(error)

    /** * Extend this class for feature specific failures.*/
    abstract class FeatureFailure(error: Exception?) : Failure(error)

    companion object {
        fun analyzeException(exception: Exception?): Failure {
            // TODO Create cases
            return when (exception) {
                is ConnectException -> NetworkConnection(exception)
                else -> UnknownException
            }
        }
    }
}