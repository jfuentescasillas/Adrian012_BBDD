package com.example.adrian012_bbdd.presentation.fragment.list

import com.example.adrian012_bbdd.base.BaseViewState
import com.example.adrian012_bbdd.domain.model.NoteDomainModel


data class NotesListState(
    val notesList: List<NoteDomainModel> = listOf()
): BaseViewState()