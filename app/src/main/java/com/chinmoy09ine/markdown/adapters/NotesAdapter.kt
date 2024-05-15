package com.chinmoy09ine.markdown.adapters

import android.content.Context
import android.content.Intent
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.chinmoy09ine.markdown.R
import com.chinmoy09ine.markdown.activities.NoteActivity
import com.chinmoy09ine.markdown.database.NotesTable
import com.chinmoy09ine.markdown.databinding.NotesItemBinding

class NotesAdapter(private val myContext: Context, private var list: ArrayList<NotesTable>): RecyclerView.Adapter<NotesAdapter.NotesViewHolder>() {

    inner class NotesViewHolder(val binding: NotesItemBinding) :
        RecyclerView.ViewHolder(binding.root) {


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        val binding: NotesItemBinding =
            DataBindingUtil.inflate(LayoutInflater.from(myContext), R.layout.notes_item, parent, false)
        return NotesViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        val note = list[position]

        holder.binding.titleId.text = note.title
        holder.binding.descriptionId.text = note.description

        holder.binding.isLocked = note.isLocked != 0

        if(note.isPinned.toInt() == 0){
            holder.binding.isPinned.visibility = View.GONE
        }else{
            holder.binding.isPinned.visibility = View.VISIBLE
        }

        if(note.bgColor == ""){
            note.bgColor = "0"
        }



        when(note.bgColor.toInt()){

            0 -> {
                //holder.binding.noteItem.setBackgroundColor(myContext.resources.getColor(R.color.darkModeBG))
                /*val drawable: Drawable = ContextCompat.getDrawable(myContext, R.drawable.custom_bg)!!
                val tintColor = ContextCompat.getColor(myContext, R.color.darkModeBG)
                drawable.colorFilter = PorterDuffColorFilter(tintColor, PorterDuff.Mode.SRC_IN)

                holder.binding.noteItem.background = drawable*/
                changeBg(R.color.settingBgColor, holder.binding.noteItem)
            }
            1 -> {
                //holder.binding.noteItem.setBackgroundColor(myContext.resources.getColor(R.color.red))
                changeBg(R.color.red, holder.binding.noteItem)
            }
            2 -> {
                //holder.binding.noteItem.setBackgroundColor(myContext.resources.getColor(R.color.greenColor))
                changeBg(R.color.greenColor, holder.binding.noteItem)
            }
            3 -> {
                //holder.binding.noteItem.setBackgroundColor(myContext.resources.getColor(R.color.blueColor))
                changeBg(R.color.blueColor, holder.binding.noteItem)
            }
            4 -> {
                //holder.binding.noteItem.setBackgroundColor(myContext.resources.getColor(R.color.yellowColor))
                changeBg(R.color.yellowColor, holder.binding.noteItem)
            }
            5 -> {
                //holder.binding.noteItem.setBackgroundColor(myContext.resources.getColor(R.color.orangeColor))
                changeBg(R.color.orangeColor, holder.binding.noteItem)
            }
            6 -> {
                //holder.binding.noteItem.setBackgroundColor(myContext.resources.getColor(R.color.violateColor))
                changeBg(R.color.violateColor, holder.binding.noteItem)
            }

        }


        holder.binding.noteItem.setOnClickListener {

            myContext.startActivity(Intent(myContext, NoteActivity::class.java)
                .putExtra("noteId", note.noteId)
                .putExtra("comingFrom", "adapter"))

        }

    }

    private fun changeBg(colorId: Int, view: View){
        val drawable: Drawable = ContextCompat.getDrawable(myContext, R.drawable.curved_bg)!!
        val tintColor = ContextCompat.getColor(myContext, colorId)
        drawable.colorFilter = PorterDuffColorFilter(tintColor, PorterDuff.Mode.SRC_IN)

        view.background = drawable
    }

}