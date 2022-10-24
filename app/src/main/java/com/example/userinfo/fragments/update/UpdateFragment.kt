package com.example.userinfo.fragments.update

import android.app.AlertDialog
import android.os.Bundle
import android.text.TextUtils
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.userinfo.R
import com.example.userinfo.databinding.FragmentAddBinding
import com.example.userinfo.databinding.FragmentUpdateBinding
import com.example.userinfo.model.User
import com.example.userinfo.viewmodel.UserViewModel


class UpdateFragment : Fragment() {
    private lateinit var _binding: FragmentUpdateBinding
    private lateinit var mUserModelView: UserViewModel
    private val args by navArgs<UpdateFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mUserModelView = ViewModelProvider(this).get(UserViewModel::class.java)
        _binding = FragmentUpdateBinding.inflate(inflater,container,false)
        _binding.etFirstNameUpdate.setText(args.currentUser.firstName)
        _binding.etLastNameUpdate.setText(args.currentUser.lastName)
        _binding.etAgeUpdate.setText(args.currentUser.age.toString())
        _binding.btnUpdate.setOnClickListener {
            updateUser()
        }

        //add menu
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object: MenuProvider{
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                //add menu item
                menuInflater.inflate(R.menu.delete_menu,menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId){
                    R.id.delete_menu->{
                        deleteUser()
                    }
                    else ->{
                        false
                    }
                }
            }
        },viewLifecycleOwner,Lifecycle.State.RESUMED)

        return _binding.root
    }

    private fun deleteUser():Boolean{
        val builder=AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes"){_,_
            ->mUserModelView.deleteUser(args.currentUser)
            Toast.makeText(requireContext(),"Successfull removed: ${args.currentUser.firstName}",Toast.LENGTH_LONG).show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        }
        builder.setNegativeButton("No"){_,_
        ->

        }
        builder.setTitle("Delete ${args.currentUser.firstName}?")
        builder.setMessage("Are you sure you want to delete ${args.currentUser.firstName}?")
        builder.create().show()
        return true
    }

    private fun updateUser() {
        val firstName = _binding.etFirstNameUpdate.text.toString()
        val lastName = _binding.etLastNameUpdate.text.toString()
        val age = Integer.parseInt(_binding.etAgeUpdate.text.toString())
        if(checkInput(firstName,lastName,_binding.etFirstNameUpdate.text.toString())){
            val updateUser = User(args.currentUser.id,firstName,lastName,age)
            mUserModelView.updateUser(updateUser)
            Toast.makeText(requireContext(),"Update successfully",Toast.LENGTH_LONG).show()

            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        }else{
            Toast.makeText(requireContext(),"Please fill out all fields",Toast.LENGTH_LONG).show()
        }
    }

    private fun checkInput(firstName: String, lastName: String, age: String): Boolean {
        return !(TextUtils.isEmpty(firstName)&& TextUtils.isEmpty(lastName)&& TextUtils.isEmpty(age))
    }
}