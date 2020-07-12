package com.example.notes.edit_note

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.*
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.database.DBHandler
import com.example.notes.R
import com.example.notes.databinding.EditNoteFragmentBinding
import java.text.SimpleDateFormat
import java.util.*


class EditNoteFragment : Fragment() {

    private lateinit var binding: EditNoteFragmentBinding
    private lateinit var viewModel: EditNoteViewModel
    private lateinit var viewModelFactory: EditNoteViewModelFactory

    private lateinit var imm: InputMethodManager


    companion object {
        fun newInstance() = EditNoteFragment()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        setHasOptionsMenu(true)

        val args = EditNoteFragmentArgs.fromBundle(arguments!!)

        binding = DataBindingUtil.inflate(inflater, R.layout.edit_note_fragment, container, false)

        viewModelFactory = EditNoteViewModelFactory(DBHandler(requireContext()), args.recordID)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(EditNoteViewModel::class.java)


        Log.d("MainActivity", "ID record is" + args.recordID.toString())


        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.menu_edit_note, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        viewModel.titleNote = binding.editTextTitleNoteEdit.text.toString()
        viewModel.textNote = binding.editTextTextNoteEdit.text.toString()
        viewModel.dateOfEvent = binding.textViewPickDateEdit.text.toString()
        when(item?.itemId) {

            R.id.action_editNote -> {
                when(viewModel.updateNoteRecord()) {
                    viewModel.EMPTY_EDIT_TEXT -> Toast.makeText(requireContext(), "Заполните все текстовые поля", Toast.LENGTH_SHORT).show()
                    viewModel.DB_ERROR -> Toast.makeText(requireContext(), "Ошибка базы данных", Toast.LENGTH_SHORT).show()
                    viewModel.SUCCESS -> {
                        imm.hideSoftInputFromWindow(view?.windowToken, 0)
                        findNavController().navigate(EditNoteFragmentDirections.actionEditNoteToNavigationNote())

                    }

                }
            }

            R.id.action_deleteNote -> {
                when(viewModel.markAsDeleted()) {
                    viewModel.EMPTY_EDIT_TEXT -> Toast.makeText(requireContext(), "Заполните все текстовые поля", Toast.LENGTH_SHORT).show()
                    viewModel.DB_ERROR -> Toast.makeText(requireContext(), "Ошибка базы данных", Toast.LENGTH_SHORT).show()
                    viewModel.SUCCESS -> {
                        imm.hideSoftInputFromWindow(view?.windowToken, 0)
                        findNavController().navigate(EditNoteFragmentDirections.actionEditNoteToNavigationNote())

                    }
                }
            }

            R.id.action_shareNote -> {
                if(!(viewModel.CheckIsEmpty())) {
                    val sendIntent: Intent = Intent().apply {
                        action = Intent.ACTION_SEND
                        putExtra(Intent.EXTRA_TEXT, "${viewModel.titleNote} \n${viewModel.textNote}")
                        type = "text/plain"
                    }
                    val shareIntent = Intent.createChooser(sendIntent, null)
                    startActivity(shareIntent)
                } else {
                    Toast.makeText(requireContext(), "Заполните все текстовые поля", Toast.LENGTH_SHORT).show()
                }
            }
        }

        return false
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.editTextTitleNoteEdit.setText(viewModel.titleNote)
        binding.editTextTextNoteEdit.setText(viewModel.textNote)
        binding.textViewPickDateEdit.setText(viewModel.dateOfEvent)

        binding.textViewPickDateEdit.setOnClickListener {
            showDatePickerDialog()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        imm = (activity?.getSystemService(Context.INPUT_METHOD_SERVICE)) as InputMethodManager
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
                binding.textViewPickDateEdit.text = viewModel.dateOfEvent
            },
            currentYear,
            currentMonth,
            currentDay
        )

        datePickerDialog.show();
    }
}