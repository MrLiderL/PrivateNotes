package com.kirpoltoradnev.privatenotes.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.kirpoltoradnev.privatenotes.MainActivity
import java.lang.Exception

class DBOpenHelper(context: Context, factory: SQLiteDatabase.CursorFactory?)
    : SQLiteOpenHelper(context,
    DATABASE_NAME, factory,
    DATABASE_VERSION
) {

    companion object {
        private val DATABASE_VERSION = 1
        private val DATABASE_NAME = "openNoteApp1.1.db"
        val TABLE_NAME = "notes"
        val COLUMN_ID = "_id"
        val COLUMN_TITLE = "title"
        val COLUMN_NOTE = "note"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_NOTE_TABLE = ("CREATE TABLE $TABLE_NAME " +
                "($COLUMN_ID INTEGER PRIMARY KEY, " +
                "$COLUMN_TITLE TEXT, $COLUMN_NOTE TEXT)")
        if (db != null) db.execSQL(CREATE_NOTE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {}

    fun createNote(editedNote: Note){
        val values = ContentValues()
        values.put(COLUMN_TITLE, editedNote.title)
        values.put(COLUMN_NOTE, editedNote.noteText)
        val db = this.writableDatabase
        try {
            db.insert(TABLE_NAME, null, values)
        } catch (e:Exception){}
        db.close()
    }

    fun saveNote(editedNote: Note){
        val values = ContentValues()
        values.put(COLUMN_TITLE, editedNote.title)
        values.put(COLUMN_NOTE, editedNote.noteText)
        val db = this.writableDatabase
        db.update(TABLE_NAME,values, COLUMN_ID + "=?", arrayOf(editedNote.id.toString()))
        db.close()
    }


    fun getNotes(): ArrayList<Note> {
        val query = "SELECT * FROM $TABLE_NAME"
        val db = this.writableDatabase
        val cursor = db.rawQuery(query, null)
        val notes = ArrayList<Note>()

        if (cursor.count == 0) {
            // TODO Toast.makeText(this@MainActivity, "No Records Found", Toast.LENGTH_SHORT).show()
        } else {
            while (cursor.moveToNext()) {
                val noteId = cursor.getInt(cursor.getColumnIndex(COLUMN_ID))
                val noteTitle = cursor.getString(cursor.getColumnIndex(COLUMN_TITLE))
                val noteText = cursor.getString(cursor.getColumnIndex(COLUMN_NOTE))
                val note = Note(noteId, noteTitle, noteText)
                notes.add(note)
            }
            // TODO Toast.makeText(contextD, "${cursor.count.toString()} Records Found", Toast.LENGTH_SHORT).show()
        }

        cursor.close()
        db.close()

        return notes
    }
}