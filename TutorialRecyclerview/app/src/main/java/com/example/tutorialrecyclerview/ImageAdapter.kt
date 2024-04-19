package com.example.tutorialrecyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ImageAdapter(
    private val listImage : List<Int>
): RecyclerView.Adapter<ImageAdapter.Viewhold>(){

    class Viewhold(view: View): RecyclerView.ViewHolder(view) {
        val imageView : ImageView = view.findViewById(R.id.imageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewhold {
       val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_image, parent, false)
        return Viewhold(view)
    }

    override fun onBindViewHolder(holder: Viewhold, position: Int) {
        holder.imageView.setImageResource(listImage[position])
    }

    override fun getItemCount(): Int {
        return listImage.size
    }

}
