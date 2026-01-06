package com.example.hugyourmug.ui.orders

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hugyourmug.databinding.FragmentOrderDetailsBinding
import com.example.hugyourmug.viewmodel.OrdersViewModel
import java.text.SimpleDateFormat
import java.util.*

class OrderDetailsFragment : Fragment() {

    private var _binding: FragmentOrderDetailsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: OrdersViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOrderDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val orderId = arguments?.getString("orderId") ?: return

        val adapter = OrderDetailsItemsAdapter()
        binding.recyclerOrderItems.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerOrderItems.adapter = adapter

        viewModel.order.observe(viewLifecycleOwner) { order ->
            if (order != null) {
                val sdf = SimpleDateFormat("MMM dd, yyyy • hh:mm a", Locale.getDefault())
                binding.txtOrderDate.text = sdf.format(Date(order.timestamp))
                binding.txtFullName.text = order.fullName
                binding.txtOrderType.text = if (order.isDelivery) "Delivery" else "Pickup"

                if (order.isDelivery) {
                    binding.txtAddress.visibility = View.VISIBLE
                    binding.txtBringChange.visibility = View.VISIBLE
                    binding.txtAddress.text = order.address
                    binding.txtBringChange.text =
                        if (order.bringChange) "Bring change: Yes" else "Bring change: No"
                } else {
                    binding.txtAddress.visibility = View.GONE
                    binding.txtBringChange.visibility = View.GONE
                }

                val progress = if (order.freeItemUsed) 0 else order.pointsEarned
                binding.txtPointsEarned.text = "Points earned: ${order.pointsEarned}"
                binding.txtLoyaltyProgress.text = "Loyalty: $progress / 5"

                if (order.freeItemUsed) {
                    binding.txtFreeItemUsed.visibility = View.VISIBLE
                    binding.txtFreeItemUsed.text =
                        "🎁 Free drink applied: -$%.2f".format(order.freeItemValue)
                } else {
                    binding.txtFreeItemUsed.visibility = View.GONE
                }

                binding.txtTotalPrice.text = "$%.2f".format(order.total)
                adapter.setFreeItemValue(order.freeItemUsed)
            }
        }

        viewModel.orderItems.observe(viewLifecycleOwner) { items ->
            adapter.updateList(items)
        }

        viewModel.loadOrderById(orderId)
        viewModel.loadOrderItems(orderId)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
