package com.example.notes.addnote

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.database.DBHandler
import com.example.database.Note
import com.example.notes.R
import com.example.notes.databinding.AddNoteFragmentBinding
import kotlinx.android.synthetic.main.add_note_fragment.*
import java.text.SimpleDateFormat
import java.util.*

class AddNoteFragment : Fragment() {

    companion object {
        fun newInstance() = AddNoteFragment()
    }

    private lateinit var viewModel: AddNoteViewModel
    private lateinit var viewModelFactory: AddNoteViewModelFactory
    private lateinit var binding: AddNoteFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)

        binding = DataBindingUtil.inflate(inflater, R.layout.add_note_fragment, container, false)

        viewModelFactory = AddNoteViewModelFactory(DBHandler(requireContext()))

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(AddNoteViewModel::class.java)

        binding.textViewPickDate.setOnClickListener {
            showDatePickerDialog()
        }

        binding.textViewPickDate.text = viewModel.dateOfEvent

        return binding.root
    }


    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.menu_add_note, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        if(item?.itemId == R.id.action_addNote) {
            viewModel.titleNote = binding.editTextTitleNote.text.toString()
            viewModel.descriptionNote = binding.editTextTextNote.text.toString()
            viewModel.dateOfEvent = binding.textViewPickDate.text.toString()

            when(viewModel.addNoteRecordToDB()) {
                viewModel.EMPTY_EDIT_TEXT -> Toast.makeText(requireContext(), "Заполните все текстовые поля", Toast.LENGTH_SHORT).show()
                viewModel.DB_ERROR -> Toast.makeText(requireContext(), "Ошибка базы данных", Toast.LENGTH_SHORT).show()
                viewModel.SUCCESS -> findNavController().navigate(R.id.action_addNoteFragment_to_navigation_note)
            }
        }

        return false
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    private fun showDatePickerDialog() {
        val c: Calendar = Calendar.getInstance()
        val currentYear: Int = c.get(Calendar.YEAR)
        val currentMonth: Int = c.get(Calendar.MONTH)
        val currentDay: Int = c.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                viewModel.dateOfEvent = SimpleDateFormat("dd/MM/yy", Locale.ENGLISH).format(Date(year, monthOfYear, dayOfMonth))
                binding.textViewPickDate.text = viewModel.dateOfEvent
            },
            currentYear,
            currentMonth,
            currentDay
        )

        datePickerDialog.show();
    }



}