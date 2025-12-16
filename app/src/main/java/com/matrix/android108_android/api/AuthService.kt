package com.matrix.android108_android.api

import com.matrix.android108_android.models.User
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthService {

    @POST ("auth/login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<LoginResponse>

    @GET("auth/me")
    suspend fun getUserProfile(): Response<User>

    @POST("auth/refresh")
    fun refreshToken(@Body request: RefreshRequest): Call<TokenResponse>

}