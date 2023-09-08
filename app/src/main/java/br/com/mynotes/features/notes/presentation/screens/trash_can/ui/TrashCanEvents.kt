package br.com.mynotes.features.notes.presentation.screens.trash_can.ui

sealed class TrashCanEvents {
    data object CloseAutoDeleteMessage : TrashCanEvents()
    data object ClearTrashCan : TrashCanEvents()
    data object DeleteNotes : TrashCanEvents()
    data object RestoreFromTrashCan : TrashCanEvents()
}