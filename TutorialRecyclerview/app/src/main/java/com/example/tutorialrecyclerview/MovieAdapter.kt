package com.example.tutorialrecyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MovieAdapter(
    private val listMain : List<MovieModel>,
    private val listener : onAdapterListener
): RecyclerView.Adapter<MovieAdapter.Viewhold>(){

    class Viewhold(view: View): RecyclerView.ViewHolder(view) {
        val imageView : ImageView = view.findViewById(R.id.imageView)
        val textView : TextView = view.findViewById(R.id.textView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewhold {
       val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_model, parent, false)
        return Viewhold(view)
    }

    override fun onBindViewHolder(holder: Viewhold, position: Int) {
        val movie = listMain[position]
        holder.imageView.setImageResource(movie.image)
        holder.textView.text = movie.name
        holder.itemView.setOnClickListener {
            listener.onClick(movie)
        }
    }

    override fun getItemCount(): Int {
        return listMain.size
    }

    interface onAdapterListener{
        fun onClick(movieList : MovieModel)
    }

}
