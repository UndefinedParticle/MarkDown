package com.chinmoy09ine.markdown.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.chinmoy09ine.markdown.ConfirmPasswordDialog
import com.chinmoy09ine.markdown.R
import com.chinmoy09ine.markdown.database.NotesTable
import com.chinmoy09ine.markdown.databinding.ActivityNoteBinding
import com.chinmoy09ine.markdown.models.MainViewModel
import kotlinx.coroutines.launch


class NoteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNoteBinding
    private var isLocked = false
    private var isPinned = false
    private var colorLayout = false
    private lateinit var mainViewModel: MainViewModel
    private var notesTable: ArrayList<NotesTable>? = null
    private lateinit var note: NotesTable
    private var noteId = ""
    private var comingFrom = ""
    private var bgColor = "0"
    private var prevTitle = ""
    private var prevDesc = "0"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_note)
        binding.lifecycleOwner = this

        changeStatusBarColor(R.color.darkModeBG)
        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]
        note = NotesTable()

        binding.clickHandler = ClickHandler()
        binding.noteBg.setBackgroundColor(resources.getColor(R.color.darkModeBG))
        binding.defaultColor1.setBackgroundResource(R.drawable.circle_bg)
        binding.defaultSelected.visibility = View.VISIBLE


        if(intent != null){
            noteId = intent.getStringExtra("noteId").toString()
            comingFrom = intent.getStringExtra("comingFrom").toString()

            Log.d("notesTable", "noteId: $noteId")
        }

        lifecycleScope.launch {
            notesTable = mainViewModel.findNote(noteId) as ArrayList<NotesTable>
            if (notesTable!!.isNotEmpty()) { // Add a check to verify if the list is not empty
                note = notesTable!![0]

                if(note.isLocked == 1){
                    isLocked = true
                    binding.lockButton.setImageResource(R.drawable.locked_icon_new)
                }else{
                    isLocked = false
                    binding.lockButton.setImageResource(R.drawable.open_lock)
                }

                if(note.isPinned.toInt() == 0){
                    isPinned = false
                    binding.pinButton.setImageResource(R.drawable.pin_icon)
                }else{
                    isPinned = true
                    binding.pinButton.setImageResource(R.drawable.pinned_icon)
                }

                binding.titleId.setText(note.title)
                binding.descriptionId.setText(note.description)
                prevTitle = note.title
                prevDesc = note.description
                bgColor = note.bgColor

                Log.d("notesTable", "bgColor: $bgColor")
                defaultInit()
                when(bgColor.toInt()){

                    0 -> {
                        binding.noteBg.setBackgroundColor(resources.getColor(R.color.darkModeBG))
                        binding.defaultColor1.setBackgroundResource(R.drawable.circle_bg)
                        binding.defaultSelected.visibility = View.VISIBLE

                        changeStatusBarColor(R.color.darkModeBG)
                        //bgColor = "darkModeBG"
                    }
                    1 -> {
                        binding.noteBg.setBackgroundColor(resources.getColor(R.color.red))
                        binding.redColor1.setBackgroundResource(R.drawable.circle_bg)
                        binding.redSelected.visibility = View.VISIBLE

                        changeStatusBarColor(R.color.red)
                        //bgColor = "red"
                    }
                    2 -> {
                        binding.noteBg.setBackgroundColor(resources.getColor(R.color.greenColor))
                        binding.greenColor1.setBackgroundResource(R.drawable.circle_bg)
                        binding.greenSelected.visibility = View.VISIBLE

                        changeStatusBarColor(R.color.greenColor)
                        //bgColor = "greenColor"
                    }
                    3 -> {
                        binding.noteBg.setBackgroundColor(resources.getColor(R.color.blueColor))
                        binding.blueColor1.setBackgroundResource(R.drawable.circle_bg)
                        binding.blueSelected.visibility = View.VISIBLE

                        changeStatusBarColor(R.color.blueColor)
                        //bgColor = "blueColor"
                    }
                    4 -> {
                        binding.noteBg.setBackgroundColor(resources.getColor(R.color.yellowColor))
                        binding.yellowColor1.setBackgroundResource(R.drawable.circle_bg)
                        binding.yellowSelected.visibility = View.VISIBLE

                        changeStatusBarColor(R.color.yellowColor)
                        //bgColor = "yellowColor"
                    }
                    5 -> {
                        binding.noteBg.setBackgroundColor(resources.getColor(R.color.orangeColor))
                        binding.orangeColor1.setBackgroundResource(R.drawable.circle_bg)
                        binding.orangeSelected.visibility = View.VISIBLE

                        changeStatusBarColor(R.color.orangeColor)
                        //bgColor = "orangeColor"
                    }
                    6 -> {
                        binding.noteBg.setBackgroundColor(resources.getColor(R.color.violateColor))
                        binding.violateColor1.setBackgroundResource(R.drawable.circle_bg)
                        binding.violateSelected.visibility = View.VISIBLE

                        changeStatusBarColor(R.color.violateColor)
                        //bgColor = "violateColor"
                    }

                }


            }


        }

        binding.backButton.setOnClickListener {

            onBackPressed()

        }

        binding.saveButton.setOnClickListener {

            onSave()

        }

        binding.lockButton.setOnClickListener {

            if(!isLocked) {

                val password = getSharedPreferences(
                    "MARKDOWN_PASSWORD",
                    Context.MODE_PRIVATE
                ).getString("notePassword", "").toString()

                if (password.isEmpty()) {
                    val dialogFragment = ConfirmPasswordDialog(this@NoteActivity, "", mainViewModel)
                    dialogFragment.show(supportFragmentManager, "confirmPasswordDialog")
                } else{
                    binding.lockButton.setImageResource(R.drawable.locked_icon_new)
                    isLocked = !isLocked
                }

            }else{
                binding.lockButton.setImageResource(R.drawable.open_lock)
                isLocked = !isLocked
            }

        }

        binding.pinButton.setOnClickListener {

            if(!isPinned) {
                binding.pinButton.setImageResource(R.drawable.pinned_icon)
            }else{
                binding.pinButton.setImageResource(R.drawable.pin_icon)
            }

            isPinned = !isPinned

        }

        binding.colorButton.setOnClickListener {

            if(!colorLayout) {
                binding.colorLayout.visibility = View.VISIBLE
                binding.colorButton.setImageResource(R.drawable.colored_icon)
            }else{
                binding.colorLayout.visibility = View.GONE
                binding.colorButton.setImageResource(R.drawable.color_icon)
            }

            colorLayout = !colorLayout

        }

        binding.shareButton.setOnClickListener {

            val title: String = binding.titleId.text.toString().trim()
            val description: String = binding.descriptionId.text.toString().trim()
            shareNote(title, description)

        }

        observeLiveData()
    }

    private fun onSave() {
        Log.d("notesTable", "save noteId: $noteId")

        if(binding.titleId.text.toString().trim().isEmpty()){
            Toast.makeText(this@NoteActivity, "Title can't be empty!", Toast.LENGTH_SHORT).show()
            return
        }

        if(comingFrom == "add"){

            note.noteId = removeSpaces(binding.titleId.text.toString().trim()) + System.currentTimeMillis()
            note.title = binding.titleId.text.toString().trim()
            note.description = binding.descriptionId.text.toString().trim()
            note.createdAt = System.currentTimeMillis()
            note.updatedAt = System.currentTimeMillis()
            note.bgColor = bgColor
            if(isLocked){
                note.isLocked = 1
            }else{
                note.isLocked = 0
            }

            if(isPinned){
                note.isPinned = System.currentTimeMillis()
            }else{
                note.isPinned = 0
            }


            mainViewModel.insertNote(note)
        }else{
            note.title = removeSpaces(binding.titleId.text.toString().trim())
            note.description = binding.descriptionId.text.toString().trim()
            note.updatedAt = System.currentTimeMillis()
            note.bgColor = bgColor
            if(isLocked){
                note.isLocked = 1
            }else{
                note.isLocked = 0
            }

            if(isPinned){
                note.isPinned = System.currentTimeMillis()
            }else{
                note.isPinned = 0
            }

            mainViewModel.updateNote(noteId, note.title, note.description, note.updatedAt, note.isLocked, note.isPinned, note.bgColor)
        }

        finish()
    }

    private fun observeLiveData(){

        mainViewModel.hasPassword.observe(this, Observer {

            if (it) {
                binding.lockButton.setImageResource(R.drawable.locked_icon_new)
                isLocked = !isLocked
            }

        })

    }

    inner class ClickHandler{

        fun changeBg(pos: Int){

            defaultInit()


            bgColor = pos.toString()
            when(pos){

                0 -> {
                    binding.noteBg.setBackgroundColor(resources.getColor(R.color.darkModeBG))
                    binding.defaultColor1.setBackgroundResource(R.drawable.circle_bg)
                    binding.defaultSelected.visibility = View.VISIBLE

                    changeStatusBarColor(R.color.darkModeBG)
                    //bgColor = "darkModeBG"
                }
                1 -> {
                    binding.noteBg.setBackgroundColor(resources.getColor(R.color.red))
                    binding.redColor1.setBackgroundResource(R.drawable.circle_bg)
                    binding.redSelected.visibility = View.VISIBLE

                    changeStatusBarColor(R.color.red)
                    //bgColor = "red"
                }
                2 -> {
                    binding.noteBg.setBackgroundColor(resources.getColor(R.color.greenColor))
                    binding.greenColor1.setBackgroundResource(R.drawable.circle_bg)
                    binding.greenSelected.visibility = View.VISIBLE

                    changeStatusBarColor(R.color.greenColor)
                    //bgColor = "greenColor"
                }
                3 -> {
                    binding.noteBg.setBackgroundColor(resources.getColor(R.color.blueColor))
                    binding.blueColor1.setBackgroundResource(R.drawable.circle_bg)
                    binding.blueSelected.visibility = View.VISIBLE

                    changeStatusBarColor(R.color.blueColor)
                    //bgColor = "blueColor"
                }
                4 -> {
                    binding.noteBg.setBackgroundColor(resources.getColor(R.color.yellowColor))
                    binding.yellowColor1.setBackgroundResource(R.drawable.circle_bg)
                    binding.yellowSelected.visibility = View.VISIBLE

                    changeStatusBarColor(R.color.yellowColor)
                    //bgColor = "yellowColor"
                }
                5 -> {
                    binding.noteBg.setBackgroundColor(resources.getColor(R.color.orangeColor))
                    binding.orangeColor1.setBackgroundResource(R.drawable.circle_bg)
                    binding.orangeSelected.visibility = View.VISIBLE

                    changeStatusBarColor(R.color.orangeColor)
                    //bgColor = "orangeColor"
                }
                6 -> {
                    binding.noteBg.setBackgroundColor(resources.getColor(R.color.violateColor))
                    binding.violateColor1.setBackgroundResource(R.drawable.circle_bg)
                    binding.violateSelected.visibility = View.VISIBLE

                    changeStatusBarColor(R.color.violateColor)
                    //bgColor = "violateColor"
                }

            }

        }

    }

    private fun defaultInit(){
        binding.defaultSelected.visibility = View.GONE
        binding.redSelected.visibility = View.GONE
        binding.greenSelected.visibility = View.GONE
        binding.blueSelected.visibility = View.GONE
        binding.yellowSelected.visibility = View.GONE
        binding.orangeSelected.visibility = View.GONE
        binding.violateSelected.visibility = View.GONE

        binding.defaultColor1.background = null
        binding.redColor1.background = null
        binding.greenColor1.background = null
        binding.blueColor1.background = null
        binding.yellowColor1.background = null
        binding.orangeColor1.background = null
        binding.violateColor1.background = null
    }
    private fun shareNote(title: String, description: String) {
        val text = title + "\n\n" + description
        // Create an Intent with ACTION_SEND
        val shareIntent = Intent(Intent.ACTION_SEND)

        // Set the MIME type of the content to share
        shareIntent.type = "text/plain"

        // Add the text to the Intent as EXTRA_TEXT
        shareIntent.putExtra(Intent.EXTRA_TEXT, text)

        // Optionally, add a subject to the shared content
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, title)

        // Launch the Intent to share the content
        startActivity(Intent.createChooser(shareIntent, "Share Note"))
    }

    private fun changeStatusBarColor(colorCode: Int) {
        val window: Window = window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, colorCode)
    }

    fun removeSpaces(input: String): String {
        return input.replace("\\s".toRegex(), "")
    }


    override fun onBackPressed() {
        val title = binding.titleId.text.toString().trim()
        val description = binding.descriptionId.text.toString().trim()

        if(title.isNotEmpty() && description.isNotEmpty() && (prevTitle != title || prevDesc != description)){
            onSave()
        }else{
            finish()
        }
    }

}