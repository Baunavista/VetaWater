package com.example.vetamineralwater.ui.theme.screens.product

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.vetamineralwater.R
import com.example.vetamineralwater.models.Product

@Composable
fun AddProductScreen(navController: NavController) {
    val context = LocalContext.current

    // Initialize your product list correctly
    val products = remember { mutableStateListOf(
        Product("VETA", "20Litre", "Ksh:250", "5", "", "1",""),
        Product("VETA", "1Litre", "Ksh:75", "3", "", "2","")
    ) }

    Scaffold(
        bottomBar = {
            BottomAppBar(
                actions = {
                    IconButton(onClick = { /* Navigate to Home */ }) {
                        Icon(Icons.Filled.Home, contentDescription = "Home")
                    }
                    IconButton(onClick = { /* Handle Shopping Cart */ }) {
                        Icon(Icons.Filled.ShoppingCart, contentDescription = "Shopping cart")
                    }
                    IconButton(onClick = { /* Handle Refresh */ }) {
                        Icon(Icons.Filled.Refresh, contentDescription = "Refresh")
                    }
                },
                floatingActionButton = {
                    FloatingActionButton(
                        onClick = { /* Handle Profile */ },
                        containerColor = BottomAppBarDefaults.bottomAppBarFabColor,
                        elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation()
                    ) {
                        Icon(Icons.Filled.AccountCircle, contentDescription = "Profile")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            Text(
                text = "Products",
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding()
            )

            LazyColumn(modifier = Modifier.padding(10.dp)) {
                items(products) { product ->
                    ProductItem(
                        name = product.name,
                        description = product.description,
                        price = product.price,
                        quantity = product.quantity,
                        id = product.id,
                        onAddToCart = {
                            // Logic to add product to cart
                            Toast.makeText(context, "${product.name} added to cart", Toast.LENGTH_SHORT).show()
                        },
                        onPay = {
                            // Logic to handle payment
                            Toast.makeText(context, "Proceeding to payment for ${product.name}", Toast.LENGTH_SHORT).show()
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun ProductItem(
    name: String,
    description: String,
    price: String,
    quantity: String,
    id: String,
    onAddToCart: () -> Unit,
    onPay: () -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            confirmButton = {
                Button(onClick = {
                    showDialog = false
                    onPay()
                }) {
                    Text("Confirm")
                }
            },
            dismissButton = {
                Button(onClick = { showDialog = false }) {
                    Text("Cancel")
                }
            },
            title = { Text(text = "Confirm Purchase") },
            text = { Text(text = "Do you want to proceed with the payment for $name?") }
        )
    }

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
                painter = painterResource(id = R.drawable.water),
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
            Spacer(modifier = Modifier.height(8.dp))
            Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth()) {
                Button(
                    onClick = onAddToCart,
                    colors = ButtonDefaults.buttonColors(Color.Blue)
                ) {
                    Text(text = "Add to Cart", color = Color.White)
                }
                Button(
                    onClick = { showDialog = true },
                    colors = ButtonDefaults.buttonColors(Color.Magenta)
                ) {
                    Text(text = "Pay", color = Color.White)
                }
            }
        }
    }
}

