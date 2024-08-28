package com.practicum.imdbservice

import android.view.LayoutInflater
import android.view.RoundedCorner
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners

class FilmViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    private val film_name: TextView
    private val film_information: TextView
    private val film_banner: ImageView

    init {
        film_name = itemView.findViewById(R.id.film_name)
        film_information = itemView.findViewById(R.id.film_information)
        film_banner = itemView.findViewById(R.id.film_banner)
    }

    fun bind(film: Film) {
        film_name.text = film.title
        film_information.text = film.description
        Glide.with(itemView.context)
            .load(film.image)
            .placeholder(R.drawable.placeholder)
            .centerInside().into(film_banner)
    }

}