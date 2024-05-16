package com.chinmoy09ine.markdown

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.chinmoy09ine.markdown.activities.NoteActivity
import com.chinmoy09ine.markdown.databinding.ConfirmPasswordDialogBinding
import com.chinmoy09ine.markdown.models.MainViewModel

class ConfirmPasswordDialog(private val myContext: Context, private val noteId: String, private val mainViewModel: MainViewModel?) : DialogFragment() {
    private lateinit var binding: ConfirmPasswordDialogBinding
    private var password = ""
    private var changePassword = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.CustomDialog)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ConfirmPasswordDialogBinding.inflate(layoutInflater, container, false)

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.setDimAmount(0.8f)
        dialog?.setCancelable(false)

        password = myContext.getSharedPreferences("MARKDOWN_PASSWORD", Context.MODE_PRIVATE).getString("notePassword", "").toString()
        if(password.isEmpty()){
            binding.changePassword.visibility = View.GONE
            binding.heading.text = myContext.resources.getString(R.string.set_password)

        }else{
            binding.changePassword.visibility = View.VISIBLE
        }

        binding.box1.requestFocus()
        setUpTextWatchers()

        binding.cancel.setOnClickListener {

            dialog?.dismiss()

        }

        binding.confirmCode.setOnClickListener {

            onSubmit()

        }

        binding.changePassword.setOnClickListener {

            binding.box1.setText("")
            binding.box2.setText("")
            binding.box3.setText("")
            binding.box4.setText("")

            binding.box11.requestFocus()
            setUpTextWatchers1()
            binding.heading.text = myContext.resources.getString(R.string.old_password)
            binding.oldPassword.visibility = View.VISIBLE
            binding.newHeading.visibility = View.VISIBLE
            binding.changePassword.visibility = View.GONE

            changePassword = true
        }

        return binding.root
    }

    private fun setUpTextWatchers() {

        binding.box1.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if (!s.isNullOrEmpty()) {
                    binding.box2.requestFocus()
                }else {
                    //binding.box1.clearFocus()
                }
            }
        })

        binding.box2.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if (!s.isNullOrEmpty()) {
                    binding.box3.requestFocus()
                }else{
                    binding.box1.requestFocus()
                }
            }
        })

        binding.box3.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if (!s.isNullOrEmpty()) {
                    binding.box4.requestFocus()
                }else{
                    binding.box2.requestFocus()
                }
            }
        })

        binding.box4.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if (!s.isNullOrEmpty()) {
                    binding.box4.requestFocus()
                }else{
                    binding.box3.requestFocus()
                }
            }
        })

    }

    private fun setUpTextWatchers1() {

        binding.box11.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if (!s.isNullOrEmpty()) {
                    binding.box21.requestFocus()
                }else {
                    //binding.box1.clearFocus()
                }
            }
        })

        binding.box21.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if (!s.isNullOrEmpty()) {
                    binding.box31.requestFocus()
                }else{
                    binding.box11.requestFocus()
                }
            }
        })

        binding.box31.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if (!s.isNullOrEmpty()) {
                    binding.box41.requestFocus()
                }else{
                    binding.box21.requestFocus()
                }
            }
        })

        binding.box41.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if (!s.isNullOrEmpty()) {
                    binding.box41.requestFocus()
                }else{
                    binding.box31.requestFocus()
                }
            }
        })

    }

    private fun onSubmit(){
        var otp = binding.box1.text.toString()
        otp += binding.box2.text.toString()
        otp += binding.box3.text.toString()
        otp += binding.box4.text.toString()

        if(changePassword){
            var otp1 = binding.box11.text.toString()
            otp1 += binding.box21.text.toString()
            otp1 += binding.box31.text.toString()
            otp1 += binding.box41.text.toString()

            if(otp1 == password){
                if(otp.length != 4){
                    binding.invalidPassword.visibility = View.VISIBLE
                    return
                }
                binding.invalidPassword.visibility = View.GONE

                myContext.getSharedPreferences("MARKDOWN_PASSWORD", Context.MODE_PRIVATE)
                    .edit()
                    .putString("notePassword", otp)
                    .apply()

                Toast.makeText(myContext, "Password has been changed!", Toast.LENGTH_SHORT).show()

                dialog?.dismiss()
            }else{
                Toast.makeText(myContext, "Password doesn't matched!", Toast.LENGTH_SHORT).show()
            }

            return
        }

        if(password.isEmpty()){
            myContext.getSharedPreferences("MARKDOWN_PASSWORD", Context.MODE_PRIVATE)
                .edit()
                .putString("notePassword", otp)
                .apply()

            if(mainViewModel != null){
                mainViewModel.hasPassword.value = true
            }

            dialog?.dismiss()
            return
        }

        if(otp.isEmpty() || otp != password){
            binding.invalidPassword.visibility = View.VISIBLE
            return
        }

        binding.invalidPassword.visibility = View.GONE

        dialog?.dismiss()

        myContext.startActivity(
            Intent(myContext, NoteActivity::class.java)
            .putExtra("noteId", noteId)
            .putExtra("comingFrom", "adapter"))

    }


}