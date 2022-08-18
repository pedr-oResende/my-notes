package br.com.mynotes.features.notes.domain.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "note")
@Parcelize
data class Note(
    @PrimaryKey val id: Int? = null,
    val title: String,
    val content: String,
    val createAt: String,
    val timestamp: Long,
    val isArchived: Boolean,
    val isDeleted: Boolean,
    val isFixed: Boolean,
    @Ignore val isSelected: Boolean
) : Parcelable {
    constructor(
        id: Int?,
        title: String,
        content: String,
        createAt: String,
        timestamp: Long,
        isArchived: Boolean,
        isFixed: Boolean,
        isDeleted: Boolean
    ) : this(
        id = id,
        title = title,
        content = content,
        createAt = createAt,
        timestamp = timestamp,
        isArchived = isArchived,
        isFixed = isFixed,
        isSelected = false,
        isDeleted = isDeleted
    )
}