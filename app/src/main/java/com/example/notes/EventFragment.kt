package com.example.notes


import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.database.DBHandler
import com.example.notes.notes.TopSpacingItemDecoration
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import java.util.*


class EventFragment:Fragment()
{
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var viewAdapter: NoteCalendarAdapter
    @SuppressLint("SimpleDateFormat")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val dbHandler=DBHandler(requireContext())
        val tempView=inflater.inflate(R.layout.fragment_event, container, false)
        recyclerView=tempView.findViewById(R.id.eventList)
        viewManager = LinearLayoutManager(requireContext())

        viewAdapter=NoteCalendarAdapter(listOf())
        recyclerView.apply {
            adapter = viewAdapter
            val eventsItemDecorator = EventsItemDecorator(0, 10)
            addItemDecoration(eventsItemDecorator)
            layoutManager = viewManager
        }
        val widget:MaterialCalendarView=tempView.findViewById(R.id.eventsCalendar)
        widget.selectionColor=Color.rgb(102,204,0)
        widget.setDateTextAppearance((R.color.colorAccent))
        val listEvents=dbHandler.getEvents()
        val calendar=Calendar.getInstance()
        for (elem in listEvents){
            calendar.time=elem.notifDate!!
            val calendarDay=CalendarDay.from(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH)+1,calendar.get(Calendar.DAY_OF_MONTH))
            widget.addDecorator(CurrentDayDecorator(context = requireActivity(),currentDay = calendarDay))
        }
        widget.setOnDateChangedListener{
                _, date, selected ->
            calendar.set(date.year,date.month-1,date.day)
            val eventsByDay=dbHandler.getEventsByDate(calendar.time)
            recyclerView.adapter=NoteCalendarAdapter(eventsByDay)
        }
        return tempView
    }

}

class CurrentDayDecorator(context: Activity?, currentDay: CalendarDay) : DayViewDecorator {
    var myDay = currentDay
    override fun shouldDecorate(day: CalendarDay): Boolean {
        return day == myDay
    }

    override fun decorate(view: DayViewFacade) {
        view.addSpan(ForegroundColorSpan(Color.RED))
    }
}