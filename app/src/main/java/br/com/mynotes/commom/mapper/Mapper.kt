package br.com.mynotes.commom.mapper

interface Mapper<I, O> {
    fun map(input: I): O
}