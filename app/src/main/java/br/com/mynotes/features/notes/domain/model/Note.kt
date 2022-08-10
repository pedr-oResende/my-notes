package br.com.mynotes.features.notes.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.mynotes.ui.theme.*

@Entity
data class Note(
    @PrimaryKey val id: Int? = null,
    val title: String,
    val content: String,
    val createAt: String,
    val timestamp: Long,
    val color: Int
) {
    companion object {
        val colors = listOf(null, Red, Blue, Orange, Green, Purple)
    }
}