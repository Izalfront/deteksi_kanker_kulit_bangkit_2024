package com.example.tutorialrecyclerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        imageAdapter()
//        textAdapter()
        movieAdapter()
    }

    private fun movieAdapter(){
        val movieList = listOf<MovieModel>(
            MovieModel(1, "logo anjay", R.drawable.photo1),
            MovieModel(2, "logo anjay 2", R.drawable.photo2),
            MovieModel(3, "logo anjay 3", R.drawable.photo3),

        )

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val adapter = MovieAdapter(movieList, object : MovieAdapter.onAdapterListener{
            override fun onClick(movieList: MovieModel) {
                Toast.makeText(applicationContext, movieList.name, Toast.LENGTH_SHORT).show()
            }

        })
        recyclerView.adapter = adapter
    }

    private fun imageAdapter(){
        val image = listOf(
            R.drawable.photo3,
            R.drawable.photo2,
            R.drawable.photo1,
        )
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val adapter = ImageAdapter(image)
        recyclerView.adapter = adapter
    }

    private fun textAdapter(){
        val names = listOf(
            "rizal",
            "andi",
            "budi",
            "dimas",
            "yuda"
        )
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val adapter = MainAdapter(names)
        recyclerView.adapter = adapter
    }
}