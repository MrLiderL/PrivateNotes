package com.kirpoltoradnev.privatenotes.db

class Note {
    var id = 0
    var title: String? = null
    var noteText: String? = null

    constructor(id: Int, titleGiven: String, noteGiven: String) {
        this.id = id
        this.title = titleGiven
        this.noteText = noteGiven
    }

    // Обновление полей элемента <Note>
    fun updateNote(titleNew: String?, noteNew: String?){
        this.title = titleNew
        this.noteText = noteNew
    }

}
