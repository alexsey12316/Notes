package com.example.notes


import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.database.DBHandler
import com.example.database.Note
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade
import com.prolificinteractive.materialcalendarview.MaterialCalendarView

import java.text.SimpleDateFormat
import java.util.*

class EventFragment:Fragment()
{
    @SuppressLint("SimpleDateFormat")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val note=Note("iouuu","Lol",java.util.Date())
        val dbHandler=DBHandler(requireContext())
        val tempView=inflater.inflate(R.layout.fragment_event, container, false)
        val widget:MaterialCalendarView=tempView.findViewById(R.id.eventsCalendar)
        val listEvents=dbHandler.getEvents()
        val calendar=Calendar.getInstance()
       for (elem in listEvents){
            calendar.time=elem.notifDate!!
           val calendarDay=CalendarDay.from(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH)+1,calendar.get(Calendar.DAY_OF_MONTH))
            widget.addDecorator(CurrentDayDecorator(activity,calendarDay))
        }
        return tempView
    }


}

class CurrentDayDecorator(context: Activity?, currentDay: CalendarDay) : DayViewDecorator {
    private val drawable: Drawable?
    var myDay = currentDay
    override fun shouldDecorate(day: CalendarDay): Boolean {
        return day == myDay
    }

    override fun decorate(view: DayViewFacade) {
        view.setSelectionDrawable(drawable!!)
    }

    init {
        drawable = ContextCompat.getDrawable(context!!, R.color.colorAccent)
    }
}