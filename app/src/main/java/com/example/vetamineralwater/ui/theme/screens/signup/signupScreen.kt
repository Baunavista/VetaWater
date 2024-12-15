package com.example.vetamineralwater.ui.theme.screens.signup

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.vetamineralwater.R
import com.example.vetamineralwater.data.AuthViewModel
import com.example.vetamineralwater.navigation.ROUTE_LOGIN

@Composable
fun SignupScreen(navController: NavController){
    val context = LocalContext.current
    val authViewModel: AuthViewModel = viewModel()

    val isLoading by authViewModel.isLoading.collectAsState()

    var username by remember {
        mutableStateOf(value = "")
    }
    var email by remember {
        mutableStateOf(value = "")
    }
    var password by remember {
        mutableStateOf(value = "")
    }
    var confirmPassword by remember {
        mutableStateOf(value = "")
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Register Here",
            fontSize = 20.sp,
            color = Color.White,
            fontFamily = FontFamily.SansSerif,
            fontStyle = FontStyle.Normal,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .background(Color.Black)
                .padding(20.dp)
                .fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(10.dp))
        Image(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .height(80.dp),
            painter = painterResource(id = R.drawable.background),
            contentDescription = "Veta Logo"
        )
        Spacer(modifier = Modifier.height(10.dp))

        Box(
            modifier = Modifier
                .background(Color.Transparent)
                .padding(16.dp)
        ) {
            Column {
                OutlinedTextField(
                    value = username,
                    onValueChange = { userName -> username = userName },
                    label = { Text(text = "Enter username") },
                    placeholder = { Text(text = "Please enter username") }
                )
                Spacer(modifier = Modifier.height(10.dp))
                OutlinedTextField(
                    value = email,
                    onValueChange = { newEmail -> email = newEmail },
                    label = { Text(text = "Enter email") },
                    placeholder = { Text(text = "Please enter email") }
                )
                Spacer(modifier = Modifier.height(10.dp))
                OutlinedTextField(
                    value = password,
                    onValueChange = { Password -> password = Password },
                    label = { Text(text = "Enter password") },
                    placeholder = { Text(text = "Please enter password") }
                )
                Spacer(modifier = Modifier.height(10.dp))
                OutlinedTextField(
                    value = confirmPassword,
                    onValueChange = { newConfirmPassword -> confirmPassword = newConfirmPassword },
                    label = { Text(text = "Confirm Password") },
                    placeholder = { Text(text = "Please enter Password") }
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))
        Button(
            onClick = {
                authViewModel.signup(username, email, password, confirmPassword, navController, context)
            },
            enabled = !isLoading,
            colors = ButtonDefaults.buttonColors(Color.Black),
            modifier = Modifier
                .wrapContentWidth()
                .align(Alignment.CenterHorizontally)
        ) {
            if (isLoading) {
                CircularProgressIndicator(color = Color.Black, strokeWidth = 4.dp)
            } else {
                Text(
                    modifier = Modifier.padding(10.dp),
                    text = "REGISTER HERE"
                )
            }
        }
        Spacer(modifier = Modifier.height(10.dp))

        ClickableText( text = AnnotatedString("Already have an account? Sign in"),
            onClick = { navController.navigate(ROUTE_LOGIN) },
            style = TextStyle( color = Color.Blue, fontSize = 16.sp,
                fontWeight = FontWeight.Bold, textAlign = TextAlign.Center ) )

    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SignupScreenPreview() {
    SignupScreen(rememberNavController())
}
