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
        executeCoroutines({
            repository.insert(NoteDomainModel(title = "Sample of title 1", body = "Sample of body 1"))
            repository.insert(NoteDomainModel(title = "Sample of title 2", body = "Sample of body 2"))
            repository.insert(NoteDomainModel(title = "Sample of title 3", body = "Sample of body 3"))
            repository.insert(NoteDomainModel(title = "Sample of title 4", body = "Sample of body 4"))
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