package com.example.database

import android.nfc.Tag
import android.util.Log
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

import com.example.database.*;
import org.w3c.dom.Node
import java.util.*
import java.util.concurrent.ConcurrentHashMap

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    @Test
    fun useAppContext() {
        // Context of the app under test.

        val db=DBHandler(appContext)

        val note= com.example.database.Note("test1" ,"test description 1",
            Date(2020,7,10,12,30))

        Log.d(TAG,note.toString())
        db.add(note)
        Log.d(TAG,note.toString())

    }



    companion object
    {
        val TAG="DATABASE_DEBUG"
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext

    }

    fun getNote(i: Int)=Note("test$i" ,"test description $i",
        Date(2020,7,10+i,15,30))
    fun getNote()=Note("test" ,"test description",
        Date(99999999990))

    @Test
    fun addANDget()
    {
        val db=DBHandler(appContext)

        for (i in 0..9)
        {
            val note=getNote(i);
            Log.d(TAG,note.toString())
            db.add(note)
        }
        val arr=db.getAll()
        for (i in arr)
            Log.d(TAG,i.toString())
    }

    @Test
    fun getAll()
    {
        val db=DBHandler(appContext)
        val arr=db.getAll()
        for (i in arr)
            Log.d(TAG,i.toString())
    }

    @Test
    fun addOne()
    {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext

        val db=DBHandler(appContext)

        db.add(getNote())
    }
    @Test
    fun Erase()
    {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext

        val db=DBHandler(appContext)

        db.EraseDeleted()
    }

    @Test
    fun ChangeName()
    {
        val db=DBHandler(appContext)
        val arr=db.getAll()
        val last=arr.last()
        Log.d(TAG,last.toString())
        db.updateName(last,"change")
        Log.d(TAG,last.toString())

    }

    @Test
    fun Delete()
    {
        Log.d(TAG,"----------------------------------------------------------------------------------------------------------------------------------------------------------------------")
        val db=DBHandler(appContext)
        var arr=db.getAll()
        var last=arr.last()
        Log.d(TAG,last.toString())
        db.Delete(last)
        Log.d(TAG,last.toString())
        arr=db.getAll()
        last=arr.last()
        Log.d(TAG,last.toString())

    }

    @Test
    fun date()
    {
        //       Date-> CalendarDay(year month day)
        val date= Date()

        val calendar=Calendar.getInstance()
        calendar.time=date
    }
    @Test
    fun Events()
    {
        Log.d(TAG,"----------------------------------------------------------------------------------------------------------------------------------------------------------------------")
        val db=DBHandler(appContext)
        val arr=db.getEvents()
        for (i in arr)
            Log.d(TAG,i.toString())
    }
    @Test
    fun getSpecificEvents()
    {
        Log.d(TAG,"----------------------------------------------------------------------------------------------------------------------------------------------------------------------")
        val db=DBHandler(appContext)
        val arr=db.getEventsByDate(Date())
        for (i in arr)
            Log.d(TAG,i.toString())
    }
    @Test
    fun AddEvents()
    {
        Log.d(TAG,"----------------------------------------------------------------------------------------------------------------------------------------------------------------------")
        val db=DBHandler(appContext)
        db.add(Note("Event","Event description", Date()))
    }

    @Test
    fun ChangeLast()
    {

        Log.d(TAG,"--------------------------------------------------------------------------------------------------------------------")
        val db=DBHandler(appContext)
        val arr=db.getAll()
        val last=arr.last()
//        val note=Note("name","desc",Date(100000))
        val note=Note("name","desc")
        Log.d(TAG,last.toString())
        db.update(last,note)
        Log.d(TAG,last.toString())
    }
    @Test
    fun ChangeLastDate()
    {
        Log.d(TAG,"--------------------------------------------------------------------------------------------------------------------")
        val db=DBHandler(appContext)
        val arr=db.getAll()
        val last=arr.last()
        Log.d(TAG,last.toString())
        db.updateNotification(last,Date())
        Log.d(TAG,last.toString())
    }

    @Test
    fun EraseTimeOut()
    {
        getAll()
        val db=DBHandler(appContext)
        Log.d(TAG,"--------------------------------------------------------------------------------------------------------------------")
db.EraseTimeOut(1)
        getAll()
    }
}


