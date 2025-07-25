package com.example.myloginapp.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.myloginapp.MainActivity
import com.google.firebase.auth.FirebaseAuth

@Composable
fun LoginScreen(auth: FirebaseAuth, context: Context, onSwitchToSignUp: () -> Unit) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Login")
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth()
        )
        Button(onClick = {
            if (email.isNotBlank() && password.isNotBlank()) {
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            context.startActivity(Intent(context, MainActivity::class.java))
                            if (context is Activity) context.finish()
                        } else {
                            errorMessage = task.exception?.message
                        }
                    }
            } else {
                errorMessage = "Please fill all fields"
            }
        }) {
            Text("Sign In")
        }
        TextButton(onClick = onSwitchToSignUp) {
            Text("Don't have an account? Sign Up")
        }
        errorMessage?.let {
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = it, color = Color.Red)
        }
    }
}

@Composable
fun SignUpScreen(auth: FirebaseAuth, context: Context, onSwitchToLogin: () -> Unit) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Sign Up")
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth()
        )
        Button(onClick = {
            if (email.isNotBlank() && password.isNotBlank()) {
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            context.startActivity(Intent(context, MainActivity::class.java))
                            if (context is Activity) context.finish()
                        } else {
                            errorMessage = task.exception?.message
                        }
                    }
            } else {
                errorMessage = "Please fill all fields"
            }
        }) {
            Text("Sign Up")
        }
        TextButton(onClick = onSwitchToLogin) {
            Text("Already have an account? Log In")
        }
        errorMessage?.let {
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = it, color = Color.Red)
        }
    }
}
