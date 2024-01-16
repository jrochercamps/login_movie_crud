package com.iesmariaenriquez.pmdm.login_movie_crud.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FabPosition

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface

import androidx.compose.ui.draw.clip

import androidx.compose.ui.unit.dp

import com.iesmariaenriquez.pmdm.login_movie_crud.ui.model.Movie
import com.iesmariaenriquez.pmdm.login_movie_crud.ui.viewmodel.MovieViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieScreen(onAddMovie: () -> Unit, onLogout: () -> Unit) {

    // Scaffold with a TopAppBar and FloatingActionButton
    Scaffold(
        topBar = {
            // TopAppBar with title, navigation button, and logout button
            TopAppBar(
                title = { Text("Movies") },
                navigationIcon = {
                    IconButton(onClick = onLogout) {
                        Icon(imageVector = Icons.Default.ExitToApp, contentDescription = null)
                    }
                }
            )
        },
        floatingActionButton = {
            // FloatingActionButton positioned at the end with optional text, icon, and click behavior
            ExtendedFloatingActionButton(
                text = { Text("Add Movie") },
                onClick = onAddMovie,
                icon = {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add"
                    )
                },
                modifier = Modifier.padding(16.dp)
            )
        },
        floatingActionButtonPosition = FabPosition.End, // Position of the floating action button (Start or End)

    ) { innerPadding ->
        // Column with inner padding and content of the screen
        Column(modifier = Modifier.padding(innerPadding)) {
            // Content of the screen
            MovieBodyContent()
        }
    }
}

@Composable
fun MovieBodyContent() {

    val viewModel: MovieViewModel = MovieViewModel()

    val moviesState by viewModel.movies.collectAsState()
    viewModel.getAllMovies()

    // Movie list
    LazyColumn {

        items(items = listOf(Unit)) {
            when (val state = moviesState) {
                is DataState.Success -> {
                    state.data.forEach { movie ->
                        MovieItem(
                            movie = movie
                        )
                    }
                }

                is DataState.Error -> {
                    // Handle the error state, for example, display an error message
                    println("Error: ${state.exception.message}")
                }

                is DataState.Loading -> {
                    // Display a loading indicator or some other visual indicator
                    println("Loading")
                }
            }
        }
    }

}

@Composable
fun MovieItem(movie: Movie) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clip(MaterialTheme.shapes.medium)
            .clickable { /* Handle item click if needed */ }
            .background(MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(text = "Title: ${movie.title}")
            Spacer(modifier = Modifier.height(8.dp))

            Text(text = "Director: ${movie.director}")
            Spacer(modifier = Modifier.height(4.dp))

            Text(text = "Year: ${movie.year}")
        }
    }
}

sealed class DataState<out T> {
    data class Success<T>(val data: T) : DataState<T>()
    data class Error(val exception: Exception) : DataState<Nothing>()
    object Loading : DataState<Nothing>()
}
