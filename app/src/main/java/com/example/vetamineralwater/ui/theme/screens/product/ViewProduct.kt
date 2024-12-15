package com.example.vetamineralwater.ui.theme.screens.product

import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.vetamineralwater.R
import com.example.vetamineralwater.models.Order
import com.example.vetamineralwater.data.ProductViewModel

@Composable
fun ViewProducts(navController: NavHostController, isAdmin: Boolean, clientId: String? = null) {
    val context = LocalContext.current

    val productRepository = remember { ProductViewModel() }
    val products = remember { mutableStateListOf<Order>() }

    LaunchedEffect(isAdmin, clientId) {
        try {
            productRepository.getAllOrders(products, context)
            Log.d("ViewProducts", "Products fetched: ${products.size}")
        } catch (e: Exception) {
            Log.e("ViewProducts", "Error fetching products: ${e.message}", e)
        }
    }

    if (products.isEmpty()) {
        Text("No products available", modifier = Modifier.padding(16.dp))
    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
        ) {
            items(products) { product ->
                ProductCard(
                    product = product,
                    isAdmin = isAdmin,
                    navController = navController,
                    productRepository = productRepository
                )
            }
        }
    }
}

@Composable
fun ProductCard(
    product: Order,
    isAdmin: Boolean,
    navController: NavHostController,
    productRepository: ProductViewModel
) {
    val context = LocalContext.current
    val imageUri = remember { mutableStateOf<Uri?>(null) }
    Column(modifier = Modifier.fillMaxWidth()) {
        Card(
            modifier = Modifier
                .padding(10.dp)
                .height(250.dp),
            shape = RoundedCornerShape(10.dp),
            colors = CardDefaults.cardColors(containerColor = Color.LightGray)
        ) {
            Column {
                Image(
                    painter = painterResource(id = R.drawable.water), // Replace with actual image resource ID
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                )

                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = product.productName,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                Text(
                    text = "Quantity: ${product.quantity}",
                    fontSize = 16.sp,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                Text(
                    text = "Price: ${product.price}",
                    fontSize = 16.sp,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )

                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    if (isAdmin) {
                        Button(
                            onClick = {
                                try {
                                    productRepository.deleteProduct(context, product.productId, navController)
                                    Toast.makeText(context, "Product removed successfully", Toast.LENGTH_SHORT).show()
                                } catch (e: Exception) {
                                    Toast.makeText(context, "Error removing product: ${e.message}", Toast.LENGTH_SHORT).show()
                                }
                            },
                            colors = ButtonDefaults.buttonColors(Color.Red)
                        ) {
                            Text(text = "Remove", color = Color.White)
                        }
                        Button(
                            onClick = {
                                try {
                                    val newDetails = mapOf(
                                        "productName" to product.productName,
                                        "quantity" to product.quantity,
                                        "price" to product.price,
                                        "orderDate" to product.orderDate,
                                        "orderTime" to product.orderTime
                                    )
                                    productRepository.updateProduct(context, product.productId, newDetails, imageUri.value, navController)
                                    Toast.makeText(context, "Product updated successfully", Toast.LENGTH_SHORT).show()
                                } catch (e: Exception) {
                                    Toast.makeText(context, "Error updating product: ${e.message}", Toast.LENGTH_SHORT).show()
                                }
                            },
                            colors = ButtonDefaults.buttonColors(Color.Green)
                        ) {
                            Text(text = "Update", color = Color.White)
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ViewProductsPreview() {
    val sampleProducts = listOf(
        Order("1", "Product 1", "10", "100", "2023-01-01", "10:00 AM"),
        Order("2", "Product 2", "5", "200", "2023-01-02", "11:00 AM"),
        Order("3", "Product 3", "20", "150", "2023-01-03", "12:00 PM")
    )
    ViewProducts(rememberNavController(), isAdmin = true, clientId = null)
}

