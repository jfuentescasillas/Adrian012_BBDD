package com.example.adrian012_bbdd.presentation.fragment.form

import com.example.adrian012_bbdd.base.BaseViewState
import com.example.adrian012_bbdd.domain.model.NoteDomainModel

// Here we show all the attributes shown on my screen
data class NotesFormState (
    val title: String = "",
    val body: String = "",
    val note: NoteDomainModel? = null
): BaseViewState()
