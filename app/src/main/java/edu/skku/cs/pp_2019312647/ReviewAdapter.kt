package edu.skku.cs.pp_2019312647

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import org.w3c.dom.Text

class ReviewAdapter (val data: ArrayList<ReviewCell>, val context: Context):BaseAdapter(){
    override fun getCount(): Int {
        return data.size
    }

    override fun getItem(position: Int): Any {
        return data[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val View = inflater.inflate(R.layout.review_cell, parent,false)

        val genderImageView = View.findViewById<ImageView>(R.id.genderImageView)
        val nickname = View.findViewById<TextView>(R.id.nicknameTextVIew)
        val age =  View.findViewById<TextView>(R.id.ageTextView)
        val ratingBar = View.findViewById<RatingBar>(R.id.reviewratingBar)
        val comment =  View.findViewById<TextView>(R.id.commentTextView)

        if(data[position].gender == "MALE")  genderImageView.setImageResource(R.drawable.male)
        else genderImageView.setImageResource(R.drawable.female)

        nickname.text = data[position].nickname.toString()
        age.text = data[position].age_group.toString() + "살"
        ratingBar.rating = data[position].rating!!.toFloat()
        comment.text = data[position].comment

        print(nickname.text.toString())

        return View
    }


}