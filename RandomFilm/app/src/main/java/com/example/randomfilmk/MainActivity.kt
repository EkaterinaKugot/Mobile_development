package com.example.randomfilmk

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import com.google.gson.Gson
import java.io.InputStream
import java.io.InputStreamReader
import java.util.*

class MainActivity : AppCompatActivity() {

    lateinit var movies: Array<String>;
    lateinit var indexs: Array<Int>
    lateinit var movies_data: Movies
    private lateinit var tvName: TextView
    private lateinit var tvYear: TextView
    private lateinit var tvRating: TextView
    private lateinit var tvAgeLimit: TextView
    private lateinit var tvDuration: TextView
    private lateinit var tvGenre: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvName = findViewById<TextView>(R.id.name)
        tvYear = findViewById<TextView>(R.id.year)
        tvRating = findViewById<TextView>(R.id.rating)
        tvAgeLimit = findViewById<TextView>(R.id.age_limit)
        tvDuration = findViewById<TextView>(R.id.duration)
        tvGenre = findViewById<TextView>(R.id.genre)

        movies = resources.getStringArray(R.array.movies)
        indexs = Array(movies.size) { -1 }
        Log.d("mytag", movies[0])
        // открываем файл
        val movies_stream = resources.openRawResource(R.raw.movies)
        val gson = Gson() // конвертор из JSON обратно
        movies_data = gson.fromJson(InputStreamReader(movies_stream), Movies::class.java)
        Log.d("mytag", "Loaded movies ${movies_data.movies.size}")

    }

    @SuppressLint("SetTextI18n")
    fun onNextClick(view: View) {
        var rndIndex = Random().nextInt(movies.size)
        if (-1 !in indexs) {
            tvName.text = "Фильмы кончились"
            tvYear.text = ""
            tvRating.text = ""
            tvAgeLimit.text = ""
            tvDuration.text = ""
            tvGenre.text = ""
        } else {
            while (rndIndex in indexs) {
                rndIndex = Random().nextInt(movies.size)
            }
            indexs[rndIndex] = rndIndex
            tvName.text = "Name: " + movies[rndIndex]

            var idx: Int = 0
            for (m in 0 until movies_data.movies.size) {
                if (movies_data.movies[m].name == movies[rndIndex]) {
                    idx = m
                }
            }

            tvYear.text = "Year: " + movies_data.movies[idx].year.toString()
            tvRating.text = "Rating: " + movies_data.movies[idx].rating.toString()
            tvAgeLimit.text = "Age Limit: " + movies_data.movies[idx].age_limit.toString()
            tvDuration.text = "Duration: " + movies_data.movies[idx].duration.toString()

            val genre = movies_data.movies[idx].genre
            var txtGenre = ""
            genre.forEach {
                txtGenre += it.toString() + " "
            }

            tvGenre.text = "Genre: " + txtGenre

        }
    }

    fun onClearClick(view: View) {
        indexs = Array(movies.size) { -1 }
        tvName.text = "Фильмы обновлены"
        tvYear.text = ""
        tvRating.text = ""
        tvAgeLimit.text = ""
        tvDuration.text = ""
        tvGenre.text = ""
    }
}