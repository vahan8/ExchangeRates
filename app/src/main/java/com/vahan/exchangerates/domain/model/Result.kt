package com.vahan.exchangerates.domain.model

sealed class Result<out T : Any> {
    data class Success<out T : Any>(val data: T) : Result<T>()
    //I didn't pay much attention to error handling, in real life application I would pass proper error message to Error object
    object Error : Result<Nothing>()
}