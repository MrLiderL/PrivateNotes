package com.kirpoltoradnev.privatenotes

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.kirpoltoradnev.privatenotes.adapter.CustomAdapter
import com.kirpoltoradnev.privatenotes.db.DBOpenHelper
import com.kirpoltoradnev.privatenotes.db.Note
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var customAdapter: CustomAdapter
    private lateinit var dbHandler: DBOpenHelper

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dbHandler = DBOpenHelper(this, null)

        initViews()

        initViewModel()

        setUpUtils()

    }

    // Обновление (заполнение) RecyclerView после нового открытия MainActivity
    override fun onResume() {
        initViewModel()
        super.onResume()
    }

    // Инициализация списка RecyclerView
    private fun initViews(){
        customAdapter = CustomAdapter{
            intentQueryToAddNote(it)
        }
        val divider = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        with(recyclerView){
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = customAdapter
            addItemDecoration(divider)
        }
    }

    // Инициализация моделей списка (заполнение) RecyclerView
    private fun initViewModel(){
        customAdapter.updateData(dbHandler.getNotes())
    }

    // Обработчики кликов и взаимодействие с View
    private fun setUpUtils(){
        buttonCreateNote.setOnClickListener {
            intentQueryToAddNote(null)
        }
    }

    // Отправка Intent в AddNoteActivity. Если аругумент не null - отправляем
    // не пустой intent (т.е. открываем существующую заметку)
    private fun intentQueryToAddNote(it: Note?){
        val intent = Intent(this, AddNoteActivity::class.java)
        if (it != null) {
            intent.putExtra("id", it.id.toString())
        }
        startActivity(intent)
    }
}
