package com.example.notes.trash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.database.DBHandler
import com.example.notes.R
import com.example.notes.notes.NotesAdapter
import com.example.notes.notes.NotesFragmentDirections
import com.example.notes.notes.TopSpacingItemDecoration

class TrashFragment : Fragment()
{
    lateinit var viewModel: TrashViewModel
    lateinit var viewModelFactory: TrashViewModelFactory
    lateinit var viewAdapter: TrashAdapter

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModelFactory = TrashViewModelFactory(DBHandler(requireContext()))

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(TrashViewModel::class.java)

        return inflater.inflate(R.layout.fragment_trash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewAdapter = TrashAdapter(viewModel.deletedNoted.toMutableList()) { id: Int? ->
            Toast.makeText(requireContext(), "Произведите свайп, чтобы восстановить заметку", Toast.LENGTH_SHORT).show()
        }

        viewManager = LinearLayoutManager(requireContext())

        recyclerView = view.findViewById<RecyclerView>(R.id.deleted_note_list).apply {
            adapter = viewAdapter
            val topSpacingItemDecoration = TopSpacingItemDecoration(15, 10)
            addItemDecoration(topSpacingItemDecoration)
            layoutManager = viewManager
        }

        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val removedNote = viewAdapter.removeItem(viewHolder)
                viewModel.restoreNote(removedNote)
                Toast.makeText(requireContext(), "Восстановлено", Toast.LENGTH_SHORT).show()
            }

        }

        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)

    }


}