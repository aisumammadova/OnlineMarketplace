package com.matrix.android108_android.roomdb

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.matrix.android108_android.models.Product

@Dao
interface ProductDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun likeProduct(product: ProductEntity)

    @Delete
    suspend fun unlikeProduct(product: ProductEntity)

    @Query("SELECT * FROM ProductEntity")
    suspend fun getLikedProducts():List<ProductEntity>



}