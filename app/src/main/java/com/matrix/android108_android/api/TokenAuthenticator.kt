package com.matrix.android108_android.api

import android.content.SharedPreferences
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

class TokenAuthenticator(private val sharedPreferences: SharedPreferences): Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {

        synchronized(this) { // Prevent multiple refresh calls

            val currentAccessToken = sharedPreferences.getString("access_token", null)
            val refreshToken = sharedPreferences.getString("refresh_token", null) ?: return null

            // If the access token changed since the first failed request, retry with new token
            if (currentAccessToken != response.request.header("Authorization")?.removePrefix("Bearer ")) {
                return response.request.newBuilder()
                    .header("Authorization", "Bearer $currentAccessToken")
                    .build()
            }

            // Fetch new tokens synchronously
            val newTokensResponse = RetrofitClient.retrofit.create(AuthService::class.java).refreshToken(
                RefreshRequest(refreshToken)).execute()
            if (!newTokensResponse.isSuccessful) {
                return null // Refresh failed, trigger logout
            }

            val newTokens = newTokensResponse.body() ?: return null

            // Save new tokens
            sharedPreferences.edit()
                .putString("access_token", newTokensResponse.body()?.accessToken)
                .putString("refresh_token", newTokensResponse.body()?.refreshToken)
                .apply()

            // Retry the original request with new token
            return response.request.newBuilder()
                .header("Authorization", "Bearer ${newTokensResponse.body()?.accessToken}")
                .build()
        }
    }
    }