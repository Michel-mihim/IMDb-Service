package com.practicum.imdbservice

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FilmViewHolder(parent: ViewGroup): RecyclerView.ViewHolder(LayoutInflater.from(parent.context)
    .inflate(R.layout.film_list_item, parent,false)) {

    var film_name: TextView = itemView.findViewById(R.id.film_name)
    var film_information: TextView = itemView.findViewById(R.id.film_information)

    fun bind(film: Film) {
        film_name.text = film.title //?
        film_information.text = film.description //?
    }

}