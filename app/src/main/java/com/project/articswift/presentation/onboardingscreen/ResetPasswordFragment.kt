package com.project.articswift.presentation.onboardingscreen

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.jakewharton.rxbinding2.widget.RxTextView
import com.project.articswift.R
import com.project.articswift.databinding.FragmentResetPasswordBinding

class ResetPasswordFragment : Fragment() {

    private lateinit var binding: FragmentResetPasswordBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentResetPasswordBinding.inflate(inflater, container, false)


        return binding.root
    }

    @SuppressLint("CheckResult")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()

        binding.backToLogin.setOnClickListener {
            findNavController().navigate(R.id.action_resetPasswordFragment_to_signInFragment)
        }

        binding.btnReset.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()

            auth.sendPasswordResetEmail(email)
                .addOnCompleteListener { reset ->
                    if (reset.isSuccessful){
                        findNavController().navigate(R.id.action_resetPasswordFragment_to_signInFragment)
                        Toast.makeText(requireContext(), "Password changed successfully!!!", Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(requireContext(), reset.exception?.message, Toast.LENGTH_SHORT).show()
                    }
                }
        }

        val emailStream = RxTextView.textChanges(binding.etEmail)
            .skipInitialValue()
            .map { email ->
                !Patterns.EMAIL_ADDRESS.matcher(email).matches()
            }
        emailStream.subscribe {
            showEmailValidateAlert(it)
        }
    }
    private fun showEmailValidateAlert(isNotValid : Boolean){
       if (isNotValid){
           binding.etEmail.error = "Email is not valid"
           binding.btnReset.isEnabled = false
           binding.btnReset.backgroundTintList = ContextCompat.getColorStateList(requireContext(), R.color.grey)
       }else{
           binding.etEmail.error = null
           binding.btnReset.isEnabled = true
           binding.btnReset.backgroundTintList = ContextCompat.getColorStateList(requireContext(), R.color.blue)
       }
    }
}