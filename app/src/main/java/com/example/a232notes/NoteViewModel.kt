package com.example.a232notes

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch


class NoteViewModel(application: Application) : AndroidViewModel(application) {

     private var repository: NoteRepository
     var allNotes : LiveData<List<Note>>

    init {
        val dao = NoteDataBase.getDataBaseInstant(application.applicationContext).getNoteDoa()
        repository = NoteRepository(dao)
        allNotes =repository.allNotes

    }

    fun addNote (note: Note) =
       viewModelScope.launch {
           repository.insertNote(note)
       }

        fun updateNote (note: Note) =
       viewModelScope.launch {
           repository.updateNote(note)
       }

        fun deleteNote (note: Note) =
       viewModelScope.launch {
           repository.deleteNote(note)
       }
            fun deleteAllNotes () =
       viewModelScope.launch {
           repository.deleteAllNotes()
       }


}