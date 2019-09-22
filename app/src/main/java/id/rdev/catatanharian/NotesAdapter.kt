package id.rdev.catatanharian

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_note.view.*

class NotesAdapter(val notes : ArrayList<Note>, val onClick : OnClick) : RecyclerView.Adapter<NotesAdapter.MyHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_note, parent, false)
        return MyHolder(view)
    }

    override fun getItemCount(): Int = notes.size

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.bind(notes.get(position))
        holder.itemView.btDeleteNote.setOnClickListener {
            onClick.delete(notes.get(position).key)
        }
        holder.itemView.setOnClickListener {
            onClick.edit(notes.get(position))
        }
    }

    class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(note : Note){
            itemView.tvNoteName.text = note.title
            itemView.tvNoteDescription.text = note.description
        }
    }

    interface OnClick {
        fun delete(key : String?)
        fun edit(note : Note?)
    }
}