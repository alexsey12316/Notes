package com.example.notes.notes

import androidx.lifecycle.ViewModel
import com.example.database.DBHandler
import com.example.database.Note

class NotesViewModel(val DB: DBHandler) : ViewModel() {
    val listNotes: List<Note> = DB.getRunning()

    fun markAsDeleted(note: Note) {
        DB.Delete(note)
    }
}