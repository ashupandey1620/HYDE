package com.ashu.hyd

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.util.Calendar


class MessagingFragment : Fragment() {

    private lateinit var msgRecyclerView: RecyclerView
    private lateinit var  sendMsgEditText: EditText
    private  lateinit var sendMessageButton :FloatingActionButton
    private lateinit var fauth : FirebaseAuth
    private lateinit var fstore : FirebaseFirestore

    private lateinit var messageLayoutManager : RecyclerView.LayoutManager
    private lateinit var messageAdapter : MessageAdapter

    override fun onCreateView(
        inflater: LayoutInflater , container: ViewGroup? ,
        savedInstanceState: Bundle?
    ): View? {

        val view =  inflater.inflate(R.layout.fragment_messaging , container , false)

        fauth = FirebaseAuth.getInstance()
        fstore = FirebaseFirestore.getInstance()




        msgRecyclerView  = view.findViewById(R.id.messageRV)
        sendMessageButton = view.findViewById(R.id.btnSendMessage)
        sendMsgEditText = view.findViewById(R.id.editSendMessage)

        sendMessageButton.setOnClickListener {
            sendMessage()
        }


        return view
    }

    private fun sendMessage() {
        val message = sendMsgEditText.text.toString()
        if(TextUtils.isEmpty(message))
        {
            sendMsgEditText.error = "Enter Some Message to Send"
        }
        else
        {
            val c = Calendar.getInstance()
            val hour = c.get(Calendar.HOUR_OF_DAY)
            val minute =  c.get(Calendar.MINUTE)
            val timeStamp = "$hour:$minute"

            val messageObject = mutableMapOf<String,String>().also {
                it["message"] = message
                it["messageSender"] = FirebaseAuth.getInstance().currentUser?.uid.toString()
                it["messageReceiver"] = ""
                it["messageTime"] = timeStamp
            }
        }
    }


}