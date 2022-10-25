package com.example.yolharakati.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.example.yolharakati.R
import com.example.yolharakati.databinding.FragmentLikeBinding
import com.example.yolharakati.db.MyDbHelper
import com.example.yolharakati.models.Label
import com.example.yolharakati.models.MyObject

class LikeFragment : Fragment(), MyRvAdapter.RvClick {

    private lateinit var binding: FragmentLikeBinding
    private lateinit var myDbHelper: MyDbHelper
    private lateinit var list:ArrayList<Label>
    private lateinit var myRvAdapter: MyRvAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding=FragmentLikeBinding.inflate(layoutInflater)
        list= ArrayList()
        myDbHelper= MyDbHelper(binding.root.context)
        myDbHelper.getAllLabel().forEach {
            if (it.like=="1"){
                list.add(it)
            }
        }
        myRvAdapter= MyRvAdapter(list, this)
        binding.myRv.adapter=myRvAdapter
        return binding.root
    }

    override fun editClick(label: Label) {
        MyObject.edit=true
        MyObject.label=label
        findNavController().navigate(R.id.addLabelFragment, bundleOf("key" to label))
    }

    override fun deleteClick(label: Label) {
        myDbHelper.deleteLabel(label)
        list.remove(label)
        myRvAdapter.notifyDataSetChanged()
    }

    override fun itemClick(label: Label) {
        findNavController().navigate(R.id.labelInfoFragment, bundleOf("key" to label))
    }

    override fun likeClick(label: Label) {
        if (label.like=="1"){
            label.like="0"
            myDbHelper.editLabel(label)
            myRvAdapter.notifyDataSetChanged()
        }else if(label.like=="0"){
            label.like="1"
            myDbHelper.editLabel(label)
            myRvAdapter.notifyDataSetChanged()
        }
    }
}