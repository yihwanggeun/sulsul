package edu.skku.cs.pp_2019312647

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import org.w3c.dom.Text

class WhiskeyAdapter(val data: ArrayList<WhiskeyCell>, val context: Context) : BaseAdapter() {
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
        val View = inflater.inflate(R.layout.whiskey_cell, parent,false)
        val whiskeyImage = View.findViewById<ImageView>(R.id.whiskeyImage)
        val name = View.findViewById<TextView>(R.id.name)
        val price = View.findViewById<TextView>(R.id.price)
        val alcohol = View.findViewById<TextView>(R.id.alcohol)
        val origin = View.findViewById<TextView>(R.id.origin)
        val drawId = data[position].id
        when (drawId) {
            0 -> whiskeyImage.setImageResource(R.drawable.a0)
            1 -> whiskeyImage.setImageResource(R.drawable.a1)
            10 -> whiskeyImage.setImageResource(R.drawable.a10)
            11 -> whiskeyImage.setImageResource(R.drawable.a11)
            100 -> whiskeyImage.setImageResource(R.drawable.a100)
            101 -> whiskeyImage.setImageResource(R.drawable.a101)
            110 -> whiskeyImage.setImageResource(R.drawable.a110)
            111 -> whiskeyImage.setImageResource(R.drawable.a111)
            1000 -> whiskeyImage.setImageResource(R.drawable.a1000)
            1001 -> whiskeyImage.setImageResource(R.drawable.a1001)
            1010 -> whiskeyImage.setImageResource(R.drawable.a1010)
            1011 -> whiskeyImage.setImageResource(R.drawable.a1011)
            1100 -> whiskeyImage.setImageResource(R.drawable.a1100)
            1101 -> whiskeyImage.setImageResource(R.drawable.a1101)
            1110 -> whiskeyImage.setImageResource(R.drawable.a1110)
            1111 -> whiskeyImage.setImageResource(R.drawable.a1111)
        }
        name.text = data[position].name
        price.text = data[position].price + "원"
        alcohol.text = data[position].alcohol +"%"
        origin.text = data[position].origin
        println(data[position].id)
        return View
    }
}