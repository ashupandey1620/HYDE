package com.ashu.hyd

import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import de.hdodenhof.circleimageview.CircleImageView

class ProfileFragment : Fragment() {

    private lateinit var auth : FirebaseAuth
    private lateinit var fstore : FirebaseFirestore
    private lateinit var db : DocumentReference

    private lateinit var profilePic : CircleImageView
    private lateinit var profilePicAdd : ImageView

    private lateinit var nameVis : TextView
    private lateinit var emailVis : TextView
    private lateinit var statusVis : TextView

    private lateinit var nameEdit : TextInputLayout
    private lateinit var emailEdit : TextInputLayout
    private lateinit var statusEdit : TextInputLayout

    private lateinit var updateButton : Button
    private lateinit var saveButton : Button

    private lateinit var editName : TextInputEditText
    private lateinit var editEmail : TextInputEditText
    private lateinit var editStatus : TextInputEditText

private lateinit var progressBar : ProgressBar

private lateinit var userID: String

    override fun onCreateView(
        inflater: LayoutInflater , container: ViewGroup? ,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view  = inflater.inflate(R.layout.fragment_profile , container , false)

 auth = FirebaseAuth.getInstance()
        fstore = FirebaseFirestore.getInstance()
        userID = auth.currentUser!!.uid

        editName = view.findViewById(R.id.eProfileName)
        editEmail = view.findViewById(R.id.eProfileEmail)
        editName = view.findViewById(R.id.eProfileStatus)

        profilePic = view.findViewById(R.id.imgProfileImage)
        profilePicAdd = view.findViewById(R.id.imgAddProfileImage)

        nameEdit = view.findViewById(R.id.profile_name)
        emailEdit = view.findViewById(R.id.email_name)
        statusEdit = view.findViewById(R.id.profile_status)

        nameVis = view.findViewById(R.id.textNameProfile)
        emailVis = view.findViewById(R.id.textEmailProfile)
        statusVis = view.findViewById(R.id.textStatusProfile)

        updateButton = view.findViewById(R.id.btUpdateProfile)
        saveButton = view.findViewById(R.id.btSaveProfile)

        progressBar = view.findViewById(R.id.profileProgressBar)


        updateButton.visibility = View.VISIBLE
        updateButton.setOnClickListener{
            nameVis.visibility = View.GONE
            emailVis.visibility = View.GONE
            statusVis.visibility = View.GONE

            nameEdit.visibility = View.VISIBLE
            emailEdit.visibility = View.VISIBLE
            statusEdit.visibility = View.VISIBLE
            saveButton.visibility = View.VISIBLE

            updateButton.visibility = View.GONE



            editName.text = Editable.Factory.getInstance().newEditable(nameVis.text.toString())
            editEmail.text = Editable.Factory.getInstance().newEditable(emailVis.text.toString())
            editStatus.text = Editable.Factory.getInstance().newEditable(statusVis.text.toString())

        }

        saveButton.visibility = View.VISIBLE
        saveButton.setOnClickListener {
            nameVis.visibility = View.VISIBLE
            emailVis.visibility = View.VISIBLE
            statusVis.visibility = View.VISIBLE

            nameEdit.visibility = View.GONE
            emailEdit.visibility = View.GONE
            statusEdit.visibility = View.GONE
            saveButton.visibility = View.GONE

            updateButton.visibility = View.VISIBLE



        }


        return view
    }


}