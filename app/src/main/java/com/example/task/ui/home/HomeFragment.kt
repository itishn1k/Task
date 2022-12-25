package com.example.task.ui.home

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.task.App
import com.example.task.R
import com.example.task.databinding.FragmentHomeBinding
import com.example.task.model.Task
import com.example.task.ui.home.adapter.TaskAdapter


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private lateinit var adapter: TaskAdapter
    private lateinit var data: List<Task>


    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = TaskAdapter(this::onClick)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvTasks.adapter = adapter
        data = App.db.dao().getAll()
        adapter.addTasks(data)
        //
        clickListener()
    }

    private fun clickListener() {
        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.navigation_task)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onClick(position: Int) {
        val builder = AlertDialog.Builder(requireContext())

        builder.setTitle("Delete")
        builder.setMessage("Are you sure you want to delete this note?")

        builder.setPositiveButton("Yes") { _: DialogInterface, _: Int ->
            App.db.dao().delete(data[position])
            findNavController().run {
                popBackStack()
                navigate(R.id.navigation_home)
            }
        }
        builder.setNegativeButton("No") { _: DialogInterface, _: Int ->
        }
        builder.show()
    }
}