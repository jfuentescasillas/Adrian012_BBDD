package com.example.adrian012_bbdd.data.notes.respository.local

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update
import com.example.adrian012_bbdd.data.notes.model.NoteEntityDataModel
import javax.inject.Inject

class NoteLocal @Inject constructor(private val dao: NoteDAO) {
    suspend fun getAll(): List<NoteEntityDataModel> {
        return dao.getAll()
    }

    suspend fun insert(note: NoteEntityDataModel) {
        dao.insert(note)
    }

    suspend fun update(note: NoteEntityDataModel) {
        dao.update(note)
    }

    suspend fun delete(note: NoteEntityDataModel) {
        dao.delete(note)
    }
}