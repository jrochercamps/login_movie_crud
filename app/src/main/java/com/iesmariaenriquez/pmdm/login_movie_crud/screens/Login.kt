package com.iesmariaenriquez.pmdm.login_movie_crud.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseUser
import com.iesmariaenriquez.pmdm.login_movie_crud.ui.viewmodel.UserViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(onLoginSuccess: (FirebaseUser) -> Unit){
    // Scaffold for the overall structure of the screen
    Scaffold { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {}
        LoginBodyContent(onLoginSuccess)
    }
}

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun LoginBodyContent(onLoginSuccess: (FirebaseUser) -> Unit){
    // Accessing the current context and creating a UserViewModel
    val context = LocalContext.current
    val userViewModel: UserViewModel = UserViewModel()

    // Column layout with user interface elements
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // State variables for username, password, and password visibility
        var username by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        var isPasswordVisible by remember { mutableStateOf(false) }

        // TextFields for username and password
        var keyboardController = androidx.compose.ui.platform.LocalSoftwareKeyboardController.current

        androidx.compose.material3.TextField(
            value = username,
            onValueChange = { username = it },
            label = { androidx.compose.material3.Text("Username") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )

        androidx.compose.material3.TextField(
            value = password,
            onValueChange = { password = it },
            label = { androidx.compose.material3.Text("Password") },
            keyboardOptions = androidx.compose.foundation.text.KeyboardOptions.Default.copy(
                keyboardType = androidx.compose.ui.text.input.KeyboardType.Password
            ),
            keyboardActions = androidx.compose.foundation.text.KeyboardActions(
                onDone = {
                    keyboardController?.hide()
                }
            ),
            trailingIcon = {
                androidx.compose.material3.IconButton(onClick = {
                    isPasswordVisible = !isPasswordVisible
                }) {
                    androidx.compose.material3.Icon(
                        imageVector = androidx.compose.material.icons.Icons.Default.Info,
                        contentDescription = null
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )

        // Login button
        androidx.compose.material3.Button(
            onClick = {
                // Perform authentication with Firebase
                if (username.isNotEmpty() && password.isNotEmpty()) {
                    userViewModel.signInWithFirebase(
                        context,
                        username,
                        password,
                        onLoginSuccess
                    )

                    keyboardController?.hide()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            androidx.compose.material3.Text("Log In")
        }

        // Spacer
        androidx.compose.foundation.layout.Spacer(
            modifier = Modifier.height(16.dp)
        )

        // Button to register a new user
        androidx.compose.material3.Button(
            onClick = {
                userViewModel.signUpWithFirebase(context, username, password)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            androidx.compose.material3.Text("Sign Up")
        }
    }
}
