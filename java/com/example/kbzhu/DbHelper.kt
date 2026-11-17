package com.example.kbzhu

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DbHelper(context: Context) : SQLiteOpenHelper(context, "kbzhu.db", null, 1) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("CREATE TABLE foods(id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, kcal REAL, p REAL, f REAL, c REAL)")
        db.execSQL("CREATE TABLE records(id INTEGER PRIMARY KEY AUTOINCREMENT, date TEXT, food TEXT, grams REAL, kcal REAL, p REAL, f REAL, c REAL)")

        val foods = listOf("Куриная грудка", "Яйцо", "Рис отварной", "Гречка отварная", "Банан", "Овсянка", "Творог 5%", "Хлеб", "Молоко 3.2%", "Яблоко")
        val vals = listOf(
            doubleArrayOf(165.0, 31.0, 3.6, 0.0),
            doubleArrayOf(78.0, 6.3, 5.3, 0.6),
            doubleArrayOf(116.0, 2.2, 0.5, 25.0),
            doubleArrayOf(110.0, 4.2, 1.1, 21.0),
            doubleArrayOf(96.0, 1.5, 0.2, 21.0),
            doubleArrayOf(88.0, 3.0, 1.7, 15.0),
            doubleArrayOf(121.0, 17.0, 5.0, 3.0),
            doubleArrayOf(250.0, 8.0, 3.0, 48.0),
            doubleArrayOf(60.0, 3.0, 3.2, 4.7),
            doubleArrayOf(52.0, 0.3, 0.2, 14.0)
        )
        for (i in foods.indices) {
            db.execSQL("INSERT INTO foods(name,kcal,p,f,c) VALUES(?,?,?,?,?)", arrayOf(foods[i], vals[i][0], vals[i][1], vals[i][2], vals[i][3]))
        }
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {}
}