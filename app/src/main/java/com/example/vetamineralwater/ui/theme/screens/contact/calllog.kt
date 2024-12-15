package com.example.vetamineralwater.ui.theme.screens.calllog

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.vetamineralwater.models.Contact

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CallLogScreen(navController: NavController, contacts: List<Contact>) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Call Log") },
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
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            LazyColumn(modifier = Modifier.padding(16.dp)) {
                items(contacts) { contact ->
                    ContactItem(
                        contact = contact,
                        navController = navController
                    )
                }
            }
        }
    }
}

@Composable
fun ContactItem(contact: Contact, navController: NavController) {
    val context = LocalContext.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable {  }
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(contact.name, fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Text(contact.phone, fontSize = 16.sp, color = Color.Gray)
        }
        IconButton(onClick = {
            val intent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("smsto:${contact.phone}")
                putExtra("sms_body", "Hello ${contact.name}")
            }
            val chooser = Intent.createChooser(intent, "Send message via")
            context.startActivity(chooser)
        }) {
            Icon(imageVector = Icons.Filled.Email, contentDescription = "Message")
        }
        IconButton(onClick = {
            val intent = Intent(Intent.ACTION_DIAL).apply {
                data = Uri.parse("tel:${contact.phone}")
            }
            context.startActivity(intent)
        }) {
            Icon(imageVector = Icons.Filled.Call, contentDescription = "Call")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CallLogScreenPreview() {
    val contacts = listOf(
        Contact("John Doe", "john@example.com", "123-456-7890"),
        Contact("Jane Smith", "jane@example.com", "098-765-4321")
    )
    CallLogScreen(navController = rememberNavController(), contacts = contacts)
}
