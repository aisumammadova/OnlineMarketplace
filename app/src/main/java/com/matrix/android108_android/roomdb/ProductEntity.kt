package com.matrix.android108_android.roomdb

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ProductEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val price: Double,
    val imageUrl:String,
    var isLiked: Boolean
)
