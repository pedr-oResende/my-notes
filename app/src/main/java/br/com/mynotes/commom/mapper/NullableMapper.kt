package br.com.mynotes.commom.mapper

interface NullableMapper<I, O> {
    fun map(input: I?): O?
}