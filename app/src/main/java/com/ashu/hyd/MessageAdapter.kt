package com.ashu.hyd

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MessageAdapter(val context: Context , val messageList: ArrayList<MessageModal>):
RecyclerView.Adapter<MessageAdapter.MessageViewHolder>(){
    override fun onCreateViewHolder(
        parent: ViewGroup ,
        viewType: Int
    ): MessageAdapter.MessageViewHolder {

    }

    override fun onBindViewHolder(holder: MessageAdapter.MessageViewHolder , position: Int) {

    }

    override fun getItemCount(): Int {

    }

    class MessageViewHolder(view: View):RecyclerView.ViewHolder(view){
        val message : TextView = view.findViewById(R.id.txtMessage)
    }

}
