package com.example.adrian012_bbdd.domain.repository

import com.example.adrian012_bbdd.domain.model.NoteDomainModel


interface NoteRepository {
    suspend fun getAll(): List<NoteDomainModel>
    suspend fun insert(note: NoteDomainModel)
    suspend fun update(note: NoteDomainModel)
    suspend fun delete(note: NoteDomainModel)
}