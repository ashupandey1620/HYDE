package com.ashu.hyd.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.RecyclerView
import com.ashu.hyd.R
import com.ashu.hyd.User
import com.squareup.picasso.Picasso

class SearchAdapter(val context: Context, private val searchList : ArrayList<User>):RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {
    class SearchViewHolder(view: View):RecyclerView.ViewHolder(view) {
        val contactListName : TextView = view.findViewById(R.id.txtName)
        val contactListStatus : TextView = view.findViewById(R.id.txtStatus)

        val image : ImageView = view.findViewById(R.id.imgProfile)

    }

    override fun onCreateViewHolder(parent: ViewGroup , viewType: Int): SearchViewHolder {
        val searchView = LayoutInflater.from(parent.context).inflate(R.layout.rv_contacts,parent,false)
        return SearchAdapter.SearchViewHolder(searchView)
    }

    override fun getItemCount(): Int {
     return searchList.size
    }

    override fun onBindViewHolder(holder: SearchViewHolder , position: Int) {
        val list = searchList[position]
        holder.contactListName.text  = list.profileName
        holder.contactListStatus.text = list.profileStatus
        Picasso.get().load(list.profilePicture).into(holder.image)
    }
}