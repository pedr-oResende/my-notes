package br.com.mynotes.features.notes.domain.model

import android.os.Parcelable
import androidx.room.Entity
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
    val isFixed: Boolean,
    val isSelected: Boolean
) : Parcelable

val notes = listOf(
    Note(
        id = 1,
        title = "title",
        content = "contentcontentcontentcontentcontentcontentcontentcontentcontent",
        createAt = "2022-08-10",
        timestamp = 1000L,
        isArchived = false,
        isFixed = true,
        isSelected = false
    ),
    Note(
        id = 2,
        title = "titletitle",
        content = "contentcontentcontentcontentcontentcontentcontentcontentcontent" +
                "contentcontentcontentcontentcontentcontentcontentcontentcontent" + "contentcontentcontentcontentcontentcontentcontentcontentcontent" +
                "contentcontentcontentcontentcontentcontentcontentcontentcontent"+ "contentcontentcontentcontentcontentcontentcontentcontentcontent" +
                "contentcontentcontentcontentcontentcontentcontentcontentcontent"+ "contentcontentcontentcontentcontentcontentcontentcontentcontent" +
                "contentcontentcontentcontentcontentcontentcontentcontentcontent",
        createAt = "2021-08-10",
        timestamp = 2000L,
        isArchived = false,
        isFixed = false,
        isSelected = false
    ),
    Note(
        id = 3,
        title = "title",
        content = "contentcontentcontentcontentcontentcontentcontentcontentcontent"+ "contentcontentcontentcontentcontentcontentcontentcontentcontent",
        createAt = "2022-08-10",
        timestamp = 1000L,
        isArchived = false,
        isFixed = false,
        isSelected = false
    ),
    Note(
        id = 4,
        title = "titletitle",
        content = "contentcontentcontentcontentcontentcontentcontentcontentcontent",
        createAt = "2021-08-10",
        timestamp = 2000L,
        isArchived = false,
        isFixed = false,
        isSelected = false
    ),
    Note(
        id = 5,
        title = "title",
        content = "contentcontentcontentcontentcontentcontentcontentcontentcontent",
        createAt = "2022-08-10",
        timestamp = 1000L,
        isArchived = false,
        isFixed = false,
        isSelected = false
    ),
    Note(
        id = 6,
        title = "titletitle",
        content = "contentcontentcontentcontentcontentcontentcontentcontentcontent" +
                "contentcontentcontentcontentcontentcontentcontentcontentcontent" + "contentcontentcontentcontentcontentcontentcontentcontentcontent" +
                "contentcontentcontentcontentcontentcontentcontentcontentcontent"+ "contentcontentcontentcontentcontentcontentcontentcontentcontent" +
                "contentcontentcontentcontentcontentcontentcontentcontentcontent"+ "contentcontentcontentcontentcontentcontentcontentcontentcontent" +
                "contentcontentcontentcontentcontentcontentcontentcontentcontent",
        createAt = "2021-08-10",
        timestamp = 2000L,
        isArchived = true,
        isFixed = false,
        isSelected = false
    ),
    Note(
        id = 7,
        title = "title",
        content = "contentcontentcontentcontentcontentcontentcontentcontentcontent"+ "contentcontentcontentcontentcontentcontentcontentcontentcontent",
        createAt = "2022-08-10",
        timestamp = 1000L,
        isArchived = false,
        isFixed = false,
        isSelected = false
    ),
    Note(
        id = 8,
        title = "titletitle",
        content = "contentcontentcontentcontentcontentcontentcontentcontentcontent",
        createAt = "2021-08-10",
        timestamp = 2000L,
        isArchived = false,
        isFixed = false,
        isSelected = false
    ),
    Note(
        id = 9,
        title = "title",
        content = "contentcontentcontentcontentcontentcontentcontentcontentcontent",
        createAt = "2022-08-10",
        timestamp = 1000L,
        isArchived = true,
        isFixed = false,
        isSelected = false
    ),
    Note(
        id = 10,
        title = "titletitle",
        content = "contentcontentcontentcontentcontentcontentcontentcontentcontent" +
                "contentcontentcontentcontentcontentcontentcontentcontentcontent" + "contentcontentcontentcontentcontentcontentcontentcontentcontent" +
                "contentcontentcontentcontentcontentcontentcontentcontentcontent"+ "contentcontentcontentcontentcontentcontentcontentcontentcontent" +
                "contentcontentcontentcontentcontentcontentcontentcontentcontent"+ "contentcontentcontentcontentcontentcontentcontentcontentcontent" +
                "contentcontentcontentcontentcontentcontentcontentcontentcontent",
        createAt = "2021-08-10",
        timestamp = 2000L,
        isArchived = true,
        isFixed = false,
        isSelected = false
    ),
    Note(
        id = 11,
        title = "title",
        content = "contentcontentcontentcontentcontentcontentcontentcontentcontent"+ "contentcontentcontentcontentcontentcontentcontentcontentcontent",
        createAt = "2022-08-10",
        timestamp = 1000L,
        isArchived = false,
        isFixed = false,
        isSelected = false
    ),
    Note(
        id = 12,
        title = "titletitle",
        content = "content",
        createAt = "2021-08-10",
        timestamp = 5000L,
        isArchived = false,
        isFixed = false,
        isSelected = false
    )
).sortedByDescending { it.timestamp }