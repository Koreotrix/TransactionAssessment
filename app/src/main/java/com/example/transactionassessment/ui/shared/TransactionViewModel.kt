package com.example.transactionassessment.ui.shared

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.transactionassessment.data.Transaction
import com.example.transactionassessment.data.TransactionRepository

class TransactionViewModel(app: Application) : AndroidViewModel(app) {

    private val repository = TransactionRepository(app)
    val transactionData = repository.transactionData
    val selectedTransaction = MutableLiveData<Transaction>()

}