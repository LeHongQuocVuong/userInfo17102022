package com.example.userinfo.fragments.add

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.userinfo.R
import com.example.userinfo.data.User
import com.example.userinfo.data.UserViewModel
import com.example.userinfo.databinding.FragmentAddBinding


class AddFragment : Fragment() {
    private lateinit var _binding:FragmentAddBinding
    private lateinit var mUserModelView: UserViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_add, container, false)
        mUserModelView = ViewModelProvider(this).get(UserViewModel::class.java)

        _binding = FragmentAddBinding.inflate(inflater,container,false)
        _binding.btnAdd.setOnClickListener {
            insertDataToDatabase()
        }
        return _binding.root
    }

    private fun insertDataToDatabase() {
        val firstName = _binding.etFirstName.text.toString()
        val lastName = _binding.etLastName.text.toString()
        val age = _binding.etAge.text.toString()

        if(checkInput(firstName,lastName,age)){
            val user = User(0,firstName,lastName,Integer.parseInt(age))
            mUserModelView.addUser(user)
            Toast.makeText(requireContext(),"Successfully added",Toast.LENGTH_LONG).show()

            findNavController().navigate(R.id.action_addFragment_to_listFragment)
        }else{
            Toast.makeText(requireContext(),"Please fill out all fields",Toast.LENGTH_LONG).show()
        }
    }

    private fun checkInput(firstName: String, lastName: String, age: String): Boolean {
        return !(TextUtils.isEmpty(firstName)&&TextUtils.isEmpty(lastName)&&TextUtils.isEmpty(age))
    }
}