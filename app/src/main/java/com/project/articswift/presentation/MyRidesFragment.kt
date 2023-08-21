package com.project.articswift.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.project.articswift.databinding.FragmentMyRidesBinding

class MyRidesFragment : Fragment() {

    private var _binding: FragmentMyRidesBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentMyRidesBinding.inflate(inflater, container, false)
        return binding.root
    }

}