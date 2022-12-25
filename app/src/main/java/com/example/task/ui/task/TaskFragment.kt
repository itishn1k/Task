package com.example.task.ui.task

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.task.App
import com.example.task.databinding.FragmentTaskBinding
import com.example.task.model.Task

class TaskFragment : Fragment() {

    private lateinit var binding: FragmentTaskBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnSave.setOnClickListener {
            save()
        }
    }

    private fun save() {
        val data = Task(
            title = binding.etTitle.text.toString(),
            desc = binding.etDesc.text.toString()
        )
        App.db.dao().insert(data)
        App.db.dao().delete(data)
        findNavController().navigateUp()
    }
}