package com.example.adrian012_bbdd.presentation.fragment.list

import com.example.adrian012_bbdd.base.BaseViewModel
import com.example.adrian012_bbdd.domain.model.NoteDomainModel
import com.example.adrian012_bbdd.domain.repository.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class NotesListViewModel @Inject constructor(private val repository: NoteRepository): BaseViewModel<NotesListState>() {
    override val defaultState: NotesListState = NotesListState()


    override fun onStartFirstTime() {

    }


    override fun onResume() {
        executeCoroutines({
            val notes = repository.getAll()

            updateToNormalState(NotesListState(notes))
        }, {

        })
    }


    fun onActionDeleteNote(item: NoteDomainModel) {
        executeCoroutines({
            repository.delete((item))
            val notes = repository.getAll()

            checkDataState { state ->
                updateToNormalState(state.copy(notesList = notes))
            }
        }, {

        })
    }
}