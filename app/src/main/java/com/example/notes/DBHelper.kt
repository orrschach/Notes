package com.example.notes

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DBHelper(
    context: Context?,
    name: String?,
    version: Int
) : SQLiteOpenHelper(context, name, null,version) {


    val TAG = "@@DBHelper"

    override fun onCreate(db: SQLiteDatabase) {
        Log.d(TAG,"DB onCreate ... ")
        db.execSQL("CREATE TABLE ${Word.TABLE} (\n" +
                "\t${Word.COL_ID} integer PRIMARY KEY autoincrement,\n" +
                "\t${Word.COL_en} varchar,\n" +
                "\t${Word.COL_ch} varchar,\n" +
                "\t${Word.COL_w_class} varchar)")

    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        Log.d(TAG,"DB on upgrade ,version from $oldVersion to $newVersion")
        if(oldVersion < 2  && newVersion >= 2){
            // 1 -> 2
            Log.d(TAG,"DB on upgrade exec 1-2 ")
            db.execSQL("create table User (id integer primary key autoincrement,name text)")

        }
        if(oldVersion < 3  && newVersion >= 3){
            // 2 -> 3
            Log.d(TAG,"DB on upgrade exec 2-3 ")
            db.execSQL("create table Note (id integer primary key autoincrement,content text)")
        }

    }
}