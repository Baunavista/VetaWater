package com.example.vetamineralwater.models

data class Order(
    val productId: String = "",
    val productName: String = "",
    val quantity: String = "",
    val price: String = "",
    val orderDate: String = "",
    val orderTime: String = "",
    val clientId: String = ""
)
