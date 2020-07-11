package com.example.notes.notes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.database.DBHandler
import com.example.notes.addnote.AddNoteViewModel
import java.lang.IllegalArgumentException

class NotesViewModelFactory(private val DB: DBHandler) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(NotesViewModel::class.java)) {
            return NotesViewModel(DB) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}