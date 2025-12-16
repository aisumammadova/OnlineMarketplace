package com.matrix.android108_android.Dto




data class ProductDto(

    val id: Int,
    val title: String,
    val price: Double,
    val thumbnail: String,
    var isLiked: Boolean = false

)
