package com.practicum.imdbservice.presentation.movies

import com.practicum.imdbservice.domain.models.Movie
import com.practicum.imdbservice.ui.movies.models.MoviesState
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.SkipStrategy
import moxy.viewstate.strategy.StateStrategyType

interface MoviesView: MvpView {

    @StateStrategyType(SkipStrategy::class)
    fun showLoading()

    @StateStrategyType(SkipStrategy::class)
    fun showContent(movies: List<Movie>)

    @StateStrategyType(SkipStrategy::class)
    fun showError(errorMessage: String)

    @StateStrategyType(SkipStrategy::class)
    fun showEmpty(emptyMessage: String)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun render(state: MoviesState)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showToast(message: String)
}