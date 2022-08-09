package br.com.mynotes.presentation.di

import br.com.mynotes.presentation.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModules = module {
    viewModel {
        HomeViewModel()
    }
}