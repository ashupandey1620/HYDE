package com.ashu.hyd

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.Toolbar

class MenuActivity : AppCompatActivity() {
    private lateinit var toolbarMenu: Toolbar
    private lateinit var frameLayout: FrameLayout
    private lateinit var optionValue: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        toolbarMenu = findViewById(R.id.toolbarMenu)
        frameLayout = findViewById(R.id.frameLayout)

        if(intent!=null)
        {
            optionValue = intent.getStringExtra("option").toString()
            when(optionValue)
            {
                "profile"->{
                    supportFragmentManager.beginTransaction().replace(R.id.frameLayout,ProfileFragment()).commit()
                    toolbarMenu.title = "Profile"
                }
                "about"->{
                    supportFragmentManager.beginTransaction().replace(R.id.frameLayout,AboutFragment()).commit()
                    toolbarMenu.title = "About"
                }
            }
        }
    }
}