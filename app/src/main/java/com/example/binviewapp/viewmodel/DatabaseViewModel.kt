package com.example.binviewapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.binviewapp.repository.DataBaseRepository
import com.example.binviewapp.storage.AppDatabase
import com.example.binviewapp.storage.entity.BinEntity
import kotlinx.coroutines.launch

class DatabaseViewModel: ViewModel() {

    private lateinit var repository: DataBaseRepository

    val itemEntitiesLiveData = MutableLiveData<List<BinEntity>>()

    val transactionCompleteLiveData = MutableLiveData<Boolean>()

    fun init(appDatabase: AppDatabase) {
        repository = DataBaseRepository(appDatabase)

        viewModelScope.launch {
            val items = repository.getAllItems().collect { items ->
                itemEntitiesLiveData.postValue(items)
            }
        }
    }

    fun insertItem(itemEntity: BinEntity) {
        viewModelScope.launch { repository.insertItem(itemEntity) }

        transactionCompleteLiveData.postValue(true)
    }

    fun deleteItem(itemEntity: BinEntity) {
        viewModelScope.launch { repository.deleteItem(itemEntity) }
    }
}