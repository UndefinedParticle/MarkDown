package com.chinmoy09ine.markdown.models

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.chinmoy09ine.markdown.database.NotesTable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(application: Application): AndroidViewModel(application) {

    private val mainRepository: MainRepository = MainRepository(application)
    var getAllNotes: LiveData<List<NotesTable>> = mainRepository.getAllNotes
    var hasPassword: MutableLiveData<Boolean> = MutableLiveData(false)
    var deleting: MutableLiveData<Boolean> = MutableLiveData(false)

    fun insertNote(note: NotesTable) = viewModelScope.launch(Dispatchers.IO) {
        Log.d("notesTable", "insertTitle(viewModel): " + note.title)

        mainRepository.insertNote(note)

    }

    fun updateNote(
        noteId: String,
        title: String,
        description: String,
        updatedAt: Long,
        isLocked: Int,
        isPinned: Long,
        bgColor: String
    ) = viewModelScope.launch(Dispatchers.IO){
        Log.d("notesTable", "updatedTitle(viewModel): $title")

        mainRepository.updateNote(noteId, title, description, updatedAt, isLocked, isPinned, bgColor)


    }

    fun deleteNote(userId: String) = viewModelScope.launch(Dispatchers.IO) {

        mainRepository.deleteNote(userId)

    }

    suspend fun findNote(userId: String): List<NotesTable> {
        return mainRepository.findNote(userId)
    }

}