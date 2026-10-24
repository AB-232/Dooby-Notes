package com.example.a232notes

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes_table")
data class Note(
    var title: String,
    var description: String,
    var priority: Int
) {

    @PrimaryKey(autoGenerate = true)
    var id = 0
}