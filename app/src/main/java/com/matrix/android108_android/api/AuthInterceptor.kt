package com.matrix.android108_android.api

import android.content.SharedPreferences
import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(val sharedPrefs: SharedPreferences): Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        val token = sharedPrefs.getString("access_token",null)
        Log.d("AuthInterceptor", "Token sent: $token")
        val newRequest = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer $token")
            .build()

        return chain.proceed(newRequest)
    }

}