package com.example.adrian012_bbdd.presentation.fragment.form

import com.example.adrian012_bbdd.base.BaseViewState

// Here we show all the attributes shown on my screen
data class NotesFormState (
    val title: String = "",
    val body: String = ""
): BaseViewState()
