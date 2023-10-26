package com.ashu.hyd

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


class MessagingFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater , container: ViewGroup? ,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_messaging , container , false)
    }


}