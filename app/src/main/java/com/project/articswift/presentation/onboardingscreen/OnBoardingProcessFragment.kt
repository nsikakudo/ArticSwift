package com.project.articswift.presentation.onboardingscreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.project.articswift.R
import com.project.articswift.databinding.FragmentOnBoardingProcessBinding

class OnBoardingProcessFragment : Fragment() {

    private lateinit var binding: FragmentOnBoardingProcessBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentOnBoardingProcessBinding.inflate(inflater, container, false)



        return binding.root
//        return inflater.inflate(R.layout.fragment_on_boarding_process, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnPassenger.setOnClickListener {
            findNavController().navigate(R.id.action_onBoardingProcessFragment_to_signUpFragment)
        }

        binding.btnDriver.setOnClickListener {
            findNavController().navigate(R.id.action_onBoardingProcessFragment_to_signUpFragment)
        }
    }

}