package com.example.yolharakati.fragments

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.yolharakati.R
import com.example.yolharakati.databinding.RvItemBinding
import com.example.yolharakati.models.Label

class MyRvAdapter(val list:ArrayList<Label>, val rvClick: RvClick): RecyclerView.Adapter<MyRvAdapter.Vh>() {

    inner class Vh(val rvItem: RvItemBinding): RecyclerView.ViewHolder(rvItem.root){
        fun onBind(label: Label){
            rvItem.rvItemTv.text=label.name
            rvItem.rvImage.setImageURI(Uri.parse(label.img))

            list.forEach {
                if (it.like=="1"){
                    rvItem.like.setImageResource(R.drawable.selected_heart)
                }else if(it.like=="0"){
                    rvItem.like.setImageResource(R.drawable.unselected_heart)
                }
            }
            rvItem.delete.setOnClickListener {
                rvClick.deleteClick(label)
            }
            rvItem.edit.setOnClickListener {
                rvClick.editClick(label)
            }
            rvItem.itemMain.setOnClickListener {
                rvClick.itemClick(label)
            }
            rvItem.like.setOnClickListener {
                rvClick.likeClick(label)
                if (label.like=="1"){
                    rvItem.like.setImageResource(R.drawable.selected_heart)
                }else{
                    rvItem.like.setImageResource(R.drawable.unselected_heart)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(RvItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface RvClick{
        fun editClick(label: Label)
        fun deleteClick(label: Label)
        fun itemClick(label: Label)
        fun likeClick(label: Label)
    }
}