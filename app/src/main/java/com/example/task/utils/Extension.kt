package com.example.task.utils

import android.content.Context
import android.net.ConnectivityManager
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.task.R

fun ImageView.loadImage(url: String){
    Glide.with(this).load(url).placeholder(R.drawable.profile).into(this)
}

fun Fragment.showToast(msg: String) {
    Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
}

fun Context.isNetworkConnected(): Boolean {
    val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
    return cm?.activeNetworkInfo != null && cm.activeNetworkInfo?.isConnected == true
}