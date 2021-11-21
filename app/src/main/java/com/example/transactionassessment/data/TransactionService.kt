package com.example.transactionassessment.data

import retrofit2.Response
import retrofit2.http.GET

interface TransactionService {
    @GET("api/v1/transactions")
    suspend fun getTransactions(): Response<List<Transaction>>
}