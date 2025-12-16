package com.matrix.android108_android.roomdb

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [ProductEntity::class], version = 1)
abstract class Database: RoomDatabase() {

    abstract fun productDao(): ProductDao


}
