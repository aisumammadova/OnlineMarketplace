package com.matrix.android108_android.api

data class LoginRequest(


    val username: String,
    val password: String,
    val expiresInMins:Int=60
)
