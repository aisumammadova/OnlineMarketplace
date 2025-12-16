package com.matrix.android108_android.models

data class ProductRoot(
    val limit: Int,
    val products: List<Product>,
    val skip: Int,
    val total: Int
)