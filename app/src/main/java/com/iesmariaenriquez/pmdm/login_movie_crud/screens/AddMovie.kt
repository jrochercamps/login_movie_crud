package com.iesmariaenriquez.pmdm.login_movie_crud.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*

import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier

import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController

import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

import com.iesmariaenriquez.pmdm.login_movie_crud.ui.viewmodel.MovieViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun AddMovieContent(closeAddCompleteMovie: () -> Unit) {
    // Create an instance of the MovieViewModel
    val viewModel: MovieViewModel = MovieViewModel()

// Mutable state variables for title, director, and year
    var title by remember { mutableStateOf("") }
    var director by remember { mutableStateOf("") }
    var year by remember { mutableStateOf("") }

// Retrieve the software keyboard controller
    val keyboardController = LocalSoftwareKeyboardController.current

// Scaffold with top app bar and floating action buttons
    Scaffold(
        topBar = {
            // Top app bar with title
            TopAppBar(
                title = { Text("Add Movie") },
                navigationIcon = {
                    // Close button in the top app bar
                    IconButton(onClick = closeAddCompleteMovie) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "Close")
                    }
                }
            )
        },
        floatingActionButton = {
            // Floating action button for adding a movie
            FloatingActionButton(
                onClick = {
                    // Validate input before adding the movie
                    if (title.isNotEmpty() && director.isNotEmpty() && year.isNotEmpty()) {
                        // Add the movie using the ViewModel
                        viewModel.addMovie(title, director, year)

                        // Close the keyboard
                        keyboardController?.hide()

                        // Close the Add Movie screen
                        closeAddCompleteMovie()
                    } else {
                        // Show an error message or handle validation as needed
                    }
                },
                modifier = Modifier
                    .padding(16.dp)
            ) {
                // Icon for the Add Movie floating action button
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Movie")
            }
        },
        floatingActionButtonPosition = FabPosition.End, // Position of the floating action button
    ) {
        // Column layout to arrange UI components vertically
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Spacer for better vertical alignment
            Spacer(modifier = Modifier.height(32.dp))

            // TextField for entering movie title
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Title") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )

            // Spacer for better vertical alignment
            Spacer(modifier = Modifier.height(8.dp))

            // TextField for entering movie director
            OutlinedTextField(
                value = director,
                onValueChange = { director = it },
                label = { Text("Director") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )

            // Spacer for better vertical alignment
            Spacer(modifier = Modifier.height(8.dp))

            // TextField for entering movie release year
            OutlinedTextField(
                value = year,
                onValueChange = {
                    // Allow only numeric input for the year
                    year = it.replace(Regex("[^\\d]"), "")
                },
                label = { Text("Year") },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        // Hide the keyboard when the "Done" action is triggered
                        keyboardController?.hide()
                    }
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )
        }
    }

}

