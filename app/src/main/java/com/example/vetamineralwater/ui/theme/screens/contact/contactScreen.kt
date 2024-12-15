package com.example.vetamineralwater.ui.theme.screens.contact

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.vetamineralwater.models.Contact

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactScreen(navController: NavController, contacts: List<Contact>, onContactSelected: (Contact) -> Unit = {}) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Select Contact") },
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
        Column(modifier = Modifier.padding(innerPadding).fillMaxSize()) {
            LazyColumn(modifier = Modifier.padding(16.dp)) {
                items(contacts) { contact ->
                    ContactItem(contact) {
                        onContactSelected(contact)
                    }
                }
            }
        }
    }
}

@Composable
fun ContactItem(contact: Contact, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable(onClick = onClick)
    ) {
        Text(contact.name, fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Text(contact.email, fontSize = 16.sp, color = Color.Gray)
        Text(contact.phone, fontSize = 16.sp, color = Color.Gray)
    }
}

@Preview(showBackground = true)
@Composable
fun ContactScreenPreview() {
    val contacts = listOf(
        Contact("John Doe", "john@example.com", "123-456-7890"),
        Contact("Jane Smith", "jane@example.com", "098-765-4321")
    )
    ContactScreen(navController = rememberNavController(), contacts = contacts)
}
