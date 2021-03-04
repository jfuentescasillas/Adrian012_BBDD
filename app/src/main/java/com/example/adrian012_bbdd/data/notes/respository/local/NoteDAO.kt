package com.example.adrian012_bbdd.data.notes.respository.local

import androidx.room.*
import com.example.adrian012_bbdd.data.notes.model.NoteEntityDataModel


@Dao
interface NoteDAO {
    @Query("SELECT * FROM notes")
    suspend fun getAll(): List<NoteEntityDataModel>

    @Insert
    suspend fun insert(vararg users: NoteEntityDataModel)

    @Update
    suspend fun update(note: NoteEntityDataModel)

    @Delete
    suspend fun delete(user: NoteEntityDataModel)
}