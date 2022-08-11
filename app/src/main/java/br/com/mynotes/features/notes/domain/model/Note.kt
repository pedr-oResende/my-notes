package br.com.mynotes.features.notes.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Note(
    @PrimaryKey val id: Int? = null,
    val title: String,
    val content: String,
    val createAt: String,
    val timestamp: Long
)

val notes = listOf(
    Note(
        id = 1,
        title = "title",
        content = "contentcontentcontentcontentcontentcontentcontentcontentcontent",
        createAt = "2022-08-10",
        timestamp = 1000L
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
        timestamp = 2000L
    ),
    Note(
        id = 3,
        title = "title",
        content = "contentcontentcontentcontentcontentcontentcontentcontentcontent"+ "contentcontentcontentcontentcontentcontentcontentcontentcontent",
        createAt = "2022-08-10",
        timestamp = 1000L
    ),
    Note(
        id = 4,
        title = "titletitle",
        content = "contentcontentcontentcontentcontentcontentcontentcontentcontent",
        createAt = "2021-08-10",
        timestamp = 2000L
    ),
    Note(
        id = 5,
        title = "title",
        content = "contentcontentcontentcontentcontentcontentcontentcontentcontent",
        createAt = "2022-08-10",
        timestamp = 1000L
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
        timestamp = 2000L
    ),
    Note(
        id = 7,
        title = "title",
        content = "contentcontentcontentcontentcontentcontentcontentcontentcontent"+ "contentcontentcontentcontentcontentcontentcontentcontentcontent",
        createAt = "2022-08-10",
        timestamp = 1000L
    ),
    Note(
        id = 8,
        title = "titletitle",
        content = "contentcontentcontentcontentcontentcontentcontentcontentcontent",
        createAt = "2021-08-10",
        timestamp = 2000L
    )
)