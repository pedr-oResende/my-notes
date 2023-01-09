package br.com.mynotes.commom.extensions

infix fun <T> T?.ifNull(other: T): T = this ?: other