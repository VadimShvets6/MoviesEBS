package com.top1shvetsvadim1.moviesebs.presentation.fragments

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(
    parent: Fragment,
    private val fragmentReview: Fragment,
    private val fragmentDescription: Fragment
) : FragmentStateAdapter(parent) {

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> fragmentDescription
            else -> fragmentReview
        }
    }
}