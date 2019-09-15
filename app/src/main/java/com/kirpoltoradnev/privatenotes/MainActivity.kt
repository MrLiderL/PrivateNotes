package com.kirpoltoradnev.privatenotes

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kirpoltoradnev.privatenotes.adapter.CustomAdapter
import com.kirpoltoradnev.privatenotes.db.DBOpenHelper
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    companion object{
        lateinit var dbHadler: DBOpenHelper
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dbHadler = DBOpenHelper(this, null)

        viewNotes()

        buttonCreateNote.setOnClickListener {
            val intent = Intent(this, AddNoteActivity::class.java)
            startActivity(intent)
        }

    }

    private fun viewNotes(){
        val notesList = dbHadler.getNotes(this)
        val adapter = CustomAdapter(this, notesList)
        recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        recyclerView.adapter = adapter
    }

    override fun onPause() {
        viewNotes()
        super.onPause()
    }
}
