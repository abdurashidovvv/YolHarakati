package com.example.yolharakati.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.yolharakati.fragments.ViewPagerItemFragment
import com.example.yolharakati.models.PagerItem

class StateAdapter(val list:ArrayList<PagerItem>, fragment: Fragment)
    : FragmentStateAdapter(fragment){
    override fun getItemCount(): Int {
        return list.size
    }

    override fun createFragment(position: Int): Fragment {
        return ViewPagerItemFragment.newInstance(list[position].type.toString(), "")
    }
}