package com.example.a232notes

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Note::class], version = 1 , exportSchema = false)
abstract class NoteDataBase : RoomDatabase (){

    abstract fun getNoteDoa (): NoteDao


    companion object {

        private var INSTANCE : NoteDataBase? = null

        fun getDataBaseInstant (context: Context) : NoteDataBase{

            return INSTANCE ?: synchronized(this){

                val instant = Room.databaseBuilder(
                    context.applicationContext,
                    NoteDataBase::class.java,
                    "note_database"
                ).build()


                INSTANCE = instant
                instant
            }

        }



    }


}