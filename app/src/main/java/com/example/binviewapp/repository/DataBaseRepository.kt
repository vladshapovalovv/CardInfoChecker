package com.example.binviewapp.repository

import com.example.binviewapp.storage.AppDatabase
import com.example.binviewapp.storage.entity.BinEntity
import kotlinx.coroutines.flow.Flow

class DataBaseRepository   (
    private val appDatabase: AppDatabase
) {

    suspend fun insertItem(itemEntity: BinEntity) {
        appDatabase.itemEntityDao().insert(itemEntity)
    }

    suspend fun deleteItem(itemEntity: BinEntity) {
        appDatabase.itemEntityDao().delete(itemEntity)
    }

    fun getAllItems(): Flow<List<BinEntity>> {
        return appDatabase.itemEntityDao().getAllItemEntities()
    }


}