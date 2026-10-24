package com.example.a232notes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder

class NoteAdapter (val listener : OnClickListener) : ListAdapter<Note,NoteAdapter.NoteViewHolder >(DIFF_CALLBACK)  {

    companion object {

        val DIFF_CALLBACK = object  : DiffUtil.ItemCallback<Note>(){
            override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
                return oldItem == newItem
            }

        }

    }



    fun getNoteAt (position: Int) : Note {
        return getItem(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout , parent , false)

        return NoteViewHolder(view)

    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {

        val currentNote = getItem(position)
        holder.title.text  = currentNote.title
        holder.description.text  = currentNote.description
        holder.priority.text  = currentNote.priority.toString()
    }


    inner class NoteViewHolder (view: View) : ViewHolder(view){

        val title : TextView = view.findViewById( R.id.title_text_view)
        val description : TextView = view.findViewById( R.id.description_text_view)
        val priority : TextView = view.findViewById( R.id.priority_text_view)

        init {

                view.setOnClickListener {
                    if (adapterPosition != RecyclerView.NO_POSITION){

                    listener.onClick(getItem(adapterPosition))

                }
            }
        }

    }

    interface OnClickListener {

        fun onClick (note: Note)

    }


}