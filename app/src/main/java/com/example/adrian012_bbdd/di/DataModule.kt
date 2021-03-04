package com.example.adrian012_bbdd.di

import com.example.adrian012_bbdd.data.config.NotesDatabase
import com.example.adrian012_bbdd.data.notes.respository.local.NoteLocal
import com.example.adrian012_bbdd.domain.repository.NoteRepository
import com.example.adrian012_bbdd.data.notes.respository.NoteRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    @Provides
    fun provideNoteRepository(notesDatabase: NotesDatabase): NoteRepository = NoteRepositoryImpl(NoteLocal(notesDatabase.loadDatabase().noteDao()))
}