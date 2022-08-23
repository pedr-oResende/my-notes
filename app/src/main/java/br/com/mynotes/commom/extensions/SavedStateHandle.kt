package br.com.mynotes.commom.extensions

import androidx.lifecycle.SavedStateHandle

fun <T> SavedStateHandle.getArgument(key: String): T? {
    val argument = get<T>(key = key)
    remove<T>(key = key)
    return argument
}

fun <T> SavedStateHandle.putArgument(key: String, argument: T?) {
    set(key = key, value = argument)
}