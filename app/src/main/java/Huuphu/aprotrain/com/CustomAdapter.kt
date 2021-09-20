package Huuphu.aprotrain.com

import android.content.Context
import android.database.Cursor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CursorAdapter
import android.widget.TextView

class CustomAdapter(context: Context?, cursor: Cursor?) : CursorAdapter(context,cursor) {

    override fun newView(context: Context?, cursor: Cursor?, parent: ViewGroup?): View {
        return LayoutInflater.from(context).inflate(R.layout.list_item,null)
    }

    override fun bindView(view: View?, context: Context?, cursor: Cursor?) {
       var noidung : TextView = view!!.findViewById(R.id.nd)
        var ngaytao : TextView = view!!.findViewById(R.id.day)
        var quantrong : TextView = view!!.findViewById(R.id.quantrong)

        var noi_dung : String = cursor!!.getString(cursor.getColumnIndex("Noi_dung"))
        var ngay_tao : String = cursor!!.getString(cursor.getColumnIndex("Ngay_tao_note"))
        var quan_trong : Int = cursor!!.getInt(cursor.getColumnIndex("Quan_trong"))

        noidung.setText(noi_dung)
        ngaytao.setText(ngay_tao)
        quantrong.setText(quan_trong)

        if (quan_trong == 1){
            quantrong.setBackgroundResource(R.color.red)
        }else if (quan_trong == 0 ){
            quantrong.setBackgroundResource(R.color.green)
        }
    }

}