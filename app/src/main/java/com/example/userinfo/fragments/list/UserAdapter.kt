package com.example.userinfo.fragments.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.userinfo.R
import com.example.userinfo.model.User

class UserAdapter:RecyclerView.Adapter<UserAdapter.MyViewHolder>() {
    private var userList = emptyList<User>()

    class MyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.user_row,parent,false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var currentUser = userList[position]
        holder.itemView.findViewById<TextView>(R.id.tv_id).text = currentUser.id.toString()
        holder.itemView.findViewById<TextView>(R.id.tv_firstName).text = currentUser.firstName.toString()
        holder.itemView.findViewById<TextView>(R.id.tv_lastName).text = currentUser.lastName.toString()
        holder.itemView.findViewById<TextView>(R.id.tv_age).text = currentUser.age.toString()

        //Update
        holder.itemView.findViewById<ConstraintLayout>(R.id.row_layout).setOnClickListener {
            val action = ListFragmentDirections.actionListFragmentToUpdateFragment(currentUser)
            holder.itemView.findNavController().navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    fun setData(users:List<User>){
        this.userList = users
        notifyDataSetChanged()
    }
}