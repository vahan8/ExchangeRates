package com.vahan.exchangerates.data.remote

import com.vahan.exchangerates.domain.model.Result

typealias OnResponse<T> = (Result<T>) -> Unit

