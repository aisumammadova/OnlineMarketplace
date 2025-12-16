package com.matrix.android108_android.roomdb

import android.content.Context
import androidx.room.Room

object DatabaseClient {

    lateinit var instance: Database

    fun init(context: Context) {

        instance = Room.databaseBuilder(
            context.applicationContext,
            Database::class.java,
            "fav_database"
        ).build()

    }

}