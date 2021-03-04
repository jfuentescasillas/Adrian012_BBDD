package com.example.adrian012_bbdd.presentation.fragment.form

import com.example.adrian012_bbdd.base.BaseViewModel
import com.example.adrian012_bbdd.presentation.fragment.list.NotesListState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class NotesFormViewModel @Inject constructor(): BaseViewModel<NotesFormState>() {
    override val defaultState: NotesFormState = NotesFormState()


    override fun onStartFirstTime() {

    }


    fun onActionSetTitle(text: String) {
        
    }


    fun onActionSetBody(text: String) {
        checkDataState { state ->
            if (text != state.body) {
                updateToNormalState(state.copy(body = text))
            }
        }
    }

    fun onActionSaveNote() {
    }
}