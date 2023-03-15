package com.example.binviewapp.storage.dao

import androidx.room.*
import com.example.binviewapp.storage.entity.BinEntity
import kotlinx.coroutines.flow.Flow
@Dao
interface BinEntityDao  {

    @Query("SELECT * FROM item_entity")
    fun getAllItemEntities(): Flow<List<BinEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(itemEntity: BinEntity)

    @Delete
    suspend fun delete(user: BinEntity)


}