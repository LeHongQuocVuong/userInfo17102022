package com.example.userinfo.fragments.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.userinfo.R
import com.example.userinfo.data.UserViewModel
import com.example.userinfo.databinding.FragmentListBinding


class ListFragment : Fragment() {

    private lateinit var _binding: FragmentListBinding
    private lateinit var mUserModelView: UserViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_add, container, false)


        _binding = FragmentListBinding.inflate(inflater,container,false)

        //recyclerView
        val adapter = UserAdapter()
        _binding.rvListUser.adapter = adapter
        _binding.rvListUser.layoutManager= LinearLayoutManager(requireContext())
        //View model
        mUserModelView = ViewModelProvider(this).get(UserViewModel::class.java)
        mUserModelView.readAllData.observe(viewLifecycleOwner, Observer{
            users->adapter.setData(users)
        })

        _binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_addFragment)
        }

        return _binding.root
    }
}