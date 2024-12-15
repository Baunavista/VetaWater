package com.example.vetamineralwater.data

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.vetamineralwater.models.SignupModel
import com.example.vetamineralwater.navigation.ROUTE_DASHBOARD
import com.example.vetamineralwater.navigation.ROUTE_LOGIN
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class AuthViewModel : ViewModel() {
    private val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    private var _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> get() = _errorMessage

    fun signup(
        username: String,
        email: String,
        password: String,
        confirmPassword: String,
        navController: NavController,
        context: Context
    ) {
        if (username.isBlank() || email.isBlank() || password.isBlank() || confirmPassword.isBlank()) {
            _errorMessage.value = "Please fill in all fields"
            showToast("Please fill in all fields",context)
            return
        }
        if (password != confirmPassword) {
            _errorMessage.value = "Passwords do not match"
            showToast("Passwords do not match", context)
            return
        }
        _isLoading.value = true
        mAuth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener { task ->
                _isLoading.value = false
                if (task.isSuccessful) {
                    val userId = mAuth.currentUser?.uid ?: ""
                    val userData = SignupModel(
                        userName = username, email = email, password = password,
                        confirmPassword = confirmPassword, userId = userId
                    )
                    saveUserToDatabase(userId, userData, navController, context)

                    val user = mAuth.currentUser
                    val profile = UserProfileChangeRequest.Builder()
                        .setDisplayName(username)
                        .build()
                    user?.updateProfile(profile)?.addOnCompleteListener {
                        updateTask -> if (updateTask.isSuccessful){
                            showToast("Display name set correctly",context)
                    }else{
                        showToast("Failed to set display name",context)
                    }
                    }
                } else {
                    _errorMessage.value = task.exception?.message
                    showToast(task.exception?.message ?: "Registration error", context)
                }
            }
    }

    private fun saveUserToDatabase(
        userId: String,
        userData: SignupModel,
        navController: NavController,
        context: Context
    ) {
        val regRef = FirebaseDatabase.getInstance()
            .getReference("Users/$userId")
        regRef.setValue(userData).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                _errorMessage.value = "Registration Successful"
                showToast("Registration Successful", context)
                navController.navigate(ROUTE_LOGIN) {
                    popUpTo("register") {inclusive = true }
                }
            } else {
                _errorMessage.value = task.exception?.message
                showToast(task.exception?.message ?: "Database error", context)
            }
        }
    }

    private fun showToast(message: String, context: Context) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
    fun login(email: String,password: String,navController: NavController,
              context: Context){
        if (email.isBlank() || password.isBlank()){
            showToast("Email and password required",context)
            return
        }
        _isLoading.value = true

        mAuth.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener { task ->
            _isLoading.value = false
            if (task.isSuccessful){
                showToast("User Successfully logged in",context)
                navController.navigate(ROUTE_DASHBOARD)
            }else{
                _errorMessage.value = task.exception?.message
                showToast(task.exception?.message ?: "Logim failed", context)
            }
        }
    }
    fun logout(navController: NavController,context: Context){
        FirebaseAuth.getInstance().signOut()
        showToast("Logged Out Successfully",context)
        navController.navigate(ROUTE_LOGIN)
    }

}