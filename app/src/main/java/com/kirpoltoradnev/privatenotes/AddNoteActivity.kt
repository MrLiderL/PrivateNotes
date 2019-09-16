package com.kirpoltoradnev.privatenotes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.kirpoltoradnev.privatenotes.db.DBOpenHelper
import com.kirpoltoradnev.privatenotes.db.Note
import kotlinx.android.synthetic.main.activity_add_note.*

class AddNoteActivity : AppCompatActivity() {

    companion object {
        lateinit var existedNote: Note
        lateinit var dbHandelr: DBOpenHelper
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)

        existedNote = Note("No title", "")
        dbHandelr = DBOpenHelper(this, null)

        dbHandelr.createNote(existedNote)

        safeButton.setOnClickListener {
            safeNoteActivity(existedNote, dbHandelr, titleNote.text.toString(), textNote.text.toString())
            Toast.makeText(this,"Database was successfully updated", Toast.LENGTH_LONG).show()
        }

    }

    private fun safeNoteActivity(existedNoteText: Note, db: DBOpenHelper, titleNew: String, textNew: String){
        existedNoteText.updateNote(titleNew, textNew)
        db.saveNote(existedNoteText)
    }

}