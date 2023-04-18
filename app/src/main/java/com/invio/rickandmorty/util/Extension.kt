package com.invio.rickandmorty.util

fun List<String>.parseIds(): String =
    this.joinToString(separator = ",") { url -> url.substringAfterLast("/") }

fun List<String>.parseIdsWithWhiteSpaces(): String =
    this.joinToString(separator = ", ") { url -> url.substringAfterLast("/") }