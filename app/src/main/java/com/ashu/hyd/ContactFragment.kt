package com.ashu.hyd

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ashu.hyd.Adapter.ContactsAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class ContactFragment : Fragment() {

    private lateinit var contactsRecyclerView  :RecyclerView
    private lateinit var contactsLayoutManager: RecyclerView.LayoutManager
    private lateinit var contactsAdapter : ContactsAdapter

    private lateinit var fstore : FirebaseFirestore
    private lateinit var auth   : FirebaseAuth

    private val contactInfo = arrayListOf<User>()
    override fun onCreateView(
        inflater: LayoutInflater , container: ViewGroup? ,
        savedInstanceState: Bundle?
    ): View? {
//         Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_contact , container , false)
        contactsRecyclerView = view.findViewById(R.id.contactsRV)
        contactsLayoutManager = LinearLayoutManager(context as Activity)

        auth = FirebaseAuth.getInstance()
        fstore = FirebaseFirestore.getInstance()

        fstore.collection("users").get().addOnSuccessListener {
            if(!it.isEmpty)
            {
                val listOfContact = it.documents
                for(i in listOfContact){
                    if(i.id== auth.currentUser?.uid){
                        Log.d("contact list loading","user account not added")
                    }
                    else
                    {
                        val contact = User(i.id,i.getString("userName").toString(),
                            i.getString("userStatus").toString(),
                            i.getString("userEmail").toString(),
                            i.getString("userProfilePhoto").toString())
                        contactInfo.add(contact)
                        contactsAdapter = ContactsAdapter(context as Activity,contactInfo)
                        contactsRecyclerView.adapter = contactsAdapter
                        contactsRecyclerView.layoutManager = contactsLayoutManager
                        contactsRecyclerView.addItemDecoration(DividerItemDecoration(contactsRecyclerView.context,(contactsLayoutManager as LinearLayoutManager).orientation))
                    }

                }
            }
        }
        contactsRecyclerView.addItemDecoration(DividerItemDecoration(contactsRecyclerView.context,(contactsLayoutManager as LinearLayoutManager).orientation))

        return view
    }


}