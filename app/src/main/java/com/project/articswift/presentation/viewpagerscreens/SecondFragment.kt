package com.project.articswift.presentation.viewpagerscreens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.viewpager2.widget.ViewPager2
import com.project.articswift.R


class SecondFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_second, container, false)

        val nextPage = view.findViewById<TextView>(R.id.tvNext2)
        val viewPager = activity?.findViewById<ViewPager2>(R.id.view_pager)

        nextPage.setOnClickListener {
            viewPager?.currentItem = 2
        }

        return view    }


}