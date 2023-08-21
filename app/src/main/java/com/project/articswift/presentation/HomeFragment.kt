package com.project.articswift.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.findNavController
import com.google.android.material.navigation.NavigationView
import com.project.articswift.R
import com.project.articswift.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var toggle: ActionBarDrawerToggle

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val drawerLayout: DrawerLayout = binding.drawerLayout

        binding.fab.setOnClickListener {
            drawerLayout.openDrawer(binding.navView)
        }


        val navigationView: NavigationView = view.findViewById(R.id.nav_view)
        val headerLayout: View = navigationView.getHeaderView(0).findViewById(R.id.nav_header)

        headerLayout.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_profileFragment)
        }


        binding.navView.setNavigationItemSelectedListener {

            when(it.itemId){

                R.id.payment -> replaceFragment(PaymentFragment())
                R.id.promotions -> replaceFragment(PromotionFragment())
                R.id.my_ride -> replaceFragment(MyRidesFragment())
                R.id.expense_your_ride -> replaceFragment(ExpenseRideFragment())
                R.id.support -> replaceFragment(SupportFragment())
                R.id.about -> replaceFragment(AboutFragment())
                R.id.settings -> replaceFragment(SettingsFragment())
                R.id.logout -> Toast.makeText(requireContext(), "Clicked Logout", Toast.LENGTH_SHORT).show()
                R.id.share -> Toast.makeText(requireContext(), "Clicked Share", Toast.LENGTH_SHORT).show()
            }
            true
        }
    }

    private fun Fragment.replaceFragment(fragment: Fragment) {
        val fragmentManager: FragmentManager = requireActivity().supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

    @Deprecated("Deprecated in Java")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }

}