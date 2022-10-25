package com.example.yolharakati.fragments

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.yolharakati.R
import com.example.yolharakati.databinding.FragmentLabelInfoBinding
import com.example.yolharakati.models.Label


class LabelInfoFragment : Fragment() {

    private lateinit var binding: FragmentLabelInfoBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding= FragmentLabelInfoBinding.inflate(layoutInflater)
        val label=arguments?.getSerializable("key") as Label
        binding.infoToolbar.text=label.name
        binding.infoBack.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.infoTv1.text=label.name
        binding.infoTv2.text=label.info
        binding.infoImg.setImageURI(Uri.parse(label.img))
        return binding.root
    }

}