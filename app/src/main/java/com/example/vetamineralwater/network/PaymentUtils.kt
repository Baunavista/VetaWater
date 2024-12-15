package com.example.vetamineralwater.network



import android.app.Activity
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

fun makePayment(context: Context, phoneNumber: String, amount: String) {
    val paymentRequest = PaymentRequest(phoneNumber, amount)

    CoroutineScope(Dispatchers.Main).launch {
        try {
            val response = withContext(Dispatchers.IO) {
                RetrofitClient.apiService.makePayment(paymentRequest)
            }

            if (response.isSuccessful) {
                Toast.makeText(context, response.body()?.message, Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(context, "Payment failed", Toast.LENGTH_LONG).show()
            }
        } catch (e: Exception) {
            Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }
}


