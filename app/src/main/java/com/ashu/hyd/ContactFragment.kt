package com.ashu.hyd

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ashu.hyd.Adapter.ContactsAdapter


class ContactFragment : Fragment() {

    private lateinit var contactsRecyclerView  :RecyclerView
    private lateinit var contactsLayoutManager: RecyclerView.LayoutManager
    private lateinit var contactsAdapter : ContactsAdapter
    override fun onCreateView(
        inflater: LayoutInflater , container: ViewGroup? ,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_contact , container , false)
        contactsRecyclerView = view.findViewById(R.id.contactsRV)

        contactsLayoutManager = LinearLayoutManager(context as Activity)



        return view
    }


}