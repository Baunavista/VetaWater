package com.example.vetamineralwater.ui.theme.screens.privatechat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.vetamineralwater.models.Contact
import com.example.vetamineralwater.models.Message

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrivateChatScreen(navController: NavController, client: Contact) {
    var message by remember { mutableStateOf("") }
    val messages = remember { mutableStateListOf<Message>() }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Chat with ${client.name}") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White,
                    titleContentColor = Color.Blue,
                    navigationIconContentColor = Color.Blue
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(Color.White)
        ) {
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
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PrivateChatScreenPreview() {
    val client = Contact("Client", "client@example.com", "+123456789")
    PrivateChatScreen(navController = rememberNavController(), client = client)
}
