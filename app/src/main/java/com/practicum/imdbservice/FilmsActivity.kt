package com.practicum.imdbservice

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
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

    private val adapter = FilmsAdapter(films)

    private lateinit var searchButton: Button
    private lateinit var expressionInput: EditText
    private lateinit var filmsRecyclerView: RecyclerView

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

        filmsRecyclerView = findViewById(R.id.filmsRecycler)

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
                        if (response.body()?.results?.isNotEmpty() == true) {
                            //наполняем адаптер значениями
                            films.clear()

                            films.addAll(response.body()?.results!!)

                            filmsRecyclerView.adapter = FilmsAdapter(films)
                            filmsRecyclerView.layoutManager = LinearLayoutManager(this@FilmsActivity, LinearLayoutManager.VERTICAL, false)
                            adapter.notifyDataSetChanged()

                            Toast.makeText(this@FilmsActivity, "Поиск успешно произведен!", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(this@FilmsActivity, "Ничего не найдено", Toast.LENGTH_SHORT).show()
                        }
                    }
                    else -> {
                        Toast.makeText(this@FilmsActivity, "Код ошибки: ${response.code()}", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onFailure(call: Call<FilmsResponse>, t: Throwable) {
                Toast.makeText(this@FilmsActivity, "Что-то пошло не так..", Toast.LENGTH_SHORT).show()
            }
        })
    }
}