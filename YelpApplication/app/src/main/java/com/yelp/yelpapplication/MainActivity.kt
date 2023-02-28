package com.yelp.yelpapplication

import android.content.Intent
import android.os.Bundle
import android.os.StrictMode
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yelp.yelpapplication.adapter.TableRowAdapter
import com.yelp.yelpapplication.model.Business
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder


class MainActivity : AppCompatActivity(){
    private lateinit var tableRecyclerView : RecyclerView
    private var userList = ArrayList<Business>()
    private lateinit var tableRowAdapter: TableRowAdapter
    private lateinit var user : Business
    override fun onCreate(savedInstanceState: Bundle?) {
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val key = findViewById<EditText>(R.id.keyword)
        val radius = findViewById<EditText>(R.id.distance)
        val loc = findViewById<EditText>(R.id.location)
        var category = ""


        val spinner = findViewById<Spinner>(R.id.spinner)
        val items = arrayOf("Default", "Arts & Entertainment", "Health & Medical", "Hotels & Travel", "Food", "Professional Services")
        val adapter = ArrayAdapter<String>(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            items
        )
        spinner.adapter = adapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                arg0: AdapterView<*>?,
                arg1: View?,
                arg2: Int,
                arg3: Long
            ) {
                // Do what you want
                category = spinner.selectedItem.toString()
                Toast.makeText(applicationContext, category, Toast.LENGTH_LONG).show()
            }
            override fun onNothingSelected(arg0: AdapterView<*>?) {}
        }

        // search button
        val searchButton = findViewById(R.id.button) as Button
        // set on-click listener
        searchButton.setOnClickListener {
            Toast.makeText(applicationContext, key.text.toString(), Toast.LENGTH_LONG).show()
            getBusinesses(key.text.toString(), radius.text.toString(), category, loc.text.toString())

            // adding data to table
            val tableBusinesses = findViewById(R.id.table_heading_layout) as TableLayout
//            val row = TableRow(this)
//            row.setPadding(10)
//            var  param: RelativeLayout.LayoutParams = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, 50)
//            param.setMargins(10,8,0,0)
//            val i:Int=1
//            val businessNumber = TextView(this)
//            businessNumber.id=i
//            businessNumber.layoutParams = param
//            businessNumber.text = "ALl ALI OUSda"
//            row.addView(businessNumber)
//            tableBusinesses.addView(row)

            userList.add(Business(
                1,
                "gsdsg9-fHBbkMstMU0GHEg",
                "https://s3-media4.fl.yelpcdn.com/bphoto/V4RCxqC_Ywb4VzHpmN4avw/o.jpg",
                24, "Papa John's"
            ))

            userList.add(Business(
                2,
                "hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh",
                "https://s3-media4.fl.yelpcdn.com/bphoto/V4RCxqC_Ywb4VzHpmN4avw/o.jpg",
                24, "Ali Ali"
            ))

            tableRecyclerView = findViewById(R.id.table_recycler_view)

            tableRowAdapter = TableRowAdapter(this, userList)

            tableRecyclerView.layoutManager = LinearLayoutManager(this)
            tableRecyclerView.adapter = tableRowAdapter

        }
    }

    private fun getBusinesses(key:String, dis:String, cat: String, loc: String) {

        var reqParam = URLEncoder.encode("keyword", "UTF-8") + "=" + URLEncoder.encode(key, "UTF-8")
        reqParam += "&" + URLEncoder.encode("distance", "UTF-8") + "=" + URLEncoder.encode(dis, "UTF-8")
        reqParam += "&" + URLEncoder.encode("category", "UTF-8") + "=" + URLEncoder.encode(cat, "UTF-8")
        reqParam += "&" + URLEncoder.encode("location", "UTF-8") + "=" + URLEncoder.encode(loc, "UTF-8")

        val mURL = URL("https://asmaa-app.herokuapp.com/api/getBusinesses?$reqParam")

        with(mURL.openConnection() as HttpURLConnection) {
            // optional default is GET
            requestMethod = "GET"

            println("URL : $url")
            println("Response Code : $responseCode")

            BufferedReader(InputStreamReader(inputStream)).use {
                val response = StringBuffer()

                var inputLine = it.readLine()
                while (inputLine != null) {
                    response.append(inputLine)
                    inputLine = it.readLine()
                }
                it.close()
                println("Response : $response")
            }
        }
    }
}