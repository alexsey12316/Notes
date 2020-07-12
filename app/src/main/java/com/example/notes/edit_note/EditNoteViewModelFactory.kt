package com.example.notes.edit_note

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.database.DBHandler
import java.lang.IllegalArgumentException

class EditNoteViewModelFactory(val DB: DBHandler, val id: Int?) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T
    {
        if(modelClass.isAssignableFrom(EditNoteViewModel::class.java)) {
            return EditNoteViewModel(DB, id) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}