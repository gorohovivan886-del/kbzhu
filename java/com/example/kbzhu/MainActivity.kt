package com.example.kbzhu

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var dbHelper: DbHelper
    private lateinit var list: LinearLayout
    private lateinit var totals: TextView
    private val today = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dbHelper = DbHelper(this)
        list = findViewById(R.id.list)
        totals = findViewById(R.id.totals)

        findViewById<Button>(R.id.btnAdd).setOnClickListener {
            startActivity(Intent(this, AddActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        refresh()
    }

    private fun refresh() {
        list.removeAllViews()
        val db = dbHelper.readableDatabase
        val c = db.rawQuery("SELECT * FROM records WHERE date=? ORDER BY id DESC", arrayOf(today))

        var kcal = 0.0
        var p = 0.0
        var f = 0.0
        var cbh = 0.0

        while (c.moveToNext()) {
            kcal += c.getDouble(4)
            p += c.getDouble(5)
            f += c.getDouble(6)
            cbh += c.getDouble(7)

            val tv = TextView(this)
            tv.text = "${c.getString(2)} ${c.getDouble(3)}г — ${Math.round(c.getDouble(4))} ккал"
            tv.setPadding(16, 16, 16, 16)
            tv.textSize = 16f
            list.addView(tv)
        }
        c.close()

        totals.text = String.format("Сегодня: %.0f ккал | Б %.0f / Ж %.0f / У %.0f", kcal, p, f, cbh)
    }
}