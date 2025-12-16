package com.matrix.android108_android.api

import android.content.SharedPreferences
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

class TokenAuthenticator(private val sharedPreferences: SharedPreferences): Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {

        synchronized(this) {

            val currentAccessToken = sharedPreferences.getString("access_token", null)
            val refreshToken = sharedPreferences.getString("refresh_token", null) ?: return null


            if (currentAccessToken != response.request.header("Authorization")?.removePrefix("Bearer ")) {
                return response.request.newBuilder()
                    .header("Authorization", "Bearer $currentAccessToken")
                    .build()
            }


            val newTokensResponse = RetrofitClient.retrofit.create(AuthService::class.java).refreshToken(
                RefreshRequest(refreshToken)).execute()
            if (!newTokensResponse.isSuccessful) {
                return null
            }

            val newTokens = newTokensResponse.body() ?: return null


            sharedPreferences.edit()
                .putString("access_token", newTokensResponse.body()?.accessToken)
                .putString("refresh_token", newTokensResponse.body()?.refreshToken)
                .apply()

            return response.request.newBuilder()
                .header("Authorization", "Bearer ${newTokensResponse.body()?.accessToken}")
                .build()
        }
    }
    }