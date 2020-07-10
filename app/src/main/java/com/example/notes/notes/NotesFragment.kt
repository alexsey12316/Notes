package com.example.notes.notes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notes.MainActivity
import com.example.notes.R

class NotesFragment:Fragment()
{
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var viewAdapter: NotesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_note, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val addNoteFloatingActionButtton: View = view.findViewById(R.id.add_note_floating_button)

        addNoteFloatingActionButtton.setOnClickListener { view ->
            view.findNavController().navigate(R.id.action_navigation_note_to_addNoteFragment)
        }

        val notesList: List<Note> = listOf(Note("Title1", getString(R.string.note_text)),Note("Title2", getString(R.string.note_text)),Note("Title2", getString(R.string.note_text)),
            Note("Title2", getString(R.string.note_text)),Note("Title2", getString(R.string.note_text)),Note("Title2", getString(R.string.note_text)),Note("Title2", getString(R.string.note_text)),
            Note("Title2", getString(R.string.note_text)),Note("Title2", getString(R.string.note_text)),Note("Title2", getString(R.string.note_text)),Note("Title2", getString(R.string.note_text)),
            Note("Title2", getString(R.string.note_text)),Note("Title2", getString(R.string.note_text)),Note("Title2", getString(R.string.note_text)),Note("Title2", getString(R.string.note_text))
            )

        viewAdapter = NotesAdapter(notesList)
        viewManager = LinearLayoutManager(requireContext())

        recyclerView = view.findViewById<RecyclerView>(R.id.note_list).apply {
            adapter = viewAdapter
            val topSpacingItemDecoration = TopSpacingItemDecoration(30, 10)
            addItemDecoration(topSpacingItemDecoration)
            layoutManager = viewManager
        }
    }
}