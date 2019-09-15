package com.kirpoltoradnev.privatenotes.db

class Note {
    var id: Int = 0
    var title: String? = null
    var noteText: String? = null

    constructor(id: Int, titleGiven: String, noteGiven: String) {
        this.id = id
        this.title = titleGiven
        this.noteText = noteGiven
    }

    constructor(titleGiven: String, noteGiven: String){
        this.title = titleGiven
        this.noteText = noteGiven
    }

    fun updateNote(titleNew: String?, noteNew: String?){
        this.id = id
        this.title = titleNew
        this.noteText = noteNew
    }
}
