package com.example.binviewapp.storage

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.binviewapp.storage.dao.BinEntityDao
import com.example.binviewapp.storage.entity.BinEntity

@Database(
    entities = [BinEntity::class],
    version = 1
)
abstract class AppDatabase: RoomDatabase() {

    companion object{
        private var appDatabase: AppDatabase? = null
        fun getDatabase(context: Context): AppDatabase{
            if(appDatabase != null){
                return appDatabase!!
            }
            appDatabase = Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "bin-view-database").build()
            return appDatabase!!
        }
    }

    abstract fun itemEntityDao(): BinEntityDao
}