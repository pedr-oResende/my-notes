package br.com.mynotes.features.notes.presentation.screens.archive

import androidx.lifecycle.ViewModel
import br.com.mynotes.features.notes.domain.use_case.ArchiveNoteUseCases
import br.com.mynotes.features.notes.presentation.screens.home.HomeViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ArchiveViewModel @Inject constructor(
    archiveNoteUseCases: ArchiveNoteUseCases
): ViewModel() {

}