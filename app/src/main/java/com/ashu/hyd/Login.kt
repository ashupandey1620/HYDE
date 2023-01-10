package com.ashu.hyd

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth

class Login : Fragment() {
    private lateinit var enterEmail    : TextInputEditText
    private lateinit var enterPassword : TextInputEditText
    private lateinit var loginButton   : Button
    private lateinit var googleButton  : ImageButton
    private lateinit var progress      : RelativeLayout


    private lateinit var googleSignInOption  : GoogleSignInOptions
    private lateinit var mGoogleSignInClient : GoogleSignInClient
    private lateinit var resultLaunch        : ActivityResultLauncher<Intent>
    private val rcSignIn = 1011




    override fun onCreateView(
        inflater: LayoutInflater , container: ViewGroup? ,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_login , container , false)
        enterEmail    = view.findViewById(R.id.eLoginEmail)
        enterPassword = view.findViewById(R.id.eLoginPassword)
        loginButton   = view.findViewById(R.id.LoginButton)
        googleButton  = view.findViewById(R.id.googleButtonLogin)
        progress      = view.findViewById(R.id.loginProgressBar)




        loginButton.setOnClickListener{
            val email = enterEmail.text.toString()
            val password = enterPassword.text.toString()


            if(TextUtils.isEmpty(email)){
                enterEmail.error = "Email is Required to Create an Account"
            }
            else if (TextUtils.isEmpty(password))
            {
                enterPassword.error = "Password is Required"
            }
            else
            {
                if(password.length<5)
                {
                    enterPassword.error = "Password must be greater than 5 characters"
                }
                else
                {
                    progress.visibility = View.VISIBLE
                  signIn(email,password)
                }

            }

        }

        googleButton.setOnClickListener{

            createRequest()
        }

        resultLaunch = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){result->
            if(result.resultCode== Activity.RESULT_OK)
            {
                val launchData = result.data
                val task = GoogleSignIn.getSignedInAccountFromIntent(launchData)
                try {
                    val account = task.getResult(ApiException::class.java)
                    Log.d("Gmail ID","FirebaseAuthWith Google: $account")
                    firebaseAuthWithGoogle(account?.idToken)

                }
                catch (e:ApiException){
                    Log.w("Error","Google Sign in failed",e)
                }
            }
        }
        return view
    }

    private fun createRequest() {
        googleSignInOption = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("Google ID")
            .requestEmail()
            .build()
    }
//getString(R.string.default_web_client_id)

    private fun firebaseAuthWithGoogle(idToken: String?) {

    }


    private fun signIn(Eml : String,Psw : String)
    {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(Eml,Psw).addOnCompleteListener{task->
            if(task.isSuccessful){
                Toast.makeText(context,"Login Successfully",Toast.LENGTH_LONG).show()
            }
        }
    }

}
