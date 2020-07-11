package com.example.notes.addnote

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.database.DBHandler
import java.lang.IllegalArgumentException

class AddNoteViewModelFactory(private val DB: DBHandler) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(AddNoteViewModel::class.java)) {
            return AddNoteViewModel(DB) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}