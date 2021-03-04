package com.example.adrian012_bbdd.data.notes.respository

import com.example.adrian012_bbdd.data.notes.model.toDataModel
import com.example.adrian012_bbdd.data.notes.model.toDomainModel
import com.example.adrian012_bbdd.data.notes.respository.local.NoteLocal
import com.example.adrian012_bbdd.domain.model.NoteDomainModel
import com.example.adrian012_bbdd.domain.repository.NoteRepository
import javax.inject.Inject


class NoteRepositoryImpl @Inject constructor(private val local: NoteLocal): NoteRepository {
    override suspend fun getAll(): List<NoteDomainModel> {
        return local.getAll().map { it.toDomainModel() }
    }

    override suspend fun insert(note: NoteDomainModel) {
        local.insert(note.toDataModel())
    }

    override suspend fun update(note: NoteDomainModel) {
        local.update(note.toDataModel())
    }

    override suspend fun delete(note: NoteDomainModel) {
        local.delete(note.toDataModel())
    }
}