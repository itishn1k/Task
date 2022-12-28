package com.example.task.ui.onBoarding.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.task.R
import com.example.task.databinding.ItemBoardingBinding
import com.example.task.extension.loadImage
import com.example.task.model.OnBoard

class OnBoardingAdapter(private val context: Context, private val onClick: () -> Unit) :
    RecyclerView.Adapter<OnBoardingAdapter.OnBoardingViewHolder>() {

    private var data = arrayListOf(
        OnBoard(
            R.raw.tasks,
            "Productivity"
        ),
        OnBoard(
            R.raw.payment,
            "Made by those who use"
        ),
        OnBoard(
            R.raw.devices,
            "Sync with other devices"
        )
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnBoardingViewHolder {
        return OnBoardingViewHolder(
            ItemBoardingBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: OnBoardingViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount() = data.size


    inner class OnBoardingViewHolder(private val binding: ItemBoardingBinding) :
        ViewHolder(binding.root) {
        fun bind(onBoard: OnBoard) {
            binding.ivBoarding.setAnimation(onBoard.image!!)
            binding.tvTitle.text = onBoard.title

            if (adapterPosition == data.lastIndex) binding.tvSkip.text =
                context.getString(R.string.next) else context.getString(R.string.skip)
            binding.tvSkip.setOnClickListener {
                onClick()
            }
        }
    }
}