package com.practicum.imdbservice.presentation.poster

import android.app.Activity
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.practicum.imdbservice.R

class PosterPresenter(
    private val view: PosterView,
    private val imageUrl: String
) {

    private lateinit var poster: ImageView

    fun onCreate() {
        view.setupPosterImage(imageUrl)
    }


}