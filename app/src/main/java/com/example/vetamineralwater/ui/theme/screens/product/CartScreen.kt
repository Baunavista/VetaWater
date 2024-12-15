package com.example.vetamineralwater.ui.theme.screens.product

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.vetamineralwater.R
import com.example.vetamineralwater.models.Product
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(navController: NavController, cartItems: List<Product>) {
    val context = LocalContext.current
    var isLoading by remember { mutableStateOf(false) }

    // LaunchedEffect to handle the loading state
    LaunchedEffect(isLoading) {
        if (isLoading) {
            delay(2000)
            isLoading = false
            Toast.makeText(context, "Refreshed successfully", Toast.LENGTH_SHORT).show()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Cart") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = {
                        isLoading = true
                    }) {
                        Icon(Icons.Filled.Refresh, contentDescription = "Refresh")
                    }
                }
            )
        }
    ) { innerPadding ->
        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            Column(modifier = Modifier.padding(innerPadding)) {
                Text(
                    text = "Cart Items",
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding()
                )

                LazyColumn(modifier = Modifier.padding(10.dp)) {
                    items(cartItems) { product ->
                        CartItem(
                            name = product.name,
                            description = product.description,
                            price = product.price,
                            quantity = product.quantity,
                            imageResId = R.drawable.water // Replace with actual image resource ID
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CartItem(
    name: String,
    description: String,
    price: String,
    quantity: String,
    imageResId: Int
) {
    Card(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Gray)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Image(
                painter = painterResource(id = imageResId),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))
            Text(text = name, fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.White)
            Text(text = description, fontSize = 16.sp, color = Color.White)
            Text(text = "Price: $price", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.White)
            Text(text = "Quantity: $quantity", fontSize = 16.sp, color = Color.White)
        }
    }
}
