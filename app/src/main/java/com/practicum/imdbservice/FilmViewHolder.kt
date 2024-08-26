package com.practicum.imdbservice

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FilmViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    private val film_name: TextView
    private val film_information: TextView

    init {
        film_name = itemView.findViewById(R.id.film_name)
        film_information = itemView.findViewById(R.id.film_information)
    }

    fun bind(film: Film) {
        film_name.text = film.title
        film_information.text = film.description
    }

}