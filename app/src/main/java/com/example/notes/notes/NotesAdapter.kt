package com.example.notes.notes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.database.Note
import com.example.notes.R
import kotlinx.android.synthetic.main.note_item.view.*

class NotesAdapter(private var noteDataSet: MutableList<Note>, private val listener : (id: Int?) -> Unit) : RecyclerView.Adapter<NotesAdapter.NotesViewHolder>() {

    inner class NotesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val note_title = itemView.note_title
        val note_text = itemView.note_text
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        return NotesViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.note_item, parent, false))
    }

    override fun getItemCount(): Int {
        return noteDataSet.size
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        holder.note_title.text = noteDataSet[position].name
        holder.note_text.text = noteDataSet[position].description
        holder.itemView.setOnClickListener { listener(noteDataSet[position].ID)}
    }

    fun removeItem(viewHolder: RecyclerView.ViewHolder) : Note{

        val removedItem = noteDataSet[viewHolder.adapterPosition]
        noteDataSet.removeAt(viewHolder.adapterPosition)
        notifyItemRemoved(viewHolder.adapterPosition)
        return removedItem
    }

}