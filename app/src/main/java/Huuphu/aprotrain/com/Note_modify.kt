package Huuphu.aprotrain.com

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

abstract class Note_modify : Cursor {

   fun add(note: Note){
    var db : SQLiteDatabase? = DBhelper.Instance(null)!!.writableDatabase
       var Giatri = ContentValues()
       Giatri.put("Noi_dung", note.Noi_dung)
       Giatri.put("Quan_trong", note.quantrong)
       val formatter = SimpleDateFormat("YYYY-MM-DD HH:MM:SS.SSS")
       val dateFormat = formatter.format(note.date)
       Giatri.put("Ngay_tao_note",dateFormat)
       db!!.insert("Note", null, Giatri)
   }

    fun update(note: Note){
        var db : SQLiteDatabase? = DBhelper.Instance(null)!!.writableDatabase
        var Giatri = ContentValues()
        Giatri.put("Noi_dung", note.Noi_dung)
        Giatri.put("Quan_trong", note.quantrong)
        val df: DateFormat = SimpleDateFormat("EEE, d MMM yyyy, HH:mm")
        val date: String = df.format(Calendar.getInstance().time)
        Giatri.put("Ngay_tao_note", date)
        db!!.update("Note", Giatri, "id = " + note.id, null)
    }

    fun delete(note: Note){
        var db : SQLiteDatabase? = DBhelper.Instance(null)!!.writableDatabase
        db!!.delete("Note", "id = " + note.id, null)
    }

    fun findall() : Cursor{
        var db : SQLiteDatabase? = DBhelper.Instance(null)?.readableDatabase
        val sql : String = "select * from Note"
        var cursor : Cursor = db!!.rawQuery(sql, null)
        return cursor
    }

}
