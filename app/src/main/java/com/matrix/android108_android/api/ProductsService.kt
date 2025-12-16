package com.matrix.android108_android.api

import com.matrix.android108_android.models.Product
import com.matrix.android108_android.models.ProductRoot
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductsService {

    @GET("auth/products")
    suspend fun getList(): Response<ProductRoot>



    @GET("auth/products/category-list")
    suspend fun getCategoryList( ): List<String>

    @GET("auth/products/category/{category}")
    suspend fun getProductsByCategory(@Path("category") category:String, ): Response<ProductRoot>




}