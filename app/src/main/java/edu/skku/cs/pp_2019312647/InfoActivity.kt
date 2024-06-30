package edu.skku.cs.pp_2019312647

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException

class InfoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)
        val id = intent.getStringExtra("id")
        val name = intent.getStringExtra("name")
        val price = intent.getStringExtra("price")
        val alcohol = intent.getStringExtra("alcohol")
        val origin = intent.getStringExtra("origin")
        val description = intent.getStringExtra("description")
        val link = intent.getStringExtra("link")

        val idImageView = findViewById<ImageView>(R.id.imageView3)
        val nameTextView = findViewById<TextView>(R.id.infoname)
        val priceTextView = findViewById<TextView>(R.id.infoprice)
        val alcoholTextView = findViewById<TextView>(R.id.infoalcohol)
        val originTextView = findViewById<TextView>(R.id.infoorigin)
        val descriptionTextView = findViewById<TextView>(R.id.infodescription)
        //val moreButton = findViewById<Button>(R.id.morebutton)
        //val reviewButton = findViewById<Button>(R.id.reviewbutton)
        val ratingBar = findViewById<RatingBar>(R.id.reviewratingBar)



        nameTextView.text = name
        priceTextView.text = price + "원"
        alcoholTextView.text = alcohol + "%"
        originTextView.text = origin + "산"
        descriptionTextView.text = description

        val okHttpClient = OkHttpClient()
        val url = "https://4vkvor4dc5.execute-api.us-east-1.amazonaws.com/default/SearchBar"
        val myApp = application as GlobalApplication
        val get_url = Uri.parse(url).buildUpon().appendQueryParameter("type","rating_get")
            .appendQueryParameter("email",myApp.getEmail()).appendQueryParameter("whiskey_id",id.toString()).build().toString()
        val request2 = Request.Builder().url(get_url).build()
        println(request2)
        okHttpClient.newCall(request2).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
            }

            override fun onResponse(call: Call, response: Response) {
                val responseBody = response.body?.string()
                if(responseBody != "[]"){
                    val json = JSONObject(responseBody)
                    val rating = json.getDouble("rating")
                    println(responseBody)
                    ratingBar.rating = rating.toFloat()
                    print(rating)
                }

                else{

                }
                // JSON 응답 파싱

            }
        })
        println(id)
        when (id?.toInt()) {
            0 -> idImageView.setImageResource(R.drawable.a0)
            1 -> idImageView.setImageResource(R.drawable.a1)
            10 -> idImageView.setImageResource(R.drawable.a10)
            11 -> idImageView.setImageResource(R.drawable.a11)
            100 -> idImageView.setImageResource(R.drawable.a100)
            101 -> idImageView.setImageResource(R.drawable.a101)
            110 -> idImageView.setImageResource(R.drawable.a110)
            111 -> idImageView.setImageResource(R.drawable.a111)
            1000 -> idImageView.setImageResource(R.drawable.a1000)
            1001 -> idImageView.setImageResource(R.drawable.a1001)
            1010 -> idImageView.setImageResource(R.drawable.a1010)
            1011 -> idImageView.setImageResource(R.drawable.a1011)
            1100 -> idImageView.setImageResource(R.drawable.a1100)
            1101 -> idImageView.setImageResource(R.drawable.a1101)
            1110 -> idImageView.setImageResource(R.drawable.a1110)
            1111 -> idImageView.setImageResource(R.drawable.a1111)
        }


        ratingBar.onRatingBarChangeListener = RatingBar.OnRatingBarChangeListener { _, rating, _ ->
            // 값이 변경될 때마다 호출되는 코드 작성
            var rating = ratingBar.rating

            val okHttpClient = OkHttpClient()
            val url = "https://4vkvor4dc5.execute-api.us-east-1.amazonaws.com/default/SearchBar"
            val new_url = Uri.parse(url).buildUpon().appendQueryParameter("type","rating")
                .appendQueryParameter("rating",rating.toString()).appendQueryParameter("email",myApp.getEmail())
                .appendQueryParameter("whiskey_id",id.toString()).build().toString()
            val request = Request.Builder().url(new_url).build()

            println(request)
            okHttpClient.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {

                }

                override fun onResponse(call: Call, response: Response) {
                    var resp = response.body!!.string()
                    println(resp)



                }

            })
        }

       idImageView.setOnClickListener {
               val url = link
               val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
               startActivity(intent)

       }


    }

}