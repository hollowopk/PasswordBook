package com.example.passwordbook.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mychat.showLog

@Database(version = 1, entities = [Account::class])
abstract class AppDatabase : RoomDatabase() {

    abstract fun accountDao(): AccountDao

    companion object {

        private var instance: AppDatabase ? = null
        private val tag = "AppDatabase"

        @Synchronized
        fun getDatabase(context: Context): AppDatabase {

            instance?.let {
                return it
            }
            return Room.databaseBuilder(context.applicationContext,
                AppDatabase::class.java, "app_database")
                .build().apply {
                    instance = this
                }
        }

    }

}