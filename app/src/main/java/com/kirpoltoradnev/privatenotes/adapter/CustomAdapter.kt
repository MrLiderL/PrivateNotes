package com.kirpoltoradnev.privatenotes.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kirpoltoradnev.privatenotes.R
import com.kirpoltoradnev.privatenotes.db.Note

import kotlinx.android.synthetic.main.layout_note.view.*

class CustomAdapter(): RecyclerView.Adapter<CustomAdapter.NoteViewHolder>() {

    var notesList: List<Note> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val convertView = inflater.inflate(R.layout.layout_note, parent, false)
        return NoteViewHolder(convertView)
    }

    override fun getItemCount(): Int = notesList.size

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(notesList[position])
    }

    fun updateData(data: List<Note>){
        notesList = data
        notifyDataSetChanged()
    }

    inner class NoteViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val noteViewTitle = itemView.noteViewTitle
        val noteViewText = itemView.noteViewText

        fun bind(item: Note) {
            noteViewTitle.text = item.title
            noteViewText.text = item.noteText
        }
    }
}