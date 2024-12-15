package com.example.vetamineralwater.ui.theme.screens.dashboard

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.VectorConverter
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.vetamineralwater.R
import com.example.vetamineralwater.data.AuthViewModel
import com.example.vetamineralwater.navigation.ROUTE_ADD_PRODUCT
import com.example.vetamineralwater.navigation.ROUTE_VIEW_PRODUCT
import com.example.vetamineralwater.navigation.ROUTE_CALL_LOG
import com.example.vetamineralwater.navigation.ROUTE_ADMIN_MESSAGES
import com.example.vetamineralwater.navigation.ROUTE_HOME
import com.example.vetamineralwater.navigation.ROUTE_PAY
import com.google.firebase.auth.FirebaseAuth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Dashboard(navController: NavController) {
    val user = FirebaseAuth.getInstance().currentUser
    val userName = user?.displayName ?: "VetaWater"

    val context = LocalContext.current
    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        LogoutDialog(onDismiss = { showDialog = false }) {
            val authRepository = AuthViewModel()
            authRepository.logout(navController, context)
            showDialog = false
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.background),
            contentDescription = "background image",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
    }
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        TopAppBar(
            title = { Text(text = "Mineral: $userName") },
            navigationIcon = {
                IconButton(onClick = { navController.navigate(ROUTE_HOME) }) {
                    Icon(imageVector = Icons.Filled.AccountCircle, contentDescription = "Person", tint = Color.Blue)
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.LightGray,
                titleContentColor = Color.Blue,
                navigationIconContentColor = Color.Blue
            ),
            actions = {
                IconButton(onClick = { openGoogleMaps(context) }) {
                    Icon(imageVector = Icons.Filled.LocationOn, contentDescription = "Location", tint = Color.Green)
                }
                IconButton(onClick = {
                    navController.navigate(ROUTE_CALL_LOG)
                }) {
                    Icon(imageVector = Icons.Filled.Call, contentDescription = "Call Log", tint = Color.Green)
                }
                IconButton(onClick = {
                    navController.navigate(ROUTE_ADMIN_MESSAGES)
                }) {
                    Icon(imageVector = Icons.Filled.Email, contentDescription = "Messages", tint = Color.Black)
                }
                IconButton(onClick = { showDialog = true }) {
                    Icon(imageVector = Icons.Filled.ExitToApp, contentDescription = "exit", tint = Color.Black)
                }
            }
        )
        Row(modifier = Modifier.wrapContentWidth()) {
            Card(
                modifier = Modifier
                    .wrapContentWidth()
                    .padding(10.dp)
                    .clickable {
                        navController.navigate(ROUTE_ADD_PRODUCT)
                    },
                shape = RoundedCornerShape(10.dp),
                elevation = CardDefaults.cardElevation(10.dp)
            ) {
                Box(modifier = Modifier
                    .height(250.dp)
                    .fillMaxSize()) {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.background),
                            contentDescription = "Profile Image",
                            modifier = Modifier
                                .size(200.dp)
                                .padding(10.dp)
                        )
                        Text(
                            text = "Veta Products",
                            color = Color.Blue,
                            fontStyle = FontStyle.Normal,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                    }
                }
            }
        }
        Row {

            Card(
                modifier = Modifier
                    .padding(10.dp)
                    .clickable {
                        navController.navigate("$ROUTE_VIEW_PRODUCT/true")  // For admin
                    },
                shape = RoundedCornerShape(10.dp),
                elevation = CardDefaults.cardElevation(10.dp)
            ) {
                Box(modifier = Modifier
                    .height(250.dp)
                    .fillMaxSize()) {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.background),
                            contentDescription = "View Client",
                            modifier = Modifier
                                .size(200.dp)
                                .padding(10.dp)
                        )
                        Text(
                            text = "View Product (Admin)",
                            color = Color.Blue,
                            fontStyle = FontStyle.Normal,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                    }
                }
            }
        }

    }
}


fun openGoogleMaps(context: android.content.Context) {
    val intent = Intent(
        Intent.ACTION_VIEW,
        Uri.parse("geo:0,0?q=Veta Water+")
    )
    intent.setPackage("com.google.android.apps.maps")
    context.startActivity(intent)
}

@Composable
fun LogoutDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text(text = "Confirm Logout") },
        text = { Text(text = "Are you sure you want to log out?") },
        confirmButton = {
            Button(onClick = { onConfirm() }) {
                Text("Confirm")
            }
        },
        dismissButton = {
            Button(onClick = { onDismiss() }) {
                Text("Cancel")
            }
        }
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DashboardPreview() {
    Dashboard(navController = rememberNavController())
}
