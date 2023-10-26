package com.ashu.hyd.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ashu.hyd.R
import com.ashu.hyd.User


class ContactsAdapter(val context: Context , val contactList :ArrayList<User>)
    : RecyclerView.Adapter<ContactsAdapter.ContactsViewHolder>() {
    class ContactsViewHolder(view: View):RecyclerView.ViewHolder(view) {

        val contactListName : TextView = view.findViewById(R.id.txtName)
        val contactListStatus : TextView = view.findViewById(R.id.txtStatus)

        val image : ImageView = view.findViewById(R.id.imgProfile)

    }

    override fun onCreateViewHolder(parent: ViewGroup , viewType: Int): ContactsViewHolder {
         val contactView = LayoutInflater.from(parent.context).inflate(R.layout.rv_contacts,parent,false)
        return ContactsViewHolder(contactView)
    }

    override fun getItemCount(): Int {
        return contactList.size
    }

    override fun onBindViewHolder(holder: ContactsViewHolder , position: Int) {
    val list = contactList[position]
        holder.contactListName.text  = list.profileName

    }
}