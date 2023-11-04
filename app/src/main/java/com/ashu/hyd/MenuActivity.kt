package com.ashu.hyd

import android.app.Activity
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ashu.hyd.Adapter.ContactsAdapter
import com.ashu.hyd.Adapter.SearchAdapter
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration


class MenuActivity : AppCompatActivity() ,
    androidx.appcompat.widget.SearchView.OnQueryTextListener {
    private var register : ListenerRegistration? = null

    private lateinit var queryTerm: String
    private var searchInfo = arrayListOf<User>()
    private lateinit var searchRecyclerView: RecyclerView
    private lateinit var searchLayoutManager: RecyclerView.LayoutManager
    private lateinit var searchAdapter: SearchAdapter

    private lateinit var toolbarMenu: androidx.appcompat.widget.Toolbar
    private lateinit var frameLayout: FrameLayout
    private lateinit var optionValue: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        toolbarMenu = findViewById(R.id.toolbarMenu)
        frameLayout = findViewById(R.id.frameLayout)

        if (intent != null) {
            optionValue = intent.getStringExtra("option").toString()
            when (optionValue) {
                "profile" -> {

                    frameLayout.visibility = View.VISIBLE

                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frameLayout , ProfileFragment()).commit()
                    toolbarMenu.title = "Profile"
                }

                "about" -> {

                    frameLayout.visibility = View.VISIBLE

                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frameLayout , AboutFragment()).commit()
                    toolbarMenu.title = "About"
                }

                "contact" -> {

                   searchRecyclerView  = findViewById(R.id.rvSearch)
                    searchLayoutManager = LinearLayoutManager(this)

                    searchRecyclerView.visibility = View.VISIBLE

                    toolbarMenu.title = "Contacts "
                    setSupportActionBar(toolbarMenu)
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search , menu)
        val searchView =
            menu?.findItem(R.id.search)?.actionView as androidx.appcompat.widget.SearchView
        searchView.isSubmitButtonEnabled = true
        searchView.setOnQueryTextListener(this)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null) {
            queryTerm = query
            if (queryTerm.isNotEmpty()) {
                searchUsers()
            }
        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if(newText!=null)
        {
            queryTerm = newText
            if(queryTerm.isNotEmpty())
            {
                searchUsers()
            }

        }
        return true
    }

    private fun searchUsers() {
      register =  FirebaseFirestore.getInstance()
            .collection("users")
            .orderBy("userName")
            .startAt(queryTerm).limit(5)
            .addSnapshotListener { snapshot , exception ->
                if (exception != null) {
                    Log.e("OnError" , "Error Occured")
                } else {
                    if (!snapshot?.isEmpty!!) {
                        searchInfo
                        val searchList = snapshot.documents

                        for (i in searchList) {
                            val contact = User(i.id,
                                i.getString("userName").toString() ,
                                i.getString("userStatus").toString() ,
                                i.getString("userEmail").toString() ,
                                i.getString("userProfilePhoto").toString()
                            )
                            searchInfo.add(contact)
                            searchAdapter = SearchAdapter(this , searchInfo)
                            searchRecyclerView.adapter = searchAdapter
                            searchRecyclerView.layoutManager = searchLayoutManager
                            searchRecyclerView.addItemDecoration(
                                DividerItemDecoration(
                                    searchRecyclerView.context ,
                                    (searchLayoutManager as LinearLayoutManager).orientation
                                )
                            )
                        }
                    }
                }
            }
    }

    override  fun onDestroy() {
        register?.remove()
        super.onDestroy()
    }
}
