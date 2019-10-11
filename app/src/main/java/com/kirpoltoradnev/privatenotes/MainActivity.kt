package com.kirpoltoradnev.privatenotes

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBar
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.navigation.NavigationView
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
        setSupportActionBar(toolbar)
        setupToollbar(supportActionBar)

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
        setUpNavigationView()
        fab.setOnClickListener {
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

    private fun setupToollbar(setSupportActionBar: ActionBar?){
        val actionBar = setSupportActionBar
        actionBar!!.title = "PrivateNotes"
        actionBar.setDisplayShowHomeEnabled(true)
    }

    private fun setUpNavigationView(){
        nav_view.itemIconTintList = null
        nav_view.setNavigationItemSelectedListener { menuItem ->
            // set item as selected to persist highlight
            menuItem.isChecked = true
            // close drawer when item is tapped
            drawer_layout.closeDrawers()
            // Handle navigation view item clicks here.
            when (menuItem.itemId) {

                R.id.nav_tokenizer -> {
                    Toast.makeText(this, "Profile", Toast.LENGTH_LONG).show()
                }
                R.id.nav_wallet -> {
                    Toast.makeText(this, "Wallet", Toast.LENGTH_LONG).show()
                }
                R.id.nav_setting -> {
                    Toast.makeText(this, "Setting", Toast.LENGTH_LONG).show()
                }
            }
            // Add code here to update the UI based on the item selected
            // For example, swap UI fragments here
            true
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.user_info_menu, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_more_info -> {
            drawer_layout.openDrawer(GravityCompat.START)
            true
        }

        else ->
            super.onOptionsItemSelected(item)
    }
}
