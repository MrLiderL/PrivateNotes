package com.kirpoltoradnev.privatenotes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import androidx.appcompat.app.ActionBar
import com.kirpoltoradnev.privatenotes.db.DBOpenHelper
import com.kirpoltoradnev.privatenotes.db.Note
import kotlinx.android.synthetic.main.activity_add_note.*

class AddNoteActivity : AppCompatActivity() {

    companion object {
        lateinit var existedNote: Note
        lateinit var dbHandler: DBOpenHelper
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)

        setSupportActionBar(toolbar)
        setupToollbar(supportActionBar)

        dbHandler = DBOpenHelper(this, null)

        initNoteContent(dbHandler)

    }

    // Наполнение AddNoteActivity текстом из выбранного окна
    private fun initNoteContent(dbHandler: DBOpenHelper){
        val existChecker = intent.getStringExtra("id")
        if (existChecker != null){
            existedNote = dbHandler.takeNote(existChecker.toInt())
            titleNote.text = insertTextToEditText(existedNote.title)
            textNote.text = insertTextToEditText(existedNote.noteText)
        } else {
            existedNote = Note(dbHandler.getNoteId(),"No title", "")
            dbHandler.createNote(existedNote)
        }
    }

    // Логика safeButton.setOnClickListener
    private fun safeButtonAction(existedNote:Note, dbHandler:DBOpenHelper){
        safeNoteActivity(existedNote, dbHandler, titleNote.text.toString(), textNote.text.toString())
        finish()
    }

    // Сохранение заметки в базу данных
    private fun safeNoteActivity(existedNoteText: Note, db: DBOpenHelper, titleNew: String, textNew: String){
        existedNoteText.updateNote(titleNew, textNew)
        db.saveNote(existedNoteText)
    }

    // Имплементация текста типа String в EditText.text
    private fun insertTextToEditText(text: String?): Editable? = Editable.Factory.getInstance().newEditable(text)

    private fun setupToollbar(setSupportActionBar: ActionBar?){
        val actionBar = setSupportActionBar
        actionBar!!.title = ""
        actionBar.setDisplayShowHomeEnabled(true)
        actionBar.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        safeButtonAction(existedNote, dbHandler)
        return true
    }

    override fun onPause() {
        safeButtonAction(existedNote, dbHandler)
        super.onPause()
    }

}