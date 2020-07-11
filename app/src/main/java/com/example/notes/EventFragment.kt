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
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import java.util.*


class EventFragment:Fragment()
{
    @SuppressLint("SimpleDateFormat")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val tempView=inflater.inflate(R.layout.fragment_event, container, false)
        val dbHandler=DBHandler(requireContext())
        val widget:MaterialCalendarView=tempView.findViewById(R.id.eventsCalendar)
        val listEvents=dbHandler.getAll()
        val rand= kotlin.random.Random
        val calendar=Calendar.getInstance()
       for (elem in listEvents){
            calendar.time=elem.notifDate!!
           val calendarDay=CalendarDay.from(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH)+1,calendar.get(Calendar.DAY_OF_MONTH))
            widget.addDecorator(CurrentDayDecorator(context = requireActivity(),currentDay = calendarDay,colorId = rand.nextInt(0,2)))
        }
        widget.setOnDateChangedListener{
                _, date, selected ->
            
        }

        return tempView
    }

}

class CurrentDayDecorator(context: Activity?, currentDay: CalendarDay,colorId:Int) : DayViewDecorator {
    private val drawable: Drawable?
    var myDay = currentDay
    override fun shouldDecorate(day: CalendarDay): Boolean {
        return day == myDay
    }

    override fun decorate(view: DayViewFacade) {
        view.setSelectionDrawable(drawable!!)
    }

    init {
        when (colorId) {
            0 -> drawable = ContextCompat.getDrawable(context!!, R.color.daysColorID0)
            1 -> drawable = ContextCompat.getDrawable(context!!, R.color.daysColorID1)
            2 -> drawable = ContextCompat.getDrawable(context!!, R.color.daysColorID2)
            else->drawable = ContextCompat.getDrawable(context!!, R.color.daysColorDefault)
        }

    }
}