package com.project.articswift.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.project.articswift.R
import com.project.articswift.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()

        binding.btnLogout.setOnClickListener {
            val action = ProfileFragmentDirections.actionProfileFragmentToSignInFragment()
            val navOptions = NavOptions.Builder()
                .setPopUpTo(R.id.signInFragment, true)
                .build()
            findNavController().navigate(action, navOptions)
            auth.signOut()
            Toast.makeText(requireContext(), "Logout Successfully!!!", Toast.LENGTH_SHORT).show()
        }
    }

}
