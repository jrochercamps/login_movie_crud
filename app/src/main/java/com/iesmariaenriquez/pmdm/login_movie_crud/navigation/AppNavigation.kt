package com.iesmariaenriquez.pmdm.login_movie_crud.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.google.firebase.auth.FirebaseUser

import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.iesmariaenriquez.pmdm.login_movie_crud.screens.AddMovieContent
import com.iesmariaenriquez.pmdm.login_movie_crud.screens.LoginScreen
import com.iesmariaenriquez.pmdm.login_movie_crud.screens.MovieScreen


@Composable
fun AppNavigation(){

    // Configurar el NavController
    val navController = rememberNavController()

    // Configurar el sistema de autenticación
    val authState = remember { mutableStateOf<FirebaseUser?>(null) }


    NavHost(navController = navController,
        startDestination = if (authState?.value == null) AppScreens.LoginScreen.route else AppScreens.MovieScreen.route){
        composable(route = AppScreens.LoginScreen.route){
            LoginScreen {
                authState.value = it
                navController.navigate(AppScreens.MovieScreen.route)
            }
        }
        composable(route = AppScreens.MovieScreen.route){
            MovieScreen(
                onAddMovie={
                    navController.navigate(AppScreens.AddMovieScreen.route)
                },
                onLogout = {
                    authState.value = null
                    navController.navigate(AppScreens.LoginScreen.route)
                })
        }
        composable(route = AppScreens.AddMovieScreen.route){
            AddMovieContent(
                closeAddCompleteMovie={
                    navController.navigate(AppScreens.MovieScreen.route)
                }
            )
        }
    }


}