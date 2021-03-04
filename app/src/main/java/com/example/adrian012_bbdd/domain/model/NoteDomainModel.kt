package com.example.adrian012_bbdd.domain.model

import java.io.Serializable


data class NoteDomainModel(
    val id: Int = 0,
    val title: String = "",
    val body: String = ""
): Serializable
