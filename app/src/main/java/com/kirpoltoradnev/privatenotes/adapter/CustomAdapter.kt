package com.kirpoltoradnev.privatenotes.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kirpoltoradnev.privatenotes.R
import com.kirpoltoradnev.privatenotes.db.Note

import kotlinx.android.synthetic.main.layout_note.view.*

class CustomAdapter(mCtx: Context, val notesList: ArrayList<Note>): RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    val mCtx = mCtx


    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val noteViewTitle = itemView.noteViewTitle
        val noteViewText = itemView.noteViewText
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.layout_note, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount() = notesList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val note: Note = notesList[position]
        holder.noteViewTitle?.text = note.title
        holder.noteViewText?.text = note.noteText
    }
}