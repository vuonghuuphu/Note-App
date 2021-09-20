package Huuphu.aprotrain.com

import android.app.Dialog
import android.content.ContentValues
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.text.Layout
import android.view.*
import android.widget.*
import android.widget.AdapterView.AdapterContextMenuInfo
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {
    lateinit var adapter : Note_adapter
    var arraynote : ArrayList<Note> = ArrayList()
lateinit var notelistv :ListView
lateinit var cursor : Cursor
    lateinit var note_mdf : Note_modify
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val customAdapter = CustomAdapter(this,cursor)
        DBhelper.Instance(this);

        adapter = Note_adapter(this, R.layout.list_item, arraynote)
        listview.adapter = adapter
        load()
        registerForContextMenu(listview)
        listview.setOnItemClickListener { parent, view, position, id ->
            var nd_note : String = arraynote[position].Noi_dung
            var quantrong_note : Int = arraynote[position].quantrong
            var date : String = arraynote[position].date
            
            val intent = Intent (this,MainActivity2::class.java)
            intent.putExtra("nd_am",nd_note)
            intent.putExtra("qt_am",quantrong_note)
            intent.putExtra("day_am",date)
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
var inference = menuInflater
        inference.inflate(R.menu.menu, menu)
        return true
    }

    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        menuInflater.inflate(R.menu.menu_contex, menu)
        super.onCreateContextMenu(menu, v, menuInfo)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.add_note -> {
                menu_add()
                return true
            }
            R.id.exit -> {
                System.exit(1)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.edit -> {
                val info = item.menuInfo as AdapterContextMenuInfo
                var index= info.position
                var cursordelete : Cursor = findall()
                cursordelete.moveToPosition(index)
                var Id :Int = cursordelete.getInt(cursordelete.getColumnIndex("id"))
                menu_edit(Id)
                return true
            }
            R.id.delete -> {
                val info = item.menuInfo as AdapterContextMenuInfo
                var index= info.position
                var cursordelete : Cursor = findall()
                cursordelete.moveToPosition(index)
                var Id :Int = cursordelete.getInt(cursordelete.getColumnIndex("id"))
               delete(Id)
//                val intent = Intent (this,MainActivity::class.java)
//                startActivity(intent)
                    load()
                return true
            }
        }
        return super.onContextItemSelected(item)
    }

    fun  menu_add(){
        val  builder = AlertDialog.Builder(this)
        val alter : View= LayoutInflater.from(this).inflate(R.layout.add_dialog, null)
        builder.setView(alter)
        val dialog:Dialog  = builder.create()
        dialog.show()
        val btnCancel: Button = alter.findViewById(R.id.op_cancel)
        btnCancel.setOnClickListener(View.OnClickListener {
            dialog.dismiss()
        })
        var edit_text_nd : EditText =alter.findViewById(R.id.edt_addnote)
        var checkbox_qt : CheckBox = alter.findViewById(R.id.quantrong1)

        val btncomit : Button = alter.findViewById(R.id.commitop)
        btncomit.setOnClickListener {
            var noidung: String =(edit_text_nd.text.toString())
            var Quantrong: Boolean = (checkbox_qt.isChecked)
            var checkqt : Int
            if (Quantrong == true){
                checkqt = 1;
            }else{
                checkqt = 0;
            }
            var currentTime: java.util.Date = Calendar.getInstance().time
            val formatter = SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss")
            val dateFormat = formatter.format(currentTime)
            var date : String = dateFormat
            var noteadd : Note = Note(noidung, checkqt, date)
            add(noteadd)
           load()
            dialog.dismiss()
        }
    }
    fun  menu_edit(id: Int){
        val  builder = AlertDialog.Builder(this)
        val alter : View= LayoutInflater.from(this).inflate(R.layout.edit_dialog, null)
        builder.setView(alter)
        val dialog : Dialog = builder.create()
        dialog.show()
        val btnCancel : Button = alter.findViewById(R.id.cancel)
        btnCancel.setOnClickListener(View.OnClickListener {
            dialog.dismiss()
        })
        var con_text_nd : EditText =alter.findViewById(R.id.con_editnote)
        var checkbox_qt : CheckBox = alter.findViewById(R.id.check_con)

        val btncomit : Button = alter.findViewById(R.id.commiteditt_con)
        btncomit.setOnClickListener {
            var noidung: String =(con_text_nd.text.toString())
            var Quantrong: Boolean = (checkbox_qt.isChecked)
            var checkqt : Int
            if (Quantrong == true){
                checkqt = 1;
            }else{
                checkqt = 0;
            }
            var currentTime: java.util.Date = Calendar.getInstance().time
            val formatter = SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss")
            val dateFormat = formatter.format(currentTime)
            var date : String = dateFormat
            var noteadd : Note = Note(noidung, checkqt, date)
            update(noteadd,id)
            load()
            dialog.dismiss()
        }
    }
    fun load(){
        arraynote.clear()
        var notecursor : Cursor = findall()
        while (notecursor.moveToNext()){
            var id : Int = notecursor.getInt(0)
            var noidung : String = notecursor.getString(1);
            var qt : Int = notecursor.getInt(2)
            var date : String = notecursor.getString(3)
            arraynote.add(Note(noidung, qt, date))
        }
        Toast.makeText(this, "Size: " + adapter.datalist.size, Toast.LENGTH_SHORT).show()
        adapter.notifyDataSetChanged()
    }

    fun add(note: Note){
        var db : SQLiteDatabase? = DBhelper.Instance(null)!!.writableDatabase
        var Giatri = ContentValues()
        Giatri.put("Noi_dung", note.Noi_dung)
        Giatri.put("Quan_trong", note.quantrong)
        Giatri.put("Ngay_tao_note", note.date)
        db!!.insert("Note", null, Giatri)
    }

    fun findall() : Cursor {
        var db : SQLiteDatabase? = DBhelper.Instance(null)?.readableDatabase
        val sql : String = "select * from Note"
        var cursor : Cursor = db!!.rawQuery(sql, null)
        return cursor
    }
fun delete(id : Int){
    var db : SQLiteDatabase? = DBhelper.Instance(null)!!.writableDatabase
    db!!.delete("Note", "_id = " + id, null)
}
fun update(note: Note,id :Int){
    var db : SQLiteDatabase? = DBhelper.Instance(null)!!.writableDatabase
    var Giatri = ContentValues()
    Giatri.put("Noi_dung", note.Noi_dung)
    Giatri.put("Quan_trong", note.quantrong)
    Giatri.put("Ngay_tao_note", note.date)
    db!!.update("Note", Giatri, "_id = " + id, null)
}}

