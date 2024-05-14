package com.chinmoy09ine.markdown.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.chinmoy09ine.markdown.R
import com.chinmoy09ine.markdown.databinding.ActivityNoteBinding


class NoteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNoteBinding
    private var isLocked = false
    private var isPinned = false
    private var colorLayout = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_note)
        binding.lifecycleOwner = this

        changeStatusBarColor()
        binding.clickHandler = ClickHandler()
        binding.noteBg.setBackgroundColor(resources.getColor(R.color.darkModeBG))
        binding.defaultColor1.setBackgroundResource(R.drawable.circle_bg)
        binding.defaultSelected.visibility = View.VISIBLE

        binding.backButton.setOnClickListener {

            onBackPressed()

        }

        binding.saveButton.setOnClickListener {

            onBackPressed()

        }

        binding.lockButton.setOnClickListener {

            if(!isLocked) {
                binding.lockButton.setImageResource(R.drawable.locked_icon_new)
            }else{
                binding.lockButton.setImageResource(R.drawable.open_lock)
            }

            isLocked = !isLocked

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

            val text: String = binding.titleId.text.toString().trim() + "\n\n" + binding.descriptionId.text.toString().trim()
            shareNote(text)

        }

    }

    inner class ClickHandler{

        fun changeBg(pos: Int){

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



            when(pos){

                0 -> {
                    //binding.defaultColor.setBackgroundColor(resources.getColor(R.color.transparentColor))
                    binding.noteBg.setBackgroundColor(resources.getColor(R.color.darkModeBG))
                    binding.defaultColor1.setBackgroundResource(R.drawable.circle_bg)
                    binding.defaultSelected.visibility = View.VISIBLE
                }
                1 -> {
                    //binding.redColor.setBackgroundColor(resources.getColor(R.color.transparentColor))
                    binding.noteBg.setBackgroundColor(resources.getColor(R.color.red))
                    binding.redColor1.setBackgroundResource(R.drawable.circle_bg)
                    binding.redSelected.visibility = View.VISIBLE
                }
                2 -> {
                    //binding.greenColor.setBackgroundColor(resources.getColor(R.color.transparentColor))
                    binding.noteBg.setBackgroundColor(resources.getColor(R.color.greenColor))
                    binding.greenColor1.setBackgroundResource(R.drawable.circle_bg)
                    binding.greenSelected.visibility = View.VISIBLE
                }
                3 -> {
                    //binding.blueColor.setBackgroundColor(resources.getColor(R.color.transparentColor))
                    binding.noteBg.setBackgroundColor(resources.getColor(R.color.blueColor))
                    binding.blueColor1.setBackgroundResource(R.drawable.circle_bg)
                    binding.blueSelected.visibility = View.VISIBLE
                }
                4 -> {
                    //binding.yellowColor.setBackgroundColor(resources.getColor(R.color.transparentColor))
                    binding.noteBg.setBackgroundColor(resources.getColor(R.color.yellowColor))
                    binding.yellowColor1.setBackgroundResource(R.drawable.circle_bg)
                    binding.yellowSelected.visibility = View.VISIBLE
                }
                5 -> {
                    //binding.orangeColor.setBackgroundColor(resources.getColor(R.color.transparentColor))
                    binding.noteBg.setBackgroundColor(resources.getColor(R.color.orangeColor))
                    binding.orangeColor1.setBackgroundResource(R.drawable.circle_bg)
                    binding.orangeSelected.visibility = View.VISIBLE
                }
                6 -> {
                    //binding.violateColor.setBackgroundColor(resources.getColor(R.color.transparentColor))
                    binding.noteBg.setBackgroundColor(resources.getColor(R.color.violateColor))
                    binding.violateColor1.setBackgroundResource(R.drawable.circle_bg)
                    binding.violateSelected.visibility = View.VISIBLE
                }

            }

        }

    }

    private fun shareNote(text: String) {
        // Create an Intent with ACTION_SEND
        val shareIntent = Intent(Intent.ACTION_SEND)

        // Set the MIME type of the content to share
        shareIntent.type = "text/plain"

        // Add the text to the Intent as EXTRA_TEXT
        shareIntent.putExtra(Intent.EXTRA_TEXT, text)

        // Optionally, add a subject to the shared content
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Shared Note")

        // Launch the Intent to share the content
        startActivity(Intent.createChooser(shareIntent, "Share Note"))
    }

    private fun changeStatusBarColor() {
        val window: Window = window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, R.color.darkModeBG)
    }

    override fun onBackPressed() {
        finish()
    }

}