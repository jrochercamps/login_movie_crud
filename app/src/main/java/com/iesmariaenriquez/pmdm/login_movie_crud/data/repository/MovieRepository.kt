package com.iesmariaenriquez.pmdm.login_movie_crud.data.repository

import com.iesmariaenriquez.pmdm.login_movie_crud.screens.DataState
import com.iesmariaenriquez.pmdm.login_movie_crud.ui.model.Movie

interface  MovieRepository {


    //fun getMovie(user: User?, result: (UiState<List<Note>>) -> Unit)
    fun addMovie(title:String,director:String,year:String, callback: (Boolean) -> Unit)
    //fun updateMovie(note: Note, result: (UiState<String>) -> Unit)
    //fun deleteMovie(note: Note, result: (UiState<String>) -> Unit)
    //suspend fun uploadSingleFile(fileUri: Uri, onResult: (UiState<Uri>) -> Unit)
    //suspend fun uploadMultipleFile(fileUri: List<Uri>, onResult: (UiState<List<Uri>>) -> Unit)
    //fun getAllMovies(callback: (List<Movie>) -> Unit)
    //fun getAllMovies(): Flow<DataState<List<Movie>>>
    fun getAllMovies(): kotlinx.coroutines.flow.Flow<DataState<List<Movie>>>
}