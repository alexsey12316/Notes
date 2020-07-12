package com.example.notes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.database.Note
import kotlinx.android.synthetic.main.note_item.view.*

class NoteCalendarAdapter(private var items:List<Note>):
    RecyclerView.Adapter<NoteCalendarAdapter.NotesViewHolder>() {
    inner class NotesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val note_title = itemView.note_title
        val note_text = itemView.note_text

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        return NotesViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.note_item, parent, false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        holder.note_title.text = items[position].name
        holder.note_text.text = items[position].description
    }
}