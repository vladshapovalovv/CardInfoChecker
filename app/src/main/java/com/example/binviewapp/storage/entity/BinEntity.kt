package com.example.binviewapp.storage.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bin_entity")
data class BinEntity(

    @PrimaryKey val id: String = "",
    val bin: String = "",
)