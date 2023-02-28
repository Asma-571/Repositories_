package com.yelp.yelpapplication

import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast
import com.google.android.material.tabs.TabLayout.TabView

class NewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        val v = findViewById<ViewPager>(R.id.viewpager_main)
        val t = findViewById<TabView>(R.id.tabs_main)


        super.onCreate(savedInstanceState)
        setContentView(R.layout.new_activity)

        val id =intent.getStringExtra("id")

        println("id: $id")

        Toast.makeText(applicationContext, id, Toast.LENGTH_LONG).show()

        val fragmentAdapter = MyPagerAdapter(supportFragmentManager)



        v.adapter = fragmentAdapter

//        t.setupWithViewPager(v)


    }
}