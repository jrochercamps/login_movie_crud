package com.iesmariaenriquez.pmdm.login_movie_crud.ui.viewmodel

import kotlinx.coroutines.launch
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.iesmariaenriquez.pmdm.login_movie_crud.data.repository.MovieRepository
import com.iesmariaenriquez.pmdm.login_movie_crud.data.repository.MovieRepositoryImp
import com.iesmariaenriquez.pmdm.login_movie_crud.screens.DataState
import com.iesmariaenriquez.pmdm.login_movie_crud.ui.model.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlin.Result


class MovieViewModel: ViewModel() {

    private val _addMovieResult = MutableStateFlow(Result.success(false))

    private val movieRepository: MovieRepository = MovieRepositoryImp()

    private val _movies = MutableStateFlow<DataState<List<Movie>>>(DataState.Loading)
    val movies: StateFlow<DataState<List<Movie>>> = _movies

    fun getAllMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            _movies.value = DataState.Loading

            try {
                movieRepository.getAllMovies().collect { dataState ->
                    when (dataState) {
                        is DataState.Success -> {
                            _movies.value = dataState
                        }
                        is DataState.Error -> {
                            _movies.value = dataState
                        }
                        is DataState.Loading -> {    }
                        }
                    }

            } catch (e: Exception) {
                _movies.value = DataState.Error(e)
            }
        }
    }

    fun addMovie(title: String, director: String, year: String) {
        viewModelScope.launch(Dispatchers.IO) {

            try {
                movieRepository.addMovie(title,director,year) { isSuccess ->
                    _addMovieResult.value = Result.success(isSuccess)
                }
            } catch (e: Exception) {
                _addMovieResult.value = Result.failure(e)
            }
        }
    }

    fun updateMovie(movie: Movie) {
        viewModelScope.launch {
            //repository.updateMovie(movie)
        }
    }

    fun deleteMovie(movie: Movie) {
        viewModelScope.launch {
            //repository.deleteMovie(movie)
        }
    }

}