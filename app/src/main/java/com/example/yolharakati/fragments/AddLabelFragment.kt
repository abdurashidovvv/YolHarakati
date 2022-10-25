package com.example.yolharakati.fragments

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import com.example.yolharakati.R
import com.example.yolharakati.adapters.SpinnerAdapter
import com.example.yolharakati.databinding.FragmentAddLabelBinding
import com.example.yolharakati.db.MyDbHelper
import com.example.yolharakati.models.Label
import com.example.yolharakati.models.MyObject
import com.example.yolharakati.models.PagerItem
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class AddLabelFragment : Fragment() {

    private lateinit var list:ArrayList<String>
    private lateinit var spinnerAdapter: SpinnerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        list= ArrayList()
        list.add(PagerItem("Ogohlantiruvchi").type.toString())
        list.add(PagerItem("Imtiyozli").type.toString())
        list.add(PagerItem("Taqiqlovchi").type.toString())
        list.add(PagerItem("Buyuruvchi").type.toString())
    }

    private lateinit var binding: FragmentAddLabelBinding
    private lateinit var uriPath:String
    private lateinit var myDbHelper: MyDbHelper
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding= FragmentAddLabelBinding.inflate(layoutInflater)

        uriPath=""
//        val label=arguments?.getSerializable("key") as Label
        myDbHelper= MyDbHelper(binding.root.context)

        when(MyObject.edit){
            true->{
                binding.name.setText(MyObject.label!!.name)
                binding.info.setText(MyObject.label!!.info)
                binding.addImg.setImageURI(Uri.parse(MyObject.label!!.img))
                binding.addImg.setOnClickListener {
                    getImageContent.launch("image/*")
                }
                spinnerAdapter= SpinnerAdapter(list)
                binding.spinner.adapter=spinnerAdapter

                binding.btnSave.setOnClickListener {
                    if (uriPath==""){
                        uriPath=MyObject.label!!.img.toString()
                    }
//                    val label=Label(
//                        name = binding.name.text.toString(),
//                        info = binding.info.text.toString(),
//                        img = uriPath,
//                        like = "0",
//                        type = list[binding.spinner.selectedItemPosition]
//                    )
                    MyObject.label!!.name=binding.name.text.toString()
                    MyObject.label!!.info=binding.info.text.toString()
                    MyObject.label!!.img=uriPath
                    MyObject.label!!.type=list[binding.spinner.selectedItemPosition]
                    myDbHelper.editLabel(MyObject.label!!)
                    findNavController().popBackStack()
                }
            }
            false->{
                binding.addImg.setOnClickListener {
                    getImageContent.launch("image/*")
                }
                spinnerAdapter= SpinnerAdapter(list)
                binding.spinner.adapter=spinnerAdapter

                binding.btnSave.setOnClickListener {
                    val label=Label(
                        name = binding.name.text.toString(),
                        info = binding.info.text.toString(),
                        img = uriPath,
                        like = "0",
                        type = list[binding.spinner.selectedItemPosition]
                    )
                    myDbHelper.addLabel(label)
                    findNavController().popBackStack()
                }
            }
        }


        return binding.root
    }
    @SuppressLint("SimpleDateFormat")
    private val getImageContent =
        registerForActivityResult(ActivityResultContracts.GetContent()) {
            it ?: return@registerForActivityResult
            binding.addImg.setImageURI(it)
            val inputStream = requireActivity().contentResolver.openInputStream(it)
            val title = SimpleDateFormat("yyyyMMdd_hhmmss").format(Date())
            val file = File(requireActivity().filesDir, "$title.jpg")
            val fileOutputStream = FileOutputStream(file)
            inputStream?.copyTo(fileOutputStream)
            inputStream?.close()
            fileOutputStream.close()
            uriPath = file.absolutePath
        }
}