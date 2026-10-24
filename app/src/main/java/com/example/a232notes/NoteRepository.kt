package com.example.a232notes

import androidx.lifecycle.LiveData


 class NoteRepository (private val dao: NoteDao){

     var allNotes : LiveData<List<Note>> =  dao.getAllNotes()


     suspend fun insertNote (note: Note) {
         dao.insertNote(note)
     }

     suspend fun updateNote (note: Note) {
         dao.updateNote(note)
     }

     suspend fun deleteNote (note: Note) {
         dao.deleteNote(note)
     }

     suspend fun deleteAllNotes () {
         dao.deleteAllNotes()
     }

}