package com.example.notes.notes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.database.DBHandler
import com.example.notes.R

class NotesFragment:Fragment()
{
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var viewAdapter: NotesAdapter

    private lateinit var viewModel: NotesViewModel
    private lateinit var viewModelFactory: NotesViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModelFactory = NotesViewModelFactory(DBHandler(requireContext()))

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(NotesViewModel::class.java)

        return inflater.inflate(R.layout.fragment_note, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val addNoteFloatingActionButtton: View = view.findViewById(R.id.add_note_floating_button)

        addNoteFloatingActionButtton.setOnClickListener { view ->
            view.findNavController().navigate(NotesFragmentDirections.actionNavigationNoteToAddNoteFragment())
        }



        viewAdapter = NotesAdapter(viewModel.listNotes) { id: Int? ->
            view.findNavController().navigate(NotesFragmentDirections.actionNavigationNoteToEditNote(id!!))
        }
        viewManager = LinearLayoutManager(requireContext())

        recyclerView = view.findViewById<RecyclerView>(R.id.note_list).apply {
            adapter = viewAdapter
            val topSpacingItemDecoration = TopSpacingItemDecoration(15, 10)
            addItemDecoration(topSpacingItemDecoration)
            layoutManager = viewManager
        }
    }
}