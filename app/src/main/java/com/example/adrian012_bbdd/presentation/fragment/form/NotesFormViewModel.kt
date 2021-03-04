package com.example.adrian012_bbdd.presentation.fragment.form

import com.example.adrian012_bbdd.base.BaseViewModel
import com.example.adrian012_bbdd.base.util.BaseExtraData
import com.example.adrian012_bbdd.domain.model.NoteDomainModel
import com.example.adrian012_bbdd.domain.repository.NoteRepository
import com.example.adrian012_bbdd.presentation.fragment.form.NotesFormFragment.Companion.CODE_ALRIGHT
import com.example.adrian012_bbdd.presentation.fragment.form.NotesFormFragment.Companion.CODE_CONFIRM_UPDATE
import com.example.adrian012_bbdd.presentation.fragment.form.NotesFormFragment.Companion.CODE_EXIT
import com.example.adrian012_bbdd.presentation.fragment.form.NotesFormFragment.Companion.FIELD_KEY_ALL
import com.example.adrian012_bbdd.presentation.fragment.form.NotesFormFragment.Companion.FIELD_KEY_BODY
import com.example.adrian012_bbdd.presentation.fragment.form.NotesFormFragment.Companion.FIELD_KEY_TITLE
import com.example.adrian012_bbdd.presentation.fragment.list.NotesListState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class NotesFormViewModel @Inject constructor(private val repository: NoteRepository): BaseViewModel<NotesFormState>() {
    override val defaultState: NotesFormState = NotesFormState()


    override fun onStartFirstTime() {

    }


    fun setNote(inputNote: NoteDomainModel?) {
        inputNote?.let { noteToEdit ->
            updateToNormalState(NotesFormState(noteToEdit.title, noteToEdit.body, noteToEdit))
        }
    }


    fun onActionSetTitle(text: String) {
        checkDataState { state ->
            if (text != state.title) {
                updateDataState(state.copy(title = text))
            }
        }
    }


    fun onActionSetBody(text: String) {
        checkDataState { state ->
            if (text != state.body) {
                updateDataState(state.copy(body = text))
            }
        }
    }

    fun onActionSaveNote() {
        updateToLoadingState()

        checkDataState { state ->
            if(state.title.isNotEmpty() && state.body.isNotEmpty()) {
                executeCoroutines({
                    if(state.note == null) {
                        repository.insert(NoteDomainModel(title = state.title, body = state.body))
                        updateToLoadingState(BaseExtraData(CODE_ALRIGHT))
                    } else {
                        updateToLoadingState(BaseExtraData(CODE_CONFIRM_UPDATE))
                    }
                }, {})
            } else {
                val msg = when {
                    state.title.isEmpty() && state.body.isEmpty() -> FIELD_KEY_ALL
                    state.title.isEmpty() -> FIELD_KEY_TITLE
                    state.body.isEmpty() -> FIELD_KEY_BODY
                    else -> ""
                }

                updateToErrorState(FieldErrorException(msg))
            }
        }
    }


    fun onActionUpdateNote() {
        checkDataState { state ->
            executeCoroutines({
                if(state.note != null) {
                    repository.update(state.note.copy(title = state.title, body = state.body))
                    updateToLoadingState(BaseExtraData(CODE_EXIT))
                }
            }, {})

        }

    }
}