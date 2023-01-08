package com.example.task.ui.task

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.task.App
import com.example.task.R
import com.example.task.databinding.FragmentTaskBinding
import com.example.task.utils.showToast
import com.example.task.model.Task
import com.example.task.ui.home.HomeFragment
import com.example.task.utils.isNetworkConnected

class TaskFragment : Fragment() {

    private lateinit var binding: FragmentTaskBinding
    private var task: Task? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        @Suppress("DEPRECATION")
        task = arguments?.getSerializable(HomeFragment.KEY_FOR_TASK) as Task?
        if (task == null) {
            binding.btnSave.text = getString(R.string.save)
        } else {
            binding.etTitle.setText(task?.title.toString())
            binding.etDesc.setText(task?.desc.toString())
            binding.btnSave.text = getString(R.string.update)
        }
        binding.btnSave.setOnClickListener {
            when (task) {
                null -> {
                    save()
                    saveDataToFb()
                }
                else -> {
                    update()
                }
            }
            findNavController().navigateUp()
        }
    }

    private fun saveDataToFb() {
        val data = Task(
            title = binding.etTitle.text.toString(),
            desc = binding.etDesc.text.toString()
        )
        App.firebaseDB?.collection("tasks")?.add(data)
            ?.addOnSuccessListener {
                Log.e("ololo", "aaa")
            }
            ?.addOnFailureListener {
                showToast(it.message.toString())
            }
    }

    private fun update() {
        task?.title = binding.etTitle.text.toString()
        task?.desc = binding.etDesc.text.toString()
        task?.let { App.db.dao().update(it) }
    }

    private fun save() {
        val data = Task(
            title = binding.etTitle.text.toString(),
            desc = binding.etDesc.text.toString()
        )
        App.db.dao().insert(data)
    }
}