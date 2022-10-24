package com.example.userinfo.fragments.list

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.userinfo.R
import com.example.userinfo.viewmodel.UserViewModel
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

        //add menu
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object: MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                //add menu item
                menuInflater.inflate(R.menu.delete_menu,menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId){
                    R.id.delete_menu->{
                        deleteAllUsers()
                    }
                    else ->{
                        false
                    }
                }
            }
        },viewLifecycleOwner, Lifecycle.State.RESUMED)

        return _binding.root
    }

    private fun deleteAllUsers():Boolean{
        val builder= AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes"){_,_
            ->mUserModelView.deleteAllUsers()
            Toast.makeText(requireContext(),"Successfull removed all users",
                Toast.LENGTH_LONG).show()
//            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        }
        builder.setNegativeButton("No"){_,_
            ->

        }
        builder.setTitle("Delete all users?")
        builder.setMessage("Are you sure you want to delete all users?")
        builder.create().show()
        return true
    }
}