package edu.skku.cs.pp_2019312647

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

class RecommendActivity : AppCompatActivity() {
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


        nameTextView.text = name
        priceTextView.text = price + "원"
        alcoholTextView.text = alcohol + "%"
        originTextView.text = origin + "산"
        descriptionTextView.text = description
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
        /*moreButton.setOnClickListener {
            val url = link
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        }*/
    }
}