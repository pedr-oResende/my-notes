package br.com.mynotes.di

import br.com.mynotes.features.notes.presentation.screens.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModules = module {
    viewModel {
        HomeViewModel()
    }
}