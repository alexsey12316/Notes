package com.example.notes.edit_note

import android.app.Activity
import android.content.Intent
import android.text.TextUtils
import androidx.core.app.ShareCompat
import androidx.core.content.ContextCompat.startActivity


import androidx.lifecycle.ViewModel
import com.example.database.DBHandler
import com.example.database.Note
import java.text.SimpleDateFormat
import java.util.*

class EditNoteViewModel(val DB: DBHandler, val recordID: Int?) : ViewModel() {
    var initNote: Note = (DB.getRunning().find { it.ID == recordID})!!

    var titleNote: String = initNote.name
    var textNote: String = initNote.description
    var dateOfEvent: String = if(initNote.notifDate == null) "" else SimpleDateFormat("dd/MM/yy").format(initNote.notifDate)

    val EMPTY_EDIT_TEXT = 1
    val DB_ERROR = -1
    val SUCCESS = 0

    fun updateNoteRecord() : Int {

        if(CheckIsEmpty()) {
            return EMPTY_EDIT_TEXT
        }

        var eventDate: Date? = null

        if(!(TextUtils.isEmpty(dateOfEvent))) {
            eventDate = SimpleDateFormat("dd/MM/yy").parse(dateOfEvent)
        }

        var updatedNote: Note = Note(titleNote, textNote, eventDate)

        DB.update(initNote, updatedNote)

        return SUCCESS
    }

    fun markAsDeleted(): Int {

        DB.Delete(initNote)

        return SUCCESS
    }

    fun CheckIsEmpty()= (TextUtils.isEmpty(titleNote) || TextUtils.isEmpty(textNote))

}