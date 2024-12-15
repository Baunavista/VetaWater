package com.example.vetamineralwater.ui.theme.screens.admin

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.vetamineralwater.R
import com.example.vetamineralwater.models.Contact

data class Message(val sender: String, val content: String, val timestamp: Long)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminMessagingScreen(navController: NavController, clients: List<Contact>) {
    var selectedClient by remember { mutableStateOf<Contact?>(null) }
    var message by remember { mutableStateOf("") }
    val messages = remember { mutableStateListOf<Message>() }

    val recentMessages = clients.associateWith {
        Message("client", "Recent message from ${it.name}", System.currentTimeMillis())
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Admin Messaging") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Black,
                    titleContentColor = Color.Cyan,
                    navigationIconContentColor = Color.Blue
                )
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(Color.LightGray)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .padding(16.dp)
                ) {
                    items(clients) { client ->
                        val context = LocalContext.current
                        Row(
                            modifier = Modifier
                                .padding(8.dp)
                                .clickable {
                                    val intent = Intent(Intent.ACTION_VIEW).apply {
                                        data = Uri.parse("sms:${client.phone}")
                                    }
                                    context.startActivity(intent)
                                },
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.person), // Replace with client's avatar
                                contentDescription = null,
                                modifier = Modifier
                                    .size(48.dp)
                                    .clip(CircleShape)
                                    .background(Color.Gray)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Column {
                                Text(
                                    text = client.name,
                                    fontSize = 18.sp,
                                    color = Color.Black,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = recentMessages[client]?.content ?: "No recent message",
                                    fontSize = 14.sp,
                                    color = Color.Gray
                                )
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                if (selectedClient != null) {
                    LazyColumn(
                        modifier = Modifier
                            .weight(1f)
                            .padding(16.dp),
                        reverseLayout = true
                    ) {
                        items(messages) { msg ->
                            Box(
                                modifier = Modifier
                                    .padding(4.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(if (msg.sender == "client") Color.Blue else Color.Gray)
                                    .padding(8.dp)
                            ) {
                                Text(text = msg.content, color = Color.White)
                            }
                        }
                    }
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        BasicTextField(
                            value = message,
                            onValueChange = { message = it },
                            modifier = Modifier
                                .weight(1f)
                                .background(Color.LightGray, MaterialTheme.shapes.small)
                                .padding(16.dp),
                            textStyle = LocalTextStyle.current.copy(color = Color.Blue)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Button(onClick = {
                            if (message.isNotEmpty()) {
                                messages.add(0, Message("admin", message, System.currentTimeMillis()))
                                message = ""
                            }
                        }) {
                            Text("Send")
                        }
                    }
                }
            }
            FloatingActionButton(
                onClick = { /* Handle email notification click */ },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(bottom = 90.dp, end = 19.dp) // Adjust the padding to move the FAB up
            ) {
                Icon(Icons.Default.Email, contentDescription = "Email Notification")
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AdminMessagingScreenPreview() {
    val clients = listOf(
        Contact("Calvin", "calvin1@gmail.com", "+254 799739529"),
        Contact("Charles", "charles2@gmail.com", "+254 725825449")
    )
    AdminMessagingScreen(navController = rememberNavController(), clients = clients)
}
