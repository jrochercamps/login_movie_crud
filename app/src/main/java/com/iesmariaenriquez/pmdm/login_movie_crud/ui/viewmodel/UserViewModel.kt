package com.iesmariaenriquez.pmdm.login_movie_crud.ui.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth

class UserViewModel: ViewModel() {
    private fun showDialog(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun signInWithFirebase(context: Context, username: String, password: String, onLoginSuccess: (FirebaseUser) -> Unit) {
        Firebase.auth.signInWithEmailAndPassword(username, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = Firebase.auth.currentUser
                    if (user != null && user.isEmailVerified) {
                        showDialog(context, "Login successful.")
                        onLoginSuccess(user)
                    } else {
                        showDialog(context, "Account not verified by email.")
                        sendEmailVerification(context)
                    }

                } else {
                    showDialog(context, "Authentication error: ${task.exception?.message}")
                }
            }
    }

    fun signUpWithFirebase(context: Context, username: String, password: String) {
        Firebase.auth.createUserWithEmailAndPassword(username, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    showDialog(context, "User created successfully: $username")
                } else {
                    showDialog(context, "Registration error: ${task.exception?.message}")
                }
            }
    }

    fun sendEmailVerification(context: Context) {
        val user = Firebase.auth.currentUser
        user?.sendEmailVerification()
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    showDialog(context, message = "A verification email has been sent to your email address.")
                } else {
                    showDialog(context, message = "Error sending verification email: ${task.exception?.message}")
                }
            }
    }

}

