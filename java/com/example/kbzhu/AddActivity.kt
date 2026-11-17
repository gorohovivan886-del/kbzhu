package com.example.kbzhu

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class AddActivity : AppCompatActivity() {

    private lateinit var spinner: Spinner
    private lateinit var grams: EditText
    private val names = mutableListOf<String>()
    private val vals = mutableListOf<DoubleArray>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        spinner = findViewById(R.id.spinner)
        grams = findViewById(R.id.grams)

        val dbHelper = DbHelper(this)
        val db = dbHelper.readableDatabase
        val c = db.rawQuery("SELECT name,kcal,p,f,c FROM foods", null)

        while (c.moveToNext()) {
            names.add(c.getString(0))
            vals.add(doubleArrayOf(c.getDouble(1), c.getDouble(2), c.getDouble(3), c.getDouble(4)))
        }
        c.close()

        spinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, names)

        findViewById<Button>(R.id.btnSave).setOnClickListener {
            val text = grams.text.toString()
            if (text.isEmpty()) return@setOnClickListener

            val g = text.toDouble() / 100.0
            val i = spinner.selectedItemPosition
            val v = vals[i]
            val today = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())

            db.execSQL("INSERT INTO records(date,food,grams,kcal,p,f,c) VALUES(?,?,?,?,?,?,?)", arrayOf(today, names[i], g * 100, v[0] * g, v[1] * g, v[2] * g, v[3] * g))
            finish()
        }
    }
}