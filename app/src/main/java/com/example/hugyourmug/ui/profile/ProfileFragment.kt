package com.example.hugyourmug.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.hugyourmug.R
import com.example.hugyourmug.databinding.FragmentProfileBinding
import com.example.hugyourmug.viewmodel.ProfileViewModel

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.user.observe(viewLifecycleOwner) { user ->
            binding.txtProfileName.text = "${user.firstName} ${user.lastName}"
            binding.txtProfileEmail.text = user.email
            binding.txtLoyaltyPoints.text = "Loyalty points: ${user.loyaltyPoints} / 5"
        }

        binding.btnMyOrders.setOnClickListener {
            findNavController().navigate(
                R.id.action_profile_to_orderHistory
            )
        }

        viewModel.loadUser()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
