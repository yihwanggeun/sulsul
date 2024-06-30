package edu.skku.cs.pp_2019312647

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.GridView
import android.widget.ImageView
import android.widget.ListView
import android.widget.RatingBar
import android.widget.TextView
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException

class ReviewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_review)

        val id = intent.getStringExtra("id")
        val name = intent.getStringExtra("name")
        val price = intent.getStringExtra("price")
        val alcohol = intent.getStringExtra("alcohol")
        val origin = intent.getStringExtra("origin")
        val description = intent.getStringExtra("description")
        val link = intent.getStringExtra("link")
        val ratingBar = findViewById<RatingBar>(R.id.reviewratingBar)
        val sharebutton = findViewById<Button>(R.id.sharebutton)
        val reviewEditText = findViewById<EditText>(R.id.reviewEditText)
        val image = findViewById<ImageView>(R.id.imageView3)
        val nam = findViewById<TextView>(R.id.infoname)
        val pri = findViewById<TextView>(R.id.infoprice)

        nam.text = name.toString()
        pri.text = price.toString()
        when (id?.toInt()) {
            0 -> image.setImageResource(R.drawable.a0)
            1 -> image.setImageResource(R.drawable.a1)
            10 -> image.setImageResource(R.drawable.a10)
            11 -> image.setImageResource(R.drawable.a11)
            100 -> image.setImageResource(R.drawable.a100)
            101 -> image.setImageResource(R.drawable.a101)
            110 -> image.setImageResource(R.drawable.a110)
            111 -> image.setImageResource(R.drawable.a111)
            1000 -> image.setImageResource(R.drawable.a1000)
            1001 -> image.setImageResource(R.drawable.a1001)
            1010 -> image.setImageResource(R.drawable.a1010)
            1011 -> image.setImageResource(R.drawable.a1011)
            1100 -> image.setImageResource(R.drawable.a1100)
            1101 -> image.setImageResource(R.drawable.a1101)
            1110 -> image.setImageResource(R.drawable.a1110)
            1111 -> image.setImageResource(R.drawable.a1111)
        }


        val okHttpClient = OkHttpClient()
        val url = "https://4vkvor4dc5.execute-api.us-east-1.amazonaws.com/default/SearchBar"
        val myApp = application as GlobalApplication
        val review_url = Uri.parse(url).buildUpon().appendQueryParameter("type","review").appendQueryParameter("whiskey_id",id.toString()).build().toString()
        val review_request = Request.Builder().url(review_url).build()

        okHttpClient.newCall(review_request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
            }

            override fun onResponse(call: Call, response: Response) {
                var reviewrespond = response.body!!.string()
                Log.d("ReviewActivity", "Review Response: $reviewrespond")
                if (reviewrespond != null) {
                    CoroutineScope(Dispatchers.Main).launch {

                        val gson = Gson()
                        val ReviewList: List<ReviewCell> =
                            gson.fromJson(reviewrespond, Array<ReviewCell>::class.java).toList()
                        val items = ArrayList<ReviewCell>()
                        for (item in ReviewList) {
                            var nickname = item.nickname
                            var gender = item.gender
                            var age_group = item.age_group
                            var comment = item.comment
                            var rating = item.rating!!.toFloat()

                            if(rating != 0.toFloat()) {
                                val Data = ReviewCell(nickname, gender, age_group, comment, rating)
                                println(Data)
                                items.add(Data)
                            }

                        }
                        println(items)
                        val reviewAdapter = ReviewAdapter(items, this@ReviewActivity)
                        val listView = findViewById<ListView>(R.id.reviewListView)
                        listView.adapter = reviewAdapter
                    }
                }
            }
        })




        val get_url = Uri.parse(url).buildUpon().appendQueryParameter("type","rating_get")
            .appendQueryParameter("email",myApp.getEmail()).appendQueryParameter("whiskey_id",id.toString()).build().toString()
        val request2 = Request.Builder().url(get_url).build()

        okHttpClient.newCall(request2).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
            }

            override fun onResponse(call: Call, response: Response) {
                val responseBody = response.body?.string()
                println(responseBody)
                // JSON 응답 파싱
                val json = JSONObject(responseBody)
                val rating = json.getDouble("rating")
                println(rating)
                ratingBar.rating = rating.toFloat()
                print(rating)
            }
        })


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



        sharebutton.setOnClickListener {
            val comment = reviewEditText.text.toString()
            val okHttpClient = OkHttpClient()
            val url = "https://4vkvor4dc5.execute-api.us-east-1.amazonaws.com/default/SearchBar"
            val myApp = application as GlobalApplication
            val share_url = Uri.parse(url).buildUpon().appendQueryParameter("type","share")
                .appendQueryParameter("email",myApp.getEmail()).appendQueryParameter("whiskey_id",id.toString())
                .appendQueryParameter("comment",comment).build().toString()
            val share_request = Request.Builder().url(share_url).build()

            okHttpClient.newCall(share_request).enqueue(object : Callback{
                override fun onFailure(call: Call, e: IOException) {

                }

                override fun onResponse(call: Call, response: Response) {
                    var share_resp = response.body!!.toString()
                    println(share_resp)
                }

            })

        }



    }
}