package com.example.yolharakati.fragments

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.yolharakati.R
import com.example.yolharakati.adapters.StateAdapter
import com.example.yolharakati.databinding.FragmentHomeBinding
import com.example.yolharakati.databinding.TabItemBinding
import com.example.yolharakati.models.PagerItem
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class HomeFragment : Fragment() {
    private lateinit var list:ArrayList<PagerItem>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        list= ArrayList()
        list.add(PagerItem("Ogohlantiruvchi"))
        list.add(PagerItem("Imtiyozli"))
        list.add(PagerItem("Taqiqlovchi"))
        list.add(PagerItem("Buyuruvchi"))
    }

    private lateinit var binding: FragmentHomeBinding
    private lateinit var stateAdapter: StateAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding=FragmentHomeBinding.inflate(layoutInflater)

        binding.home.setOnClickListener {
            Toast.makeText(binding.root.context, "Home", Toast.LENGTH_SHORT).show()
        }
        binding.like.setOnClickListener {
            findNavController().navigate(R.id.likeFragment)
        }
        binding.about.setOnClickListener {
            findNavController().navigate(R.id.aboutAppFragment)
        }


        //set viewpager adapter
        stateAdapter= StateAdapter(list, this)
        binding.myViewpager.adapter=stateAdapter

        //TabLayout Listener
        binding.myTablayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val customView=tab?.customView
                customView?.findViewById<TextView>(R.id.tab_item_tv)!!.setTextColor(Color.parseColor("#005CA1"))
                customView.findViewById<TextView>(R.id.tab_item_tv)!!.setBackgroundColor(Color.WHITE)
//                customView.findViewById<TextView>(R.id.tab_item)!!.setBackgroundResource(R.drawable.tab_item_selected_card)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                val customView=tab?.customView
                customView?.findViewById<TextView>(R.id.tab_item_tv)!!.setBackgroundColor(Color.parseColor("#005CA1"))
                customView?.findViewById<TextView>(R.id.tab_item_tv)!!.setTextColor(Color.WHITE)
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        })

        //set Tablayout Mediator
        TabLayoutMediator(binding.myTablayout,binding.myViewpager){tab,position->
            val tabItemView=TabItemBinding.inflate(layoutInflater)
            //write tablayout properties
            tabItemView.tabItemTv.text=list[position].type
            tab.customView = tabItemView.root
        }.attach()


        //add Label button
        binding.add.setOnClickListener {
            findNavController().navigate(R.id.addLabelFragment)
        }

        return binding.root
    }

}