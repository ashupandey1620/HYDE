package com.ashu.hyd

import android.annotation.SuppressLint
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Email
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore


class SignUp : Fragment() {

    private lateinit var progressBar : ProgressBar
    private lateinit var enterEmail: TextInputEditText
    private lateinit var enterPassword : TextInputEditText
    private lateinit var confirmPassword :TextInputEditText
    private lateinit var signUpButton : Button



    private lateinit var fAuth : FirebaseAuth
    private lateinit var fStore : FirebaseFirestore
    private lateinit var dB : DocumentReference


    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater , container: ViewGroup? ,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_sign_up , container , false)
        enterEmail = view.findViewById(R.id.eSignUpEmail)
        enterPassword = view.findViewById(R.id.eSignUpPassword)
        confirmPassword = view.findViewById(R.id.eSignUpConfirmPassword)
        progressBar = view.findViewById(R.id.signUpProgressBar)


        signUpButton = view.findViewById(R.id.SignUpButton)

        signUpButton.setOnClickListener(){
            val email = enterEmail.text.toString()
            val password = enterPassword.text.toString()
            val confirmPasswordVar = confirmPassword.text.toString()

            if(TextUtils.isEmpty(email)){
                enterEmail.error = "Email is Required to Create an Account"
            }
            else if (TextUtils.isEmpty(password))
            {
                enterPassword.error = "Password is Empty"
            }
            else
            {
                if(password.length<5)
                {
                    enterPassword.error = "Password must be greater than 5 characters"
                }
                else
                {
                    if (password != confirmPasswordVar)
                    {
                        confirmPassword.error = "Passwords Not Matched"
                    }
                    else
                    {
                        progressBar.visibility = View.VISIBLE
                        createAccount(email,password)
                    }
                }
            }

        }
        fAuth = FirebaseAuth.getInstance()
        fStore = FirebaseFirestore.getInstance()


        return view
    }

    private fun createAccount(Em : String ,Ps :String){

        fAuth.createUserWithEmailAndPassword(Em,Ps).addOnCompleteListener{task->
            if(task.isSuccessful)
            {
                val userInfo = fAuth.currentUser?.uid
                dB = fStore.collection("users").document(userInfo.toString())
                val obj = mutableMapOf<String,String>()
                obj["userEmail"] = Em
                obj["userPassword"] = Ps
                obj["userName"] = ""
                obj["userStatus"] = ""

                dB.set(obj).addOnSuccessListener {
                    Log.d("onSuccess" , "User Created Successfully")
                    progressBar.visibility = View.GONE
                }


            }

        }

    }




}
