package com.example.database

import java.util.*

class Note( name:String,  description:String,
//            update:Date=Date(),
            notifDate:Date?=null) {
    var ID:Int?=null
    internal set

    var name:String=name
    internal set
    var description:String=description
    internal set
    var update:Date=Date()
    internal set
    var notifDate:Date?=notifDate
    internal set
    var isDeleted=false
    internal set

    override fun toString(): String {
        return "|$name | $description | $update | $notifDate | $isDeleted | $ID"
    }
}

//fun foo(note: Note)
//{
//    note.ID=1
//}