package com.juanpineda.data.server.result.error

import com.google.gson.Gson
import com.juanpineda.data.server.result.error.ErrorType.INVALID_QUERY
import retrofit2.HttpException

class ServerFailure : Failure.FeatureFailure() {

    object InvalidValueForQueryParameter : Failure.FeatureFailure()

    companion object {
        fun serverFailure(httpException: HttpException) =
            httpException.response()?.errorBody()?.let {
                Gson().getAdapter(ErrorServiceResponse::class.java).fromJson(it.string())
                    ?.run(::mapErrorToServerFailure) ?: UnknownException
            } ?: UnknownException

        private fun mapErrorToServerFailure(errorServiceResponse: ErrorServiceResponse) =
            with(errorServiceResponse) {
                if (status == BAD_REQUEST && error == INVALID_QUERY.name) InvalidValueForQueryParameter
                else UnknownException
            }
    }
}