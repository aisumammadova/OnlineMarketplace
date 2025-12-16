package com.matrix.android108_android.mapper


import com.matrix.android108_android.Dto.ProductDto
import com.matrix.android108_android.models.Product

fun Product.toDto( isLiked: Boolean): ProductDto{

    return ProductDto(
        id = this.id,
        title = this.title,
        price = this.price,
        isLiked = isLiked,
        thumbnail = this.thumbnail
    )
}