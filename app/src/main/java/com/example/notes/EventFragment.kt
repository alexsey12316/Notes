package com.example.notes


import android.os.Bundle
import android.text.style.TtsSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import android.widget.DatePicker
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.applandeo.materialcalendarview.builders.DatePickerBuilder
import com.example.database.DBHandler


class EventFragment:Fragment()
{
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val tempvView =inflater.inflate(R.layout.fragment_event, container, false)
        val tempCalendar:com.applandeo.materialcalendarview.CalendarView=tempvView.findViewById(R.id.eventsCalendar)
        return tempvView
    }


}