package com.matrix.android108_android.api

import android.content.Context
import android.content.SharedPreferences
import com.matrix.android108_android.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private lateinit var prefs: SharedPreferences

    fun init(context: Context){
        prefs = context.getSharedPreferences("myPref", Context.MODE_PRIVATE)
    }

    val retrofit: Retrofit by lazy {

        val loggingInterceptor = HttpLoggingInterceptor()
        if(BuildConfig.DEBUG){
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        }else{
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.NONE)
        }

        val  okHttpClient = OkHttpClient
            .Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(AuthInterceptor(prefs))
            .authenticator(TokenAuthenticator(prefs))
            .build()

        Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl("https://dummyjson.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    }



    var accessToken: String =""
    var refreshToken : String =""
}