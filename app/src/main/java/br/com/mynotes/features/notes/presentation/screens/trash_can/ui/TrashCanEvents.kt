package br.com.mynotes.features.notes.presentation.screens.trash_can.ui

sealed class TrashCanEvents {
    object CloseAutoDeleteMessage : TrashCanEvents()
    object ClearTrashCan : TrashCanEvents()
    object DeleteNotes : TrashCanEvents()
    object RestoreFromTrashCan : TrashCanEvents()
}