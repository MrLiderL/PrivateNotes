package com.kirpoltoradnev.privatenotes

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.kirpoltoradnev.privatenotes.adapter.CustomAdapter
import com.kirpoltoradnev.privatenotes.db.DBOpenHelper
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var customAdapter: CustomAdapter
    private lateinit var dbHadler: DBOpenHelper

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dbHadler = DBOpenHelper(this, null)

        initViews() // создающий RecyclerView

        initViewModel()

        setUpUtils()

    }

    override fun onPause() {
        initViews()
        super.onPause()
    }


    // Инициализация списка RecyclerView
    private fun initViews(){
        customAdapter = CustomAdapter()
        val divider = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        with(recyclerView){
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = customAdapter
            addItemDecoration(divider)
        }
    }

    // Инициализация моделей списка (заполнение) RecyclerView
    private fun initViewModel(){
        customAdapter.updateData(dbHadler.getNotes())

    }

    // Обработчики кликов и взаимодействие с View
    private fun setUpUtils(){
        buttonCreateNote.setOnClickListener {
            val intent = Intent(this, AddNoteActivity::class.java)
            startActivity(intent)
        }
    }
}
