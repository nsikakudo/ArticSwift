package com.project.articswift.presentation.onboardingscreen

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
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
import com.project.articswift.databinding.FragmentSignInBinding
import io.reactivex.Observable

@SuppressLint("CheckResult")
class SignInFragment : Fragment() {

    private var _binding: FragmentSignInBinding? = null
    private val binding get() = _binding!!

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        setSupportActionBar(binding.toolbar)

        auth = FirebaseAuth.getInstance()

        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()
            loginUser(email, password)
        }

        binding.tvForgotPw.setOnClickListener {
            findNavController().navigate(R.id.action_signInFragment_to_resetPasswordFragment)
        }

        binding.noAccount.setOnClickListener {
            findNavController().navigate(R.id.action_signInFragment_to_signUpFragment)
        }

        val userNameStream = RxTextView.textChanges(binding.etEmail)
            .skipInitialValue()
            .map { username ->
                username.isEmpty()
            }
        userNameStream.subscribe {
            showTextMinimalAlert(it, "Email/Username")
        }

        val passwordStream = RxTextView.textChanges(binding.etPassword)
            .skipInitialValue()
            .map { password ->
                password.isEmpty()
            }
        passwordStream.subscribe {
            showTextMinimalAlert(it, "Password")
        }

        val invalidFieldsStream = Observable.combineLatest(
            userNameStream,
            passwordStream,
        ) { usernameInvalid: Boolean, passwordInvalid: Boolean ->
            !usernameInvalid && !passwordInvalid
        }
        invalidFieldsStream.subscribe { isValid ->
            if (isValid){
                binding.btnLogin.isEnabled = true
                binding.btnLogin.backgroundTintList = ContextCompat.getColorStateList(requireContext(), R.color.blue)
            }else{
                binding.btnLogin.isEnabled = false
                binding.btnLogin.backgroundTintList = ContextCompat.getColorStateList(requireContext(), R.color.grey)
            }
        }
    }

    private fun showTextMinimalAlert(isNotValid : Boolean, text : String){
        if (text == "Email/Username")
            binding.etEmail.error = if (isNotValid) "$text is not up to 6 letters" else null
        else if (text == "password")
            binding.etPassword.error = if (isNotValid) "$text password is incorrect or not up to 8 digit" else null
    }

    private fun loginUser(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener{
                if (it.isSuccessful){
                    findNavController().navigate(R.id.action_signInFragment_to_homeFragment)
                    Toast.makeText(requireContext(), "Login Successful!!!", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(requireContext(), it.exception?.message, Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun onBackPressed() {
        requireActivity().finish()
    }
    override fun onDestroy() {
        onBackPressed()
        Log.d("MYTAG", "Back pressed")
        super.onDestroy()
    }

}