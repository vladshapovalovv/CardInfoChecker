package com.example.binviewapp.storage.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "item_entity")
data class BinEntity(

    @PrimaryKey val id: String = "",
    val bin: String = "",
)