package com.example.hugyourmug.ui.orders

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hugyourmug.R
import com.example.hugyourmug.databinding.FragmentOrderHistoryBinding
import com.example.hugyourmug.viewmodel.OrdersViewModel

class OrderHistoryFragment : Fragment() {

    private var _binding: FragmentOrderHistoryBinding? = null
    private val binding get() = _binding!!

    private val viewModel: OrdersViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOrderHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerOrderHistory.layoutManager =
            LinearLayoutManager(requireContext())

        val adapter = OrderHistoryAdapter { order ->
            val bundle = Bundle().apply {
                putString("orderId", order.id)
            }
            findNavController().navigate(
                R.id.orderDetailsFragment,
                bundle
            )
        }

        binding.recyclerOrderHistory.adapter = adapter

        viewModel.orders.observe(viewLifecycleOwner) { orders ->
            adapter.updateList(orders)
        }

        viewModel.loadOrders()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
