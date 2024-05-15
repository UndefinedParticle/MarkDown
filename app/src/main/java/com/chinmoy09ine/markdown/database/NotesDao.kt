package com.chinmoy09ine.markdown.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface NotesDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertNote(note: NotesTable)

    @Query("UPDATE NOTES_TABLE SET title =:title, description =:description, updatedAt=:updatedAt, isLocked=:isLocked, isPinned=:isPinned, bgColor=:bgColor WHERE noteId = :noteId")
    suspend fun updateNote(
        noteId: String,
        title: String,
        description: String,
        updatedAt: Long,
        isLocked: Int,
        isPinned: Long,
        bgColor: String
    )

    @Query("DELETE FROM NOTES_TABLE WHERE noteId = :noteId")
    suspend fun deleteNote(noteId: String)

    @Query("SELECT * FROM NOTES_TABLE WHERE noteId = :noteId")
    suspend fun findNote(noteId: String): List<NotesTable>

    @Query("SELECT * FROM NOTES_TABLE ORDER BY isPinned DESC")
    fun getAllNotes(): LiveData<List<NotesTable>>

}