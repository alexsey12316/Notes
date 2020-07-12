package com.example.notes.notes

import android.content.ClipData
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.database.DBHandler
import com.example.database.Note
import com.example.notes.MainActivity
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

        val addNoteFloatingActionButton: View = view.findViewById(R.id.add_note_floating_button)

        addNoteFloatingActionButton.setOnClickListener { view ->
            view.findNavController().navigate(NotesFragmentDirections.actionNavigationNoteToAddNoteFragment())
        }

        viewAdapter = NotesAdapter(viewModel.listNotes.toMutableList()) { id: Int? ->
            view.findNavController().navigate(NotesFragmentDirections.actionNavigationNoteToEditNote(id!!))
        }

        viewManager = LinearLayoutManager(requireContext())

        recyclerView = view.findViewById<RecyclerView>(R.id.note_list).apply {
            adapter = viewAdapter
            val topSpacingItemDecoration = TopSpacingItemDecoration(15, 10)
            addItemDecoration(topSpacingItemDecoration)
            layoutManager = viewManager
        }

        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val removedNote = (viewAdapter as NotesAdapter).removeItem(viewHolder)
                viewModel.markAsDeleted(removedNote)
            }

        }

        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)

    }
}