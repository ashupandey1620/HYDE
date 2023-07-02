package com.ashu.hyd

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.CallLog.Calls
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var contactsButton  : FloatingActionButton
    private lateinit var auth            : FirebaseAuth
    private lateinit var viewPager2      : ViewPager2
    private lateinit var tabLayout       : TabLayout
    private lateinit var toolbar         : Toolbar
    private lateinit var appPagerAdapter : AppPagerAdapter
    private val titles = arrayListOf("Chats","Status")
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        contactsButton = findViewById(R.id.ButtonFloatingContacts)
        toolbar       =  findViewById(R.id.toolbarMain)
        auth          =  FirebaseAuth.getInstance()
        viewPager2    =  findViewById(R.id.viewPager2Main)
        tabLayout     =  findViewById(R.id.tabLayout)
        toolbar.title =  "HYDE"
        setSupportActionBar(toolbar)
        appPagerAdapter    = AppPagerAdapter(this)
        viewPager2.adapter = appPagerAdapter
        TabLayoutMediator(tabLayout,viewPager2){
            tab,position->
            tab.text = titles[position]
        }.attach()
   //     contactsButton.setOnClickListener()

    }
    class AppPagerAdapter(fragmentActivity: FragmentActivity):FragmentStateAdapter(fragmentActivity){

        override fun getItemCount(): Int {
            return 2;
        }

        override fun createFragment(position: Int): Fragment {
            return when(position)
            {
                0->Chats()
                2->Status()
                else->Chats()
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId)
        {
            R.id.profile ->{
                val intent = Intent(this,MenuActivity::class.java)
                intent.putExtra("option","profile")
                startActivity(intent)

            }
            R.id.LogOut -> {
                auth.signOut()
                val intent = Intent(this,AuthenticationActivity::class.java)
                startActivity(intent)
                finish()
            }

            R.id.AboutUs ->{
                val intent = Intent(this,MenuActivity::class.java)
                intent.putExtra("option","about")
                startActivity(intent)

        }



        }
        return super.onOptionsItemSelected(item)
    }

}