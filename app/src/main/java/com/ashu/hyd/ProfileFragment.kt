package com.ashu.hyd

import android.graphics.Bitmap
import android.os.Bundle
import android.text.Editable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import java.io.ByteArrayOutputStream

class ProfileFragment : Fragment() {

    private lateinit var auth : FirebaseAuth
    private lateinit var fstore : FirebaseFirestore
    private lateinit var database : DocumentReference

    private lateinit var profilePic : CircleImageView
    private lateinit var profilePicAdd : ImageView

    private lateinit var nameVisible : TextView
    private lateinit var emailVisible : TextView
    private lateinit var statusVisible : TextView

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

private lateinit var image : ByteArray
private lateinit var storageReference : StorageReference

    private val register  = registerForActivityResult(ActivityResultContracts.TakePicturePreview()){
        upload(it)
    }
    override fun onCreateView(
        inflater: LayoutInflater , container: ViewGroup? ,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view  = inflater.inflate(R.layout.fragment_profile , container , false)



        auth = FirebaseAuth.getInstance()
        fstore = FirebaseFirestore.getInstance()
        userID = auth.currentUser!!.uid

        storageReference = FirebaseStorage.getInstance().reference.child("${userID}/profilePhoto")
        editName = view.findViewById(R.id.eProfileName)
        editEmail = view.findViewById(R.id.eProfileEmail)
        editStatus = view.findViewById(R.id.eProfileStatus)

        profilePic = view.findViewById(R.id.imgProfileImage)
        profilePicAdd = view.findViewById(R.id.imgAddProfileImage)

        nameEdit = view.findViewById(R.id.profile_name)
        emailEdit = view.findViewById(R.id.email_name)
        statusEdit = view.findViewById(R.id.profile_status)

        nameVisible = view.findViewById(R.id.textNameProfile)
        emailVisible = view.findViewById(R.id.textEmailProfile)
        statusVisible = view.findViewById(R.id.textStatusProfile)

        updateButton = view.findViewById(R.id.btUpdateProfile)
        saveButton = view.findViewById(R.id.btSaveProfile)

        progressBar = view.findViewById(R.id.profileProgressBar)


        database = fstore.collection("users").document(userID)
        database.addSnapshotListener { value, error ->
            if(error!=null)
            {
                Log.d("Error","Unable to fetch the Data")
            }
            else
            {
                nameVisible.text = value?.getString("userName")
                emailVisible.text = value?.getString("userEmail")
                statusVisible.text = value?.getString("userStatus")
                Picasso.get().load(value?.getString("userProfilePhoto")).error(R.drawable.user3).into(profilePic)
            }
        }


        updateButton.visibility = View.VISIBLE
        updateButton.setOnClickListener{

            nameVisible.visibility = View.GONE
            emailVisible.visibility = View.GONE
            statusVisible.visibility = View.GONE

            nameEdit.visibility = View.VISIBLE
            emailEdit.visibility = View.VISIBLE
            statusEdit.visibility = View.VISIBLE

            saveButton.visibility = View.VISIBLE

            updateButton.visibility = View.GONE

            editName.text = Editable.Factory.getInstance().newEditable(nameVisible.text.toString())
            editEmail.text = Editable.Factory.getInstance().newEditable(emailVisible.text.toString())
            editStatus.text = Editable.Factory.getInstance().newEditable(statusVisible.text.toString())

        }

        saveButton.visibility = View.GONE
        saveButton.setOnClickListener {
            nameVisible.visibility = View.VISIBLE
            emailVisible.visibility = View.VISIBLE
            statusVisible.visibility = View.VISIBLE

            nameEdit.visibility = View.GONE
            emailEdit.visibility = View.GONE
            statusEdit.visibility = View.GONE
            saveButton.visibility = View.GONE

            updateButton.visibility = View.VISIBLE

            val object1 = mutableMapOf<String,String>()
            object1["userName"] = editName.text.toString()
            object1["userEmail"] = editEmail.text.toString()
            object1["userStatus"] = editStatus.text.toString()
            database.set(object1).addOnSuccessListener {
                Log.d("Success","Data Updated")
            }


        }

        profilePicAdd.setOnClickListener{
            takephoto()
        }


        return view
    }

    private fun takephoto(){


        register.launch(null)
    }

    private fun upload(it: Bitmap?)
    {
        val baos = ByteArrayOutputStream()
        it?.compress(Bitmap.CompressFormat.JPEG,50,baos)
        image  = baos.toByteArray()
        storageReference.putBytes(image).addOnSuccessListener {
            storageReference.downloadUrl.addOnSuccessListener {
                val obj = mutableMapOf<String,String>()
                obj["userProfilePhoto"] = it.toString()
                database.update(obj as Map<String,Any>).addOnSuccessListener {
                    Log.d("On Success","Profile Picture Uploaded")
                }
            }
        }
    }


}