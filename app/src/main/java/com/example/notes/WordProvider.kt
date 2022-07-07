package com.example.notes

import android.content.ContentProvider
import android.content.ContentUris
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri

class WordProvider : ContentProvider() {

    private val wordDir = 0
    private val wordItem = 1

    lateinit var helper:DBHelper

    private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH)

    private val authority = "com.example.notes.provider"

    init{
        uriMatcher.addURI(authority,"word",wordDir)
        uriMatcher.addURI(authority,"word/#",wordItem)
    }

    override fun onCreate(): Boolean {
        helper = DBHelper(context,"word.db",3)
        return true
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        var rs: Cursor? = null
        val db= helper.readableDatabase
        when(uriMatcher.match(uri)){
            wordDir->{
                rs = db.query(Word.TABLE,projection,selection,selectionArgs,null,null,sortOrder)
            }
            wordItem->{
                val id = ContentUris.parseId(uri)
                rs = db.query(Word.TABLE,projection,"id=?", arrayOf(id.toString()),null,null,sortOrder)
            }
        }
        return rs
    }

    override fun getType(uri: Uri): String? {
        val dir = "vnd.android.cursor.dir/vnd.$authority.notes"
        val item = "vnd.android.cursor.dir/vnd.$authority.notes"
        return when(uriMatcher.match(uri)){
            wordDir->dir
            wordItem->item
            else-> dir
        }
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        var rs: Uri? = null

        val db = helper.writableDatabase
        when(uriMatcher.match(uri)){
            wordDir->{
                val id = db.insert(Word.TABLE,null,values)
                rs = Uri.parse("content://$authority/note/$id")
            }
        }
        db.close()
        return rs
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        var rs = 0
        val db = helper.writableDatabase
        when(uriMatcher.match(uri)){
            wordItem->{
                val id = ContentUris.parseId(uri)
                rs = db.delete(Word.TABLE,"id=?", arrayOf(id.toString()))
            }
        }
        db.close()
        return rs
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
        return 0
    }
}