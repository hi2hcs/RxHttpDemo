package com.example.myapplication.network

data class Response<T>(
    var code: Int = -1,
    var msg: String? = "",
    var message: String? = "",
    var data: T?
)
