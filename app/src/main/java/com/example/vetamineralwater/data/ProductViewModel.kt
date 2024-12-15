package com.example.vetamineralwater.data

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.navigation.NavHostController
import com.example.vetamineralwater.models.Order
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage

class ProductViewModel {

    private val db = FirebaseFirestore.getInstance()
    private val storage = FirebaseStorage.getInstance()

    fun getAllOrders(products: MutableList<Order>, context: Context) {
        db.collection("orders")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val order = document.toObject(Order::class.java)
                    products.add(order)
                }
            }
            .addOnFailureListener { exception ->
                Log.e("ProductViewModel", "Error getting documents: ", exception)
            }
    }

    fun deleteProduct(context: Context, productId: String, navController: NavHostController) {
        db.collection("orders").document(productId)
            .delete()
            .addOnSuccessListener {
                Log.d("ProductViewModel", "DocumentSnapshot successfully deleted!")
                navController.navigateUp()
            }
            .addOnFailureListener { e ->
                Log.w("ProductViewModel", "Error deleting document", e)
            }
    }

    fun updateProduct(context: Context, productId: String, newDetails: Map<String, Any>, imageUri: Uri?, navController: NavHostController) {
        val docRef = db.collection("orders").document(productId)

        if (imageUri != null) {
            val storageRef = storage.reference.child("images/${imageUri.lastPathSegment}")
            val uploadTask = storageRef.putFile(imageUri)

            uploadTask.addOnSuccessListener {
                storageRef.downloadUrl.addOnSuccessListener { uri ->
                    val updatedDetails = newDetails.toMutableMap()
                    updatedDetails["imageUrl"] = uri.toString()

                    docRef.update(updatedDetails)
                        .addOnSuccessListener {
                            Log.d("ProductViewModel", "DocumentSnapshot successfully updated!")
                            navController.navigateUp()
                        }
                        .addOnFailureListener { e ->
                            Log.w("ProductViewModel", "Error updating document", e)
                        }
                }
            }.addOnFailureListener { e ->
                Log.w("ProductViewModel", "Error uploading image", e)
            }
        } else {
            docRef.update(newDetails)
                .addOnSuccessListener {
                    Log.d("ProductViewModel", "DocumentSnapshot successfully updated!")
                    navController.navigateUp()
                }
                .addOnFailureListener { e ->
                    Log.w("ProductViewModel", "Error updating document", e)
                }
        }
    }
}
