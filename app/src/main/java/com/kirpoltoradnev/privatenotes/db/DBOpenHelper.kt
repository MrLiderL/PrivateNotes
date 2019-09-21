package com.kirpoltoradnev.privatenotes.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import java.lang.Exception

class DBOpenHelper(context: Context, factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {

    companion object {
        private val DATABASE_VERSION = 1
        private val DATABASE_NAME = "openNoteApp1.1.db"
        val TABLE_NAME = "notes"
        val COLUMN_ID = "_id"
        val COLUMN_TITLE = "title"
        val COLUMN_NOTE = "note"
    }

    // Вызывается только при первом запуске приложения
    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_NOTE_TABLE = ("CREATE TABLE $TABLE_NAME " +
                "($COLUMN_ID INTEGER PRIMARY KEY, " +
                "$COLUMN_TITLE TEXT, $COLUMN_NOTE TEXT)")
        db?.execSQL(CREATE_NOTE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {}

    // Создание заметки в базе данных. Парсинг полей элемента <Note>
    fun createNote(editedNote: Note){
        val values = ContentValues()
        values.put(COLUMN_TITLE, editedNote.title)
        values.put(COLUMN_NOTE, editedNote.noteText)
        val db = this.writableDatabase
        try {
            db.insert(TABLE_NAME, null, values)
        } catch (e:Exception){
            Log.d("AppProcess", "Exception while creating note: INSERT in db ")
        }
        db.close()
    }

    // Заполняет AddNoteActivity, если была вызвана существующая заметка
    fun takeNote(id: Int): Note{
        val db = this.writableDatabase
        val query = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_ID=$id"
        val cursor = db.rawQuery(query, null)
        cursor.moveToFirst()
        val note = fillNoteFromDb(cursor)
        cursor.close()
        db.close()
        return note
    }


    // Обновление заметки в базе данных
    fun saveNote(editedNote: Note){
        val values = ContentValues()
        values.put(COLUMN_TITLE, editedNote.title)
        values.put(COLUMN_NOTE, editedNote.noteText)
        val db = this.writableDatabase
        db.update(TABLE_NAME,values, COLUMN_ID + "=?", arrayOf(editedNote.id.toString()))
        db.close()
    }


    // Берем все заметки из базы данных и выводим в виде масива элементов <Note>
    fun getNotes(): ArrayList<Note> {
        val query = "SELECT * FROM $TABLE_NAME"
        val db = this.writableDatabase
        val cursor = db.rawQuery(query, null)
        val notes = ArrayList<Note>()
        if (cursor.count != 0) {
            while (cursor.moveToNext()) {
                notes.add(fillNoteFromDb(cursor))
            }
        }
        cursor.close()
        db.close()
        return notes
    }

    // Заполнение класса <Note> даными из БД
    private fun fillNoteFromDb(cursor: Cursor): Note{
        val noteId = cursor.getInt(cursor.getColumnIndex(COLUMN_ID))
        val noteTitle = cursor.getString(cursor.getColumnIndex(COLUMN_TITLE))
        val noteText = cursor.getString(cursor.getColumnIndex(COLUMN_NOTE))
        return Note(noteId, noteTitle, noteText)
    }

    // Определение значения id для элемента <Note>
    fun getNoteId(): Int{
        val query = "SELECT $COLUMN_ID FROM $TABLE_NAME"
        val db = this.writableDatabase
        val cursor = db.rawQuery(query, null)
        var currentId = 1
        if (cursor.count != 0) {
            cursor.moveToLast()
            currentId = cursor.getInt(cursor.getColumnIndex(COLUMN_ID)) + 1
        }
        cursor.close()
        db.close()
        return currentId
    }
}