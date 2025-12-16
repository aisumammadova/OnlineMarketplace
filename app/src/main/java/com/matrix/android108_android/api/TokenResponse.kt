package com.matrix.android108_android.api

data class TokenResponse(
    val accessToken : String,
    val refreshToken: String
)
data class RefreshRequest(val refreshToken: String)
