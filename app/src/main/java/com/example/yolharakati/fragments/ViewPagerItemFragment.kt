package com.example.yolharakati.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.example.yolharakati.R
import com.example.yolharakati.databinding.FragmentViewPagerItemBinding
import com.example.yolharakati.db.MyDbHelper
import com.example.yolharakati.models.Label
import com.example.yolharakati.models.MyObject

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class ViewPagerItemFragment : Fragment(), MyRvAdapter.RvClick {
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    private lateinit var binding: FragmentViewPagerItemBinding
    private lateinit var myRvAdapter: MyRvAdapter
    private lateinit var list:ArrayList<Label>
    private lateinit var myDbHelper: MyDbHelper
    private val TAG = "ViewPagerItemFragment"
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding= FragmentViewPagerItemBinding.inflate(layoutInflater)
        myDbHelper= MyDbHelper(binding.root.context)
        list=ArrayList()
        if (param1=="Ogohlantiruvchi"){
            myDbHelper.getAllLabel().forEach {
                Log.d(TAG, "onCreateView: ${myDbHelper.getAllLabel()}")
                if (it.type==param1){
                    list.add(it)
                }
            }
            myRvAdapter= MyRvAdapter(list,this)
            binding.myRv.adapter=myRvAdapter
        }
        if (param1=="Imtiyozli"){
            myDbHelper.getAllLabel().forEach {
                if (it.type==param1){
                    list.add(it)
                }
            }
            myRvAdapter= MyRvAdapter(list,this)
            binding.myRv.adapter=myRvAdapter
        }
        if (param1=="Taqiqlovchi"){
            myDbHelper.getAllLabel().forEach {
                if (it.type==param1){
                    list.add(it)
                }
            }
            myRvAdapter= MyRvAdapter(list,this)
            binding.myRv.adapter=myRvAdapter
        }
        if (param1=="Buyuruvchi"){
            myDbHelper.getAllLabel().forEach {
                if (it.type==param1){
                    list.add(it)
                }
            }
            myRvAdapter= MyRvAdapter(list,this)
            binding.myRv.adapter=myRvAdapter
        }
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ViewPagerItemFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
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