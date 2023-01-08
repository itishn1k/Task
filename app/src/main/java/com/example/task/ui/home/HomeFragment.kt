package com.example.task.ui.home

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.task.App
import com.example.task.R
import com.example.task.databinding.FragmentHomeBinding
import com.example.task.utils.isNetworkConnected
import com.example.task.model.Task
import com.example.task.ui.home.adapter.TaskAdapter
import com.example.task.utils.showToast


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private lateinit var adapter: TaskAdapter
    private lateinit var data: List<Task>
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = TaskAdapter(this::onLongClick, this::onClick)
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
        setData()
        clickListener()
    }

    private fun clickListener() {
        binding.fab.setOnClickListener {
            if (requireContext().isNetworkConnected()) {//homework7
                showToast("Adding unavailable now")
            } else {
                findNavController().navigate(R.id.navigation_task)
            }
        }
    }

    private fun setData() {
        if (requireContext().isNetworkConnected()) {
            /*Get data from firebase*/
            getData()
        } else {
            data = App.db.dao().getAll()
            adapter.addTasks(data)
        }
    }

    private fun getData() {
        App.firebaseDB?.collection("tasks")?.get()?.addOnCompleteListener {
            if (it.isSuccessful) {
                val data = arrayListOf<Task>()
                for (i in it.result) {
                    val task = i.toObject(Task::class.java)
                    data.add(task)
                }
                adapter.addTasks(data)
            }
        }?.addOnFailureListener {
            Log.e("ololo", "get Data${it.message}")
        }
    }

//    private fun deleteData(task: Task) {
//        App.firebaseDB?.collection("tasks")?.document(data.)?.delete()
//            ?.addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully deleted!") }
//            ?.addOnFailureListener { Log.e(TAG, "Error deleting document") }
//    }


    private fun onLongClick(task: Task) {
        val builder = AlertDialog.Builder(requireContext())
        if (requireContext().isNetworkConnected()) { //homework7
            builder.setTitle("Sorry")
            builder.setMessage("Deleting unavailable now")
            builder.show()
        } else {
            builder.setTitle("Delete")
            builder.setMessage("Are you sure you want to delete this note?")


            builder.setPositiveButton("Yes") { _: DialogInterface, _: Int ->
                App.db.dao().delete(task)
                setData()
            }

            builder.setNegativeButton("No") { _: DialogInterface, _: Int ->
            }
            builder.show()
        }
    }

    private fun onClick(task: Task) {
        if (requireContext().isNetworkConnected()) {//homework7
            showToast("Editing unavailable now")
        } else {
            findNavController().navigate(R.id.navigation_task, bundleOf(KEY_FOR_TASK to task))
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val KEY_FOR_TASK = "task"
    }
}