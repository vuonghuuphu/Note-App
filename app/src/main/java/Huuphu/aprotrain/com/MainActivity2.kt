package Huuphu.aprotrain.com

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Button
import android.widget.FrameLayout
import android.widget.TextView

class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        val intent = intent
        val nd : String? = intent.getStringExtra("nd_am")
        val qt : Int = intent.getIntExtra("qt_am",0)
        val date : String? = intent.getStringExtra("day_am")

        val am_2 = findViewById<FrameLayout>(R.id.layout_activity_2)
        val tv_noidung = findViewById<TextView>(R.id.noidung_activity)
        val tv_day = findViewById<TextView>(R.id.date_act)
        tv_noidung.text =  nd.toString()
        tv_day.text = date.toString()
        if (qt == 1){
            am_2.setBackgroundResource(R.color.red)
        }else{
            am_2.setBackgroundResource(R.color.green)
        }

 val btn_return = findViewById<Button>(R.id.btn_return)
        btn_return.setOnClickListener {
            val intent = Intent (this,MainActivity::class.java)
            startActivity(intent)
        }
}}