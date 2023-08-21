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
import com.project.articswift.databinding.FragmentSignUpBinding
import io.reactivex.Observable

@SuppressLint("CheckResult")
class SignUpFragment : Fragment() {

    private lateinit var binding: FragmentSignUpBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment

        binding = FragmentSignUpBinding.inflate(inflater, container, false)

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()

        binding.btnRegister.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()
            registerUser(email, password)
        }

        binding.haveAccount.setOnClickListener {
            findNavController().navigate(R.id.action_signUpFragment_to_signInFragment)
        }

        val nameStream = RxTextView.textChanges(binding.etFullName)
            .skipInitialValue()
            .map { name ->
                name.isEmpty()
            }
        nameStream.subscribe {
            showNameExistAlert(it)
        }

        val emailStream = RxTextView.textChanges(binding.etEmail)
            .skipInitialValue()
            .map { email ->
                !Patterns.EMAIL_ADDRESS.matcher(email).matches()
            }
        emailStream.subscribe {
            showEmailValidateAlert(it)
        }

        val userNameStream = RxTextView.textChanges(binding.etUsername)
            .skipInitialValue()
            .map { username ->
                username.length < 6
            }
        userNameStream.subscribe {
            showTextMinimalAlert(it, "Username")
        }

        val passwordStream = RxTextView.textChanges(binding.etPassword)
            .skipInitialValue()
            .map { password ->
                password.length < 8
            }
        passwordStream.subscribe {
            showTextMinimalAlert(it, "Password")
        }

        val passwordConfirmStream = Observable.merge(
            RxTextView.textChanges(binding.etPassword)
                .skipInitialValue()
                .map { password ->
                    password.toString() != binding.etConfirmPw.text.toString()
                },
            RxTextView.textChanges(binding.etConfirmPw)
                .skipInitialValue()
                .map { confirmPassword ->
                    confirmPassword.toString() != binding.etPassword.text.toString()
                })
        passwordConfirmStream.subscribe {
            showPasswordConfirmAlert(it)
        }

        val invalidFieldsStream = Observable.combineLatest(
            nameStream,
            emailStream,
            userNameStream,
            passwordStream,
            passwordConfirmStream
        ) { nameInvalid: Boolean, emailInvalid: Boolean, usernameInvalid: Boolean, passwordInvalid: Boolean, passwordConfirmInvalid: Boolean ->
            !nameInvalid && !emailInvalid && !usernameInvalid && !passwordInvalid && !passwordConfirmInvalid
        }
        invalidFieldsStream.subscribe { isValid ->
            if (isValid){
                binding.btnRegister.isEnabled = true
                binding.btnRegister.backgroundTintList = ContextCompat.getColorStateList(requireContext(), R.color.blue)
            }else{
                binding.btnRegister.isEnabled = false
                binding.btnRegister.backgroundTintList = ContextCompat.getColorStateList(requireContext(), R.color.grey)
            }
        }
    }

    private fun showNameExistAlert(isNotValid : Boolean){
        binding.etFullName.error = if (isNotValid) "Nsikak udo Nkereuwem" else null
    }

    private fun showTextMinimalAlert(isNotValid : Boolean, text : String){
        if (text == "username")
            binding.etUsername.error = if (isNotValid) "$text is not up to 8 letters" else null
        else if (text == "password")
            binding.etPassword.error = if (isNotValid) "$text is not up to 8 digit" else null
    }

    private fun showEmailValidateAlert(isNotValid : Boolean){
        binding.etEmail.error = if (isNotValid) "Email is not valid" else null
    }

    private fun showPasswordConfirmAlert(isNotValid : Boolean){
        binding.etConfirmPw.error = if (isNotValid) "Password is not the same" else null
    }

    private fun registerUser(email : String, password : String){
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener{
                if (it.isSuccessful){
                    findNavController().navigate(R.id.action_signUpFragment_to_signInFragment)
                    Toast.makeText(requireContext(), "Registration completed", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(requireContext(),it.exception?.message, Toast.LENGTH_SHORT).show()
                }
            }
    }
}