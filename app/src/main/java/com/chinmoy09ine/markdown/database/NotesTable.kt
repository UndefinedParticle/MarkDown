package com.chinmoy09ine.markdown.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "NOTES_TABLE")
data class NotesTable(
    @PrimaryKey
    @ColumnInfo(name = "noteId")
    var noteId: String = "",

    @ColumnInfo
    var title: String = "",

    @ColumnInfo
    var description: String = "",

    @ColumnInfo
    var createdAt: Long = 0,

    @ColumnInfo
    var updatedAt: Long = 0,

    @ColumnInfo
    var isLocked: Int = 0, // 0 -> false, 1 -> true

    @ColumnInfo
    var isPinned: Long = 0, // 0 -> false, !0 -> true

    @ColumnInfo
    var bgColor: String = "0"
)