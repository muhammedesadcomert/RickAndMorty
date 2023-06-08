package com.muhammedesadcomert.rickandmorty.util

import java.text.SimpleDateFormat
import java.util.Locale

fun List<String>.parseIds(): String =
    this.joinToString(separator = ",") { url -> url.substringAfterLast("/") }

fun List<String>.parseIdsWithWhiteSpaces(): String =
    this.joinToString(separator = ", ") { url -> url.substringAfterLast("/") }

fun String.formatDateString(): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
    val outputFormat = SimpleDateFormat("dd MMM yyyy, HH:mm:ss", Locale.getDefault())
    val date = inputFormat.parse(this)
    return date?.let { outputFormat.format(it) } ?: this
}