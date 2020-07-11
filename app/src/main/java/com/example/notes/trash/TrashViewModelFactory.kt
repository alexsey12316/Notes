package com.example.notes.trash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.database.DBHandler
import com.example.notes.notes.NotesViewModel
import java.lang.IllegalArgumentException

class TrashViewModelFactory(private val DB: DBHandler) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(TrashViewModel::class.java)) {
            return TrashViewModel(DB) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}