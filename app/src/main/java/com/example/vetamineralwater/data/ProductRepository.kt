package com.example.vetamineralwater.data

import android.content.Context
import android.widget.Toast
import com.example.vetamineralwater.models.Order
import com.google.firebase.firestore.FirebaseFirestore

class ProductRepository {
    private val db = FirebaseFirestore.getInstance()

    fun getAllOrders(orders: MutableList<Order>, context: Context) {
        db.collection("orders")
            .get()
            .addOnSuccessListener { result ->
                orders.clear()
                for (document in result) {
                    val order = document.toObject(Order::class.java)
                    orders.add(order)
                }
                Toast.makeText(context, "Orders fetched successfully", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { exception ->
                Toast.makeText(context, "Error fetching orders: ${exception.message}", Toast.LENGTH_SHORT).show()
            }
    }

    fun getClientOrders(clientId: String, orders: MutableList<Order>, context: Context) {
        db.collection("orders")
            .whereEqualTo("clientId", clientId)
            .get()
            .addOnSuccessListener { result ->
                orders.clear()
                for (document in result) {
                    val order = document.toObject(Order::class.java)
                    orders.add(order)
                }
                Toast.makeText(context, "Client orders fetched successfully", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { exception ->
                Toast.makeText(context, "Error fetching client orders: ${exception.message}", Toast.LENGTH_SHORT).show()
            }
    }
}
