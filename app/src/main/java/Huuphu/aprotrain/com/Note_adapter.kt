package Huuphu.aprotrain.com

import android.content.Context
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.RelativeLayout
import android.widget.TextView

class Note_adapter(var context: Context, var layout: Int, var datalist: ArrayList<Note>) : BaseAdapter() {
    class viewhoder(row: View) {
        var noidungview : TextView
        var ngayview : TextView
        var quantrong : TextView
        init {
            noidungview = row.findViewById(R.id.nd)
            ngayview = row.findViewById(R.id.day)
            quantrong = row.findViewById(R.id.quantrong)
        }
    }
        override fun getCount(): Int {
            return datalist.size
        }
    override fun getItem(position: Int): Any {
        return 0;
    }

    override fun getItemId(position: Int): Long {
        return 0;
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        val view : View?
        var viewh : viewhoder
        if (convertView == null){
          var  layoutInflater : LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = layoutInflater.inflate(layout,null)
//            var layoutInflater : LayoutInflater = LayoutInflater.from(context)
//            view = layoutInflater.inflate(R.layout.list_item,null)
            viewh = viewhoder(view)
            view.tag =viewh
        }else{
            view = convertView
            viewh = convertView.tag as viewhoder

        }
        var data = datalist.get(position)
        viewh.noidungview.setText(data.Noi_dung)
        viewh.ngayview.setText(data.date)
        if (data.quantrong == 1){
            viewh.quantrong.setBackgroundResource(R.color.red)

        }else{
            viewh.quantrong.setBackgroundResource(R.color.green)
        }

        return view
    }
}