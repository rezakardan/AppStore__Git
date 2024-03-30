package com.example.appstore.utils.network

import com.example.appstore.data.model.ErrorResponse
import com.google.gson.Gson
import retrofit2.Response

open class NetworkResponse<T>(private val response: Response<T>) {


    open fun generateResponse(): NetworkRequest<T> {


        return when {

            response.code() == 402 -> NetworkRequest.Error("You Are Not Authorized")

            response.code() == 422 -> {


                var messageError = ""

                if (response.errorBody() != null) {

                    val errorResponse = Gson().fromJson(
                        response.errorBody()?.charStream(),
                        ErrorResponse::class.java
                    )

                    val errors = errorResponse.errors

                    errors?.forEach { (field, fieldError) ->


                        messageError = fieldError.joinToString()


                    }

                }


                NetworkRequest.Error(messageError)


            }


            response.code() == 500 -> NetworkRequest.Error("Try Again")

            response.isSuccessful -> NetworkRequest.Success(response.body()!!)


            else -> NetworkRequest.Error(response.message())

        }


    }


}