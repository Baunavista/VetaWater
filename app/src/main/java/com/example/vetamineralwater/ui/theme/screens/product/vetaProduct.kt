package com.example.vetamineralwater.ui.theme.screens.product

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.Alignment
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
import com.example.vetamineralwater.navigation.ROUTE_DASHBOARD
import com.example.vetamineralwater.navigation.ROUTE_PAY
import com.example.vetamineralwater.navigation.ROUTE_CART
import kotlinx.coroutines.delay

@Composable
fun AddProductScreen(navController: NavController, cartItems: MutableList<Product> = mutableListOf()) {
    val context = LocalContext.current

    // Initialize your product list correctly
    val products = remember { mutableStateListOf(
        Product("VETA", "20Litre", "Ksh:250", "5", "", "1",""),
        Product("VETA", "1Litre", "Ksh:75", "3", "", "2","")
    ) }

    // State to track the number of items in the cart
    var cartCount by remember { mutableStateOf(cartItems.size) }
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
        bottomBar = {
            BottomAppBar(
                actions = {
                    IconButton(onClick = { navController.navigate(ROUTE_DASHBOARD) }) {
                        Icon(Icons.Filled.Home, contentDescription = "Home")
                    }
                    Box {
                        IconButton(onClick = { navController.navigate(ROUTE_CART) }) {
                            Icon(Icons.Filled.ShoppingCart, contentDescription = "Shopping cart")
                        }
                        if (cartCount > 0) {
                            Box(
                                modifier = Modifier
                                    .size(16.dp)
                                    .background(Color.Red, shape = RoundedCornerShape(50))
                                    .align(Alignment.TopEnd)
                            ) {
                                Text(
                                    text = cartCount.toString(),
                                    color = Color.White,
                                    fontSize = 10.sp,
                                    modifier = Modifier.align(Alignment.Center)
                                )
                            }
                        }
                    }
                    IconButton(onClick = {
                        isLoading = true
                    }) {
                        Icon(Icons.Filled.Refresh, contentDescription = "Refresh")
                    }
                },
                floatingActionButton = {
                    FloatingActionButton(
                        onClick = { },
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

            if (isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else {
                LazyColumn(modifier = Modifier.padding(10.dp)) {
                    items(products) { product ->
                        ProductItem(
                            navController = navController,
                            name = product.name,
                            description = product.description,
                            price = product.price,
                            quantity = product.quantity,
                            id = product.id,
                            imageResId = R.drawable.water, // Replace with actual image resource ID
                            onAddToCart = {
                                // Logic to add product to cart
                                cartCount++
                                cartItems.add(product)
                                Toast.makeText(context, "${product.name} added to cart", Toast.LENGTH_SHORT).show()
                            },
                            onPay = {
                                // Navigate to payment screen
                                navController.navigate(ROUTE_PAY)
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ProductItem(
    navController: NavController,
    name: String,
    description: String,
    price: String,
    quantity: String,
    id: String,
    imageResId: Int,
    onAddToCart: () -> Unit,
    onPay: () -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            confirmButton = {
                Button(onClick = {
                    navController.navigate(ROUTE_PAY)
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

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AddProductScreenPreview() {
    val navController = rememberNavController()
    AddProductScreen(navController = navController, cartItems = mutableListOf())
}
