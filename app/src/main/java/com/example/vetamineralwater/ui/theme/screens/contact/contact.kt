package com.example.vetamineralwater.ui.theme.screens.contact

import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import com.example.vetamineralwater.R


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.vetamineralwater.navigation.ROUTE_MESSAGES

data class Contact(val name: String, val email: String, val phone: String)

@Composable
fun ContactScreen(navController: NavController, contacts: List<Contact>) {
    LazyColumn {
        items(contacts) { contact ->
            Column(modifier = Modifier
                .clickable {
                    navController.navigate("$ROUTE_MESSAGES/${contact.name}")
                }
                .padding(16.dp)) {
                Text(text = contact.name)
                Text(text = contact.email)
                Text(text = contact.phone)
                Icon(imageVector = ImageVector.vectorResource(id = R.drawable.call_call_24), contentDescription = "Call Icon")
                Icon(imageVector = ImageVector.vectorResource(id = R.drawable.message_message_24), contentDescription = "Message Icon")
            }
        }
    }
}
