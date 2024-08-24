package com.practicum.imdbservice

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class FilmsActivity : AppCompatActivity() {

    private  val imdbBaseUrl = "https://tv-api.com"

    private val retrofit = Retrofit.Builder()
        .baseUrl(imdbBaseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val imdbService = retrofit.create(IMDbApi::class.java)

    private val films = ArrayList<Film>()

    private val adapter = FilmsAdapter()

    private lateinit var searchButton: Button
    private lateinit var expressionInput: EditText
    private lateinit var filmsList: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_films)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        searchButton = findViewById(R.id.searchButton)
        expressionInput = findViewById(R.id.expressionInput)
        filmsList = findViewById(R.id.films)

        adapter.films = films

        searchButton.setOnClickListener{
            if (expressionInput.text.isNotEmpty()) {
                search()
            }
        }

    }

    private fun search() {
        imdbService.getFilms(expressionInput.text.toString()).enqueue(object : Callback<FilmsResponse> {
            override fun onResponse(call: Call<FilmsResponse>, response: Response<FilmsResponse>) {
                when (response.code()) {
                    200 -> {
                        if (response.body()?.films.isNotEmpty() == true) {
                            films.clear()
                            films.addAll(response.body()?.films!!)
                            adapter.notifyDataSetChanged()
                        }
                    }
                    else -> {
                        Toast.makeText(this@FilmsActivity, "Что-то пошло не так..", Toast.LENGTH_SHORT)
                    }
                }
            }
        })
    }
}