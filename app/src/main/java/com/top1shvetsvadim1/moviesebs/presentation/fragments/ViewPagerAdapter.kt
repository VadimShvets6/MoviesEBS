package com.top1shvetsvadim1.moviesebs.presentation.fragments

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(
    parent: Fragment,
    private val fragmentReview: Fragment,
    private val fragmentDescription: Fragment
) : FragmentStateAdapter(parent) {

    override fun getItemCount(): Int {
        return COUNT_OF_FRAGMENTS_2
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            FIRST_FRAGMENT -> fragmentDescription
            else -> fragmentReview
        }
    }

    companion object{
        private const val COUNT_OF_FRAGMENTS_2 = 2
        private const val FIRST_FRAGMENT = 0
    }
}