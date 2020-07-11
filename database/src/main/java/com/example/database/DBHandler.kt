package com.example.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import androidx.core.database.getLongOrNull
import java.util.*
import kotlin.collections.ArrayList

class DBHandler(context : Context) :SQLiteOpenHelper(context,
    DATABASE_NAME,null,1)
{

   private companion object
    {
        val DATABASE_NAME="NOTES.db"
        val TABLE_NAME="NOTES_TABLE"
        val COL_NAME="NAME"
        val COL_DESCR="DESCRIPTION"
        val COL_UPDATE_TIME="UPDATE_TIME"
        val COL_NOTIF_TIME="NOTIFICATION_TIME"
        val COL_IS_DELETED="IS_DELETED"
        val COL_ID="ID"
        val calendar=Calendar.getInstance()

    }

    override fun onCreate(db: SQLiteDatabase?)
    {
        db?.execSQL("CREATE TABLE $TABLE_NAME (" +
                "$COL_NAME TEXT," +
                "$COL_DESCR TEXT," +
                "$COL_UPDATE_TIME INTEGER," +
                "$COL_NOTIF_TIME INTEGER," +
                "$COL_IS_DELETED INTEGER,"+
                "$COL_ID INTEGER PRIMARY KEY AUTOINCREMENT)")

    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int)
    {

    }

    fun add(note: Note):Long
    {
        val db=this.writableDatabase
        val cv:ContentValues= ContentValues()
        cv.put(COL_NAME,note.name)
        cv.put(COL_DESCR,note.description)
        cv.put(COL_UPDATE_TIME,note.update.time)

        if(note.notifDate!=null)
            cv.put(COL_NOTIF_TIME,note.notifDate?.time)

        cv.put(COL_IS_DELETED,0)

        val res=db.insert(TABLE_NAME,null,cv)
        db.close()

        note.ID=res.toInt();
        return res
    }

    private fun get(sql:String):List<Note>
    {
        val db=readableDatabase
        val cursor:Cursor= db.rawQuery(sql,null)

        val array= ArrayList<Note>();

        if(cursor.moveToFirst())
        {
            var name:String
            var description:String
            var update:Date
            var notif:Date?
            var isDeleted:Boolean
            var id:Int
            var temp:Long? //for

            do {
                name=cursor.getString(0)
                description=cursor.getString(1)
                update=Date(cursor.getLong(2))

                temp=cursor.getLongOrNull(3)
                notif=if(temp==null)null else Date(temp)

                isDeleted= cursor.getInt(4)==1
                id=cursor.getInt(5)
                val note=Note(name, description,  notif)
                note.isDeleted=isDeleted
                note.ID=id
                note.update=update
                array.add(note)
            }while (cursor.moveToNext())
        }
        db.close()
        return array;
    }

    fun getAll():List<Note> = get("SELECT * from $TABLE_NAME")
    fun getDeleted():List<Note> = get("SELECT * from $TABLE_NAME WHERE $COL_IS_DELETED == 1")
    fun getRunning():List<Note> = get("SELECT * from $TABLE_NAME WHERE $COL_IS_DELETED == 0")
    fun getEvents():List<Note> = get("SELECT * from $TABLE_NAME WHERE $COL_NOTIF_TIME not null AND $COL_IS_DELETED == 0")
    fun getEventsByDate(date:Date):List<Note>
    {
        calendar.time=date
        val day= calendar.get(Calendar.DAY_OF_MONTH)
        val month= calendar.get(Calendar.MONTH)
        val year= calendar.get(Calendar.YEAR)

        calendar.set(year,month,day,0,0,0)
        val left:Long= calendar.time.time
        calendar.add(Calendar.DAY_OF_MONTH,1)
        val right:Long= calendar.time.time

        return get("SELECT * from $TABLE_NAME WHERE $COL_NOTIF_TIME BETWEEN $left AND $right AND $COL_IS_DELETED == 0")
    }

    fun EraseTimeOut(days:Int ):Boolean
    {
        calendar.time=Date()
        calendar.add(Calendar.DAY_OF_MONTH,-days)

        val date= calendar.time

        val db=this.writableDatabase
        val res=db.delete(TABLE_NAME,"$COL_IS_DELETED=1 AND $COL_UPDATE_TIME < ${date.time}", null)
        db.close()
        return res!=-1
    }


    private fun update(toChange: Note,param:ContentValues):Int
    {
        if(toChange.ID==null)
            throw IllegalArgumentException("This note doesn't assigned to data base")

        val db=this.writableDatabase
        param.put(COL_UPDATE_TIME,Date().time)
        val res=db.update(TABLE_NAME,param,"$COL_ID=?", arrayOf(toChange.ID.toString()))
        db.close()
        return res
    }

    fun updateName(toChange: Note,name: String):Int
    {
        val cv=ContentValues()
        cv.put(COL_NAME,name)
        val res=update(toChange,cv)
        if(res!=-1)
        {
            toChange.name=name
            toChange.update=Date()
        }
        return res
    }
    fun updateDescription(toChange: Note,description: String):Int
    {
        val cv=ContentValues()
        cv.put(COL_DESCR,description)
        val res=update(toChange,cv)
        if(res!=-1)
        {
            toChange.description=description
            toChange.update=Date()
        }
        return res
    }
    fun updateNotification(toChange: Note,notif: Date?):Int
    {
        val cv=ContentValues()
        if(notif==null)
            cv.putNull(COL_NOTIF_TIME)
        else
            cv.put(COL_NOTIF_TIME,notif.time)

        val res=update(toChange,cv)
        if(res!=-1)
        {
            toChange.notifDate=notif
            toChange.update=Date()
        }
        return res
    }
    private fun updateIsDeleted(toChange: Note,isDeleted: Boolean):Int
    {
        val cv=ContentValues()
        cv.put(COL_IS_DELETED,isDeleted)
        val res=update(toChange,cv)
        if(res!=-1)
        {
            toChange.isDeleted=isDeleted
            toChange.update=Date()
        }
        return res
    }
    fun Delete(note:Note):Int = updateIsDeleted(note,true)
    fun Restore(note:Note):Int= updateIsDeleted(note,false)

    fun update(toChange: Note,new: Note):Int
    {
        val cv=ContentValues()
        cv.put(COL_NAME,new.name)
        cv.put(COL_DESCR,new.description)

        if(new.notifDate==null)
            cv.putNull(COL_NOTIF_TIME)
        else
            cv.put(COL_NOTIF_TIME, new.notifDate?.time)

        cv.put(COL_IS_DELETED,new.isDeleted)
        val res=update(toChange,cv)
        if(res!=-1)
        {
            toChange.name=new.name
            toChange.description=new.description
            toChange.update=Date()
            toChange.notifDate=new.notifDate
            toChange.isDeleted=new.isDeleted
        }
        return res
    }

    fun Erase(note:Note):Boolean
    {
        if(note.ID==null)
            throw IllegalArgumentException("This note doesn't assigned to data base")
        val db=this.writableDatabase
        val res=db.delete(TABLE_NAME,"$COL_ID=?", arrayOf(note.ID.toString()))
        db.close()
        if(res!=-1)
        {
            note.ID=null
            return  true
        }
        return false
    }

    fun EraseDeleted():Boolean
    {
        val db=this.writableDatabase
        val res=db.delete(TABLE_NAME,"$COL_IS_DELETED=?", arrayOf("1"))
        db.close()
        return res!=-1
    }

}

