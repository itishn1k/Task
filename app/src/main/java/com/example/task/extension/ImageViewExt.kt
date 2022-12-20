package com.example.task.extension

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.task.R

fun ImageView.loadImage(url: String){
    Glide.with(this).load(url).placeholder(R.drawable.profile).into(this)
}