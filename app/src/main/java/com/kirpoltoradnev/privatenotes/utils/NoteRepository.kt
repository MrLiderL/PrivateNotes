package com.kirpoltoradnev.privatenotes.utils

import android.content.Context
import com.kirpoltoradnev.privatenotes.db.DBOpenHelper
import com.kirpoltoradnev.privatenotes.db.Note

class NoteRepository {

    companion object{
        private val instance = this

        fun applicationContext(): Context{
            return instance!!.applicationContext()
        }
    }

    fun loadNotes(): List<Note> {
        val context: Context = NoteRepository.applicationContext()
        val dbHendler = DBOpenHelper(context,null)
        return dbHendler.getNotes()
    }
}