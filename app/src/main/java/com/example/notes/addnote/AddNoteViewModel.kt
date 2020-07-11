package com.example.notes.addnote

import android.text.TextUtils
import androidx.lifecycle.ViewModel
import com.example.database.DBHandler
import com.example.database.Note
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


class AddNoteViewModel(val DB: DBHandler) : ViewModel() {
    lateinit var titleNote: String
    lateinit var descriptionNote: String
    var dateOfEvent: String = ""

    val EMPTY_EDIT_TEXT = 1
    val DB_ERROR = -1
    val SUCCESS = 0

    fun addNoteRecordToDB(): Int {
        if(TextUtils.isEmpty(titleNote) || TextUtils.isEmpty(descriptionNote)) {
            return EMPTY_EDIT_TEXT
        }

        var eventDate: Date? = null

        if(!(TextUtils.isEmpty(dateOfEvent))) {
            eventDate = SimpleDateFormat("dd/MM/yy").parse(dateOfEvent)
        }

        DB.add(Note(titleNote, descriptionNote, eventDate))

        return SUCCESS
    }


}