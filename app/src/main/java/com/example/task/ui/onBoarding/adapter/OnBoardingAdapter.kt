package com.example.task.ui.onBoarding.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.task.databinding.ItemBoardingBinding
import com.example.task.model.OnBoard

class OnBoardingAdapter : RecyclerView.Adapter<OnBoardingAdapter.OnBoardingViewHolder>() {

    private var data= arrayListOf<OnBoard>(
        OnBoard()
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnBoardingViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: OnBoardingViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }


    inner class OnBoardingViewHolder(private val binding: ItemBoardingBinding) :
        ViewHolder(binding.root) {

    }
}