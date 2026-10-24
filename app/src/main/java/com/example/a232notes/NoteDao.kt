package com.example.a232notes

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface NoteDao {

    @Insert
     suspend fun insertNote (note : Note)


     @Delete
     suspend fun deleteNote(note: Note)

     @Update
     suspend fun updateNote (note :Note)

     @Query("DELETE FROM notes_table")
     suspend fun deleteAllNotes()


     @Query("SELECT * FROM notes_table ORDER BY priority ASC")
        fun getAllNotes () : LiveData<List<Note>>



}