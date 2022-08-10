package br.com.mynotes.features.notes.presentation.util

sealed class OrderType {
    object Ascending: OrderType()
    object Descending: OrderType()
}
