package com.example.transactionassessment.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.transactionassessment.R
import com.example.transactionassessment.data.Transaction
import com.example.transactionassessment.databinding.FragmentMainBinding
import com.example.transactionassessment.ui.shared.TransactionViewModel


class MainFragment : Fragment(), MainRecyclerAdapter.TransactionItemListener {

    private lateinit var viewModel: TransactionViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)

        viewModel = ViewModelProvider(requireActivity()).get(TransactionViewModel::class.java)
        viewModel.transactionData.observe(viewLifecycleOwner, Observer{
            val adapter = MainRecyclerAdapter(requireContext(), it, this)
            recyclerView.adapter = adapter
        })

        val binding = FragmentMainBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        recyclerView = binding.rvTransactionlist

        return binding.root

    }

    override fun onTransactionItemClick(transaction: Transaction) {

        viewModel.selectedTransaction.value = transaction
        navController.navigate(R.id.action_navigate_to_detailFragment)

    }


}