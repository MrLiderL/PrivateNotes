package com.kirpoltoradnev.privatenotes.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.kirpoltoradnev.privatenotes.db.Note
import com.kirpoltoradnev.privatenotes.utils.NoteRepository
import com.kirpoltoradnev.privatenotes.extensions.mutableLiveNote as mutableLiveNote

class MainViewModel: ViewModel() {
    private val noteRepository = NoteRepository()

    fun getNoteData(): LiveData<List<Note>> {
        return mutableLiveNote(loadNotes())
    }

    private fun loadNotes(): List<Note> {
        val notes = noteRepository.loadNotes()
        return notes
    }
}