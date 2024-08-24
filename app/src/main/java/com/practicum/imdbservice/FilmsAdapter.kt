package com.practicum.imdbservice

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class FilmsAdapter() : RecyclerView.Adapter<FilmViewHolder>() {

    var films = ArrayList<Film>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmViewHolder = FilmViewHolder(parent)

    override fun onBindViewHolder(holder: FilmViewHolder, position: Int) {
        holder.bind(films.get(position))
    }

    override fun getItemCount(): Int = films.size
}