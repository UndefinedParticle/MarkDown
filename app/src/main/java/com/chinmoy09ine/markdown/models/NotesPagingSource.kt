package com.chinmoy09ine.markdown.models

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.chinmoy09ine.markdown.database.NotesTable
import com.chinmoy09ine.markdown.database.NotesDao

class NotesPagingSource(
    private val notesDao: NotesDao
) : PagingSource<Int, NotesTable>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, NotesTable> {
        return try {
            val page = params.key ?: 0
            val pageSize = params.loadSize
            val notes = notesDao.getNotesPaged(page * pageSize, pageSize)
            LoadResult.Page(
                data = notes,
                prevKey = if (page == 0) null else page - 1,
                nextKey = if (notes.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, NotesTable>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}
