package com.example.notes.notes

import androidx.lifecycle.ViewModel
import com.example.database.DBHandler
import com.example.database.Note

class NotesViewModel(DB: DBHandler) : ViewModel() {
    val listNotes: List<Note> = DB.getRunning()
}