package Huuphu.aprotrain.com

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteClosable
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.CalendarContract
import java.lang.reflect.Array
import java.lang.reflect.Modifier
import java.util.concurrent.Flow

val DB_version : Int = 1
val Db_Name : String = "Note"
class DBhelper(context: Context?) : SQLiteOpenHelper(context,Db_Name,null, DB_version) {
    companion object{
        var instance : DBhelper? = null
        @Synchronized
        fun Instance(context: Context?): DBhelper? {
            if (instance==null){
                if (context != null) {
                    instance = DBhelper(context)
                }
            }
            return instance
        }
    }

   override fun onCreate(db: SQLiteDatabase?) {
        val SQl_create : String = " Create table Note ( "  +
                "_id integer primary key autoincrement, " +
                " Noi_dung text, " +
                " Quan_trong integer, " +
                " Ngay_tao_note datetime )"
       db!!.execSQL(SQl_create)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

}


