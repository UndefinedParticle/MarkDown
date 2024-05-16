package com.chinmoy09ine.markdown.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.chinmoy09ine.markdown.R
import com.chinmoy09ine.markdown.adapters.NotesAdapter
import com.chinmoy09ine.markdown.database.NotesTable
import com.chinmoy09ine.markdown.databinding.ActivityMainBinding
import com.chinmoy09ine.markdown.models.MainViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var notes: NotesTable
    private lateinit var notesList:ArrayList<NotesTable>
    private lateinit var notesAdapter: NotesAdapter
    private lateinit var mainViewModel: MainViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        changeStatusBarColor()
        /*getSharedPreferences("MARKDOWN_PASSWORD", Context.MODE_PRIVATE)
            .edit()
            .clear()
            .apply()*/

        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]
        notes = NotesTable()
        notesList = ArrayList()
        notesAdapter = NotesAdapter(this@MainActivity, notesList)
        binding.recyclerView.adapter = notesAdapter


        binding.backButton.setOnClickListener {

            onBackPressed()

        }

        binding.addButton.setOnClickListener {

            startActivity(Intent(this@MainActivity, NoteActivity::class.java)
                .putExtra("comingFrom", "add"))

        }

        observeLiveData()
    }

    private fun observeLiveData() {
        mainViewModel.getAllNotes.observe(this, Observer { notes ->

            if (notes != null) {
                notesList.clear()

                val tempList = notes as ArrayList<NotesTable>
                notesList.addAll(tempList)

                notesList.sortWith(compareByDescending<NotesTable> { it.isPinned != 0L } // Sorting by isPinned
                    .thenByDescending { it.updatedAt }) // Then sorting by updatedAt if isPinned values are equal

                notesAdapter.notifyDataSetChanged()

                if (notesList.isEmpty()) {
                    binding.emptyList.visibility = View.VISIBLE
                    binding.recyclerView.visibility = View.GONE
                } else {
                    binding.emptyList.visibility = View.GONE
                    binding.recyclerView.visibility = View.VISIBLE
                }

                Log.d("notesTable", "notesList.size = ${notesList.size}")
            } else {
                binding.emptyList.visibility = View.VISIBLE
                binding.recyclerView.visibility = View.GONE

                Log.d("notesTable", "notesList = null")
            }

        })

    }


    private fun changeStatusBarColor() {
        val window: Window = window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, R.color.darkModeBG)
    }

    override fun onBackPressed() {
        finishAffinity()
    }
}