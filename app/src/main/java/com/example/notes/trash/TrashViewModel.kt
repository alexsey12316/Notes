package com.example.notes.trash

import androidx.lifecycle.ViewModel
import com.example.database.DBHandler
import com.example.database.Note

class TrashViewModel(val DB: DBHandler) : ViewModel() {

    init {
        DB.EraseTimeOut(1)
    }

    val deletedNoted: List<Note> = DB.getDeleted()

    fun restoreNote(note: Note) {
        DB.Restore(note)
    }
}