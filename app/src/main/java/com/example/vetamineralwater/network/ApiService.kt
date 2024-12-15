package com.example.vetamineralwater.network



import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

data class PaymentRequest(val phoneNumber: String, val amount: String)

data class PaymentResponse(val status: String, val message: String)

interface ApiService {
    @POST("darajaapi/")
    suspend fun makePayment(@Body request: PaymentRequest): Response<PaymentResponse>
}