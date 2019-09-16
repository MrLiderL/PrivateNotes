package com.kirpoltoradnev.privatenotes

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.kirpoltoradnev.privatenotes.adapter.CustomAdapter
import com.kirpoltoradnev.privatenotes.db.DBOpenHelper
import com.kirpoltoradnev.privatenotes.model.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var customAdapter: CustomAdapter

    companion object{
        lateinit var dbHadler: DBOpenHelper
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dbHadler = DBOpenHelper(this, null)

        initViews() // TODO метод, создающий RecyclerView
        Log.d("TAG", "Fitst stage done!")
        initViewModel()
        Log.d("TAG", "Second stage done!")
        setUpUtils() // TODO метод, запускающий утилиты типа ClickListenr
        Log.d("TAG", "Third stage done!")

    }

    override fun onPause() {
        initViews()
        super.onPause()
    }


    private fun initViews(){

        customAdapter = CustomAdapter()

        with(recyclerView){
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = customAdapter
        }

    }

    private fun initViewModel(){
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        viewModel.getNoteData().observe(this, Observer { customAdapter.updateData(it) })
    }

    private fun setUpUtils(){
        buttonCreateNote.setOnClickListener {
            val intent = Intent(this, AddNoteActivity::class.java)
            startActivity(intent)
        }
    }
}
