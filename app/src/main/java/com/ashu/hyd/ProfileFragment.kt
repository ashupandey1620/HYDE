package com.ashu.hyd

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.google.android.material.textfield.TextInputEditText
import de.hdodenhof.circleimageview.CircleImageView

class ProfileFragment : Fragment() {

    private lateinit var profilePic : CircleImageView
    private lateinit var profilePicAdd : ImageView

    private lateinit var nameVis : TextView
    private lateinit var emailVis : TextView
    private lateinit var statusVis : TextView

    private lateinit var nameEdit : TextInputEditText
    private lateinit var emailEdit : TextInputEditText
    private lateinit var statusEdit : TextInputEditText

    private lateinit var updateButton : Button
    private lateinit var saveButton : Button


    override fun onCreateView(
        inflater: LayoutInflater , container: ViewGroup? ,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view  = inflater.inflate(R.layout.fragment_profile , container , false)

        profilePic = view.findViewById(R.id.imgProfileImage)
        profilePicAdd = view.findViewById(R.id.imgAddProfileImage)

        nameEdit = view.findViewById(R.id.eProfileName)
        emailEdit = view.findViewById(R.id.eInputEmail)
        statusEdit = view.findViewById(R.id.eProfileStatus)

        nameVis = view.findViewById(R.id.textNameProfile)
        emailVis = view.findViewById(R.id.textEmailProfile)
        statusVis = view.findViewById(R.id.textStatusProfile)

        updateButton = view.findViewById(R.id.btUpdateProfile)
        saveButton = view.findViewById(R.id.btSaveProfile)

        return view
    }


}