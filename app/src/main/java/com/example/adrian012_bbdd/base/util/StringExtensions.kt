package com.example.adrian012_bbdd.base.util

import android.util.Base64

fun String.toBase64(): String = Base64.encodeToString(this.toByteArray(charset("UTF-8")), Base64.DEFAULT)

