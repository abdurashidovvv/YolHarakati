package com.example.yolharakati.db

import com.example.yolharakati.models.Label

interface MyDbInterface {
    fun addLabel(label: Label)
    fun getAllLabel():ArrayList<Label>
    fun editLabel(label: Label)
    fun deleteLabel(label: Label)
}