package com.example.adrian012_bbdd.data.config

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.adrian012_bbdd.data.notes.model.NoteEntityDataModel
import com.example.adrian012_bbdd.data.notes.respository.local.NoteDAO


@Database(entities = [NoteEntityDataModel::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun noteDao(): NoteDAO
}