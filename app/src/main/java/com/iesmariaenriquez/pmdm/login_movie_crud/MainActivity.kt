package com.iesmariaenriquez.pmdm.login_movie_crud

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.iesmariaenriquez.pmdm.login_movie_crud.navigation.AppNavigation
import com.iesmariaenriquez.pmdm.login_movie_crud.ui.theme.Login_movie_crudTheme

class MainActivity : ComponentActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        setContent {
            Login_movie_crudTheme {
                AppNavigation()
            }
        }
    }
}

