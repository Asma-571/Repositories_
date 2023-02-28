package com.yelp.yelpapplication.adapter
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.yelp.yelpapplication.MainActivity
import com.yelp.yelpapplication.NewActivity
import com.yelp.yelpapplication.R
import com.yelp.yelpapplication.model.Business
import java.util.*


class TableRowAdapter(var mContext: Context, private var userArrayList: ArrayList<Business>) :
    RecyclerView.Adapter<TableRowAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val v: View = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.table_row_layout, viewGroup, false)
//        val intent = Intent(mContext,NewActivity::class.java)
//        v.isClickable = true
        v.isFocusableInTouchMode = false
        v.setOnClickListener {
            println("view i: $i")
            println("view id for page: " + userArrayList[i].id)
        }
        return ViewHolder(v)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
//        viewHolder.tvUserName = userArrayList[i].name
        DownloadImageFromInternet(viewHolder.tvUserName).execute(userArrayList[i].name)
        viewHolder.tvAge.text = userArrayList[i].age.toString() + i.toString()
        viewHolder.tvDesignation.text = userArrayList[i].designation
        viewHolder.tvNumber.text = userArrayList[i].number.toString()
    }

    override fun getItemCount(): Int {
        return userArrayList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvNumber: TextView = itemView.findViewById(R.id.number)
        val tvUserName: ImageView = itemView.findViewById(R.id.tv_user_name)
        val tvAge: TextView = itemView.findViewById(R.id.tv_user_age)
        val tvDesignation: TextView = itemView.findViewById(R.id.tv_user_designation)

        init {
            itemView.setOnClickListener {
                for(obj in userArrayList){
                    if(obj.number.toString() == tvNumber.text){
                        mContext.startActivity(Intent(mContext, NewActivity::class.java).
                        putExtra("id", obj.id))
                    }
                }

            }
        }
    }

    @SuppressLint("StaticFieldLeak")
    @Suppress("DEPRECATION")
    private inner class DownloadImageFromInternet(var imageView: ImageView) : AsyncTask<String, Void, Bitmap?>() {
        override fun doInBackground(vararg urls: String): Bitmap? {
            val imageURL = urls[0]
            var image: Bitmap? = null
            try {
                val `in` = java.net.URL(imageURL).openStream()
                image = BitmapFactory.decodeStream(`in`)
            }
            catch (e: Exception) {
                Log.e("Error Message", e.message.toString())
                e.printStackTrace()
            }
            return image
        }
        override fun onPostExecute(result: Bitmap?) {
            imageView.setImageBitmap(result)
        }
    }
}
