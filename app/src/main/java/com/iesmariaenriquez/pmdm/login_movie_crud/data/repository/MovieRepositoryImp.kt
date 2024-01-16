package com.iesmariaenriquez.pmdm.login_movie_crud.data.repository

import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.iesmariaenriquez.pmdm.login_movie_crud.screens.DataState
import com.iesmariaenriquez.pmdm.login_movie_crud.ui.model.Movie
import com.iesmariaenriquez.pmdm.login_movie_crud.util.FireStoreCollection
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await


class MovieRepositoryImp:MovieRepository  {

    private val db = FirebaseFirestore.getInstance();

    override fun getAllMovies(): Flow<DataState<List<Movie>>> = flow {
        emit(DataState.Loading)

        try {
            val response = db.collection(FireStoreCollection.MOVIE)
                .get()
                .await()

            val moviesList = mutableListOf<Movie>()
            for (movie in response.documents) {
                val movie = movie.data?.let { mapMovieFromFirestore(it) }
                if (movie != null) {
                    moviesList.add(movie)
                }
            }

            emit(DataState.Success(moviesList))
        } catch (e: Exception) {
            Log.e(TAG, "Error getting documents: ", e)
            emit(DataState.Error(e))
        }
    }.flowOn(Dispatchers.IO)

    override fun addMovie(title:String,director:String,year:String, callback: (Boolean) -> Unit) {

        val movie = hashMapOf(
            "title" to title,
            "director" to director,
            "year" to year,
        )

        db.collection(FireStoreCollection.MOVIE).document(title)
            .set(movie)
            .addOnSuccessListener {
                callback(true)
            }
            .addOnFailureListener {
                    e -> Log.w(TAG, "Error writing document", e)
                callback(false)
            }


    }

    private fun mapMovieFromFirestore(movieData: Map<String, Any>): Movie {

        val title = movieData["title"] as? String
        val director = movieData["director"] as? String
        val year = movieData["year"] as? String

        return Movie(title ?: "", director ?: "", year?: "")
    }
}