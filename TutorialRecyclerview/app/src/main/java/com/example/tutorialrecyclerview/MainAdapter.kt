package com.example.tutorialrecyclerview

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView

class MainAdapter(

    private val listName : List<String>
): RecyclerView.Adapter<MainAdapter.Viewhold>(){
    class Viewhold(view: View): RecyclerView.ViewHolder(view) {
        val textView : TextView = view.findViewById(R.id.textView)
        val container : ConstraintLayout = view.findViewById(R.id.container)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewhold {
       val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_main, parent, false)
        return Viewhold(view)
    }

    override fun getItemCount(): Int {
       return listName.size
    }

    override fun onBindViewHolder(holder: Viewhold, position: Int) {
        val name = listName[position]
        holder.textView.text = name

        holder.container.setOnClickListener {
            Toast.makeText(holder.itemView.context, "User's name: $name", Toast.LENGTH_SHORT).show()
        }
    }
}
