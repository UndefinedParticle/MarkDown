package com.chinmoy09ine.markdown.adapters

import android.content.Context
import android.content.Intent
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.drawable.Drawable
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.chinmoy09ine.markdown.ConfirmPasswordDialog
import com.chinmoy09ine.markdown.R
import com.chinmoy09ine.markdown.activities.MainActivity
import com.chinmoy09ine.markdown.activities.NoteActivity
import com.chinmoy09ine.markdown.database.NotesTable
import com.chinmoy09ine.markdown.databinding.NotesItemBinding

class NotesAdapter(private val myContext: Context, private var list: ArrayList<NotesTable>): RecyclerView.Adapter<NotesAdapter.NotesViewHolder>() {

    fun updateItemsOnLongClick() {
        for (model in list) {
            model.deleting = !model.deleting
            model.selected = false

            MainActivity.mainViewModel.deleting.value = model.deleting
        }
        notifyDataSetChanged()
    }

    inner class NotesViewHolder(val binding: NotesItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        inner class ClickHandler(
            private val context: Context,
            private val holder: NotesViewHolder,
            private val position: Int
        ) {
            // Add a long click listener to itemView
            fun onLongClick(view: View): Boolean {
                vibrate(context)
                /*holder.binding.deleting = holder.binding.deleting?.not() ?: false
                holder.binding.selected = false*/

                updateItemsOnLongClick()

                return true
            }

            fun onClick(view: View) {
                //holder.binding.selected = holder.binding.selected?.not() ?: false
                list[position].selected = !list[position].selected
                notifyDataSetChanged()
                //Here I want to toggle "selected" variable in that particular alarmModel
            }


        }

        fun vibrate(context: Context) {
            val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                // Vibrate with vibration effect for SDK version 26 and above
                vibrator.vibrate(
                    VibrationEffect.createOneShot(
                        200,
                        VibrationEffect.DEFAULT_AMPLITUDE
                    )
                )
            } else {
                // Vibrate for devices below SDK version 26
                vibrator.vibrate(200)
            }
        }

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

        holder.binding.model = note
        holder.binding.selected = false
        holder.binding.deleting = false
        holder.binding.handler = holder.ClickHandler(myContext, holder, position)

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
                changeBg(R.color.commentBgColor, holder.binding.noteItem)
            }
            1 -> {
                changeBg(R.color.red, holder.binding.noteItem)
            }
            2 -> {
                changeBg(R.color.greenColor, holder.binding.noteItem)
            }
            3 -> {
                changeBg(R.color.blueColor, holder.binding.noteItem)
            }
            4 -> {
                changeBg(R.color.yellowColor, holder.binding.noteItem)
            }
            5 -> {
                changeBg(R.color.orangeColor, holder.binding.noteItem)
            }
            6 -> {
                changeBg(R.color.violateColor, holder.binding.noteItem)
            }

        }


        holder.binding.noteItem.setOnClickListener {

            if(MainActivity.mainViewModel.deleting.value == false) {
                if (note.isLocked != 0) {
                    val dialogFragment = ConfirmPasswordDialog(myContext, note.noteId, null)
                    val fragmentManager = (myContext as FragmentActivity).supportFragmentManager
                    dialogFragment.show(fragmentManager, "confirmPasswordDialog")
                } else {
                    myContext.startActivity(
                        Intent(myContext, NoteActivity::class.java)
                            .putExtra("noteId", note.noteId)
                            .putExtra("comingFrom", "adapter")
                    )
                }
            }else{
                list[position].selected = !list[position].selected
                holder.binding.selected = list[position].selected
            }

        }

    }

    private fun changeBg(colorId: Int, view: View){
        val drawable: Drawable = ContextCompat.getDrawable(myContext, R.drawable.curved_bg)!!
        val tintColor = ContextCompat.getColor(myContext, colorId)
        drawable.colorFilter = PorterDuffColorFilter(tintColor, PorterDuff.Mode.SRC_IN)

        view.background = drawable
    }

}