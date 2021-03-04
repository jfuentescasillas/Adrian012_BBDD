package com.example.adrian012_bbdd.data.config

import android.content.Context
import androidx.room.Room
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject


class NotesDatabase @Inject constructor(@ApplicationContext private val context: Context) {
    fun loadDatabase(): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "notes.db"). build()
    }
}