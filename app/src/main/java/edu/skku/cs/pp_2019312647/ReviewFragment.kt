package edu.skku.cs.pp_2019312647

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class ReviewFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_review, container, false)
        // Inflate the layout for this fragment
        val whiskeyView = view.findViewById<GridView>(R.id.reviewwhiskeyView)
        val okHttpClient = OkHttpClient()
        val url = "https://4vkvor4dc5.execute-api.us-east-1.amazonaws.com/default/SearchBar"
        val new_url = Uri.parse(url).buildUpon().appendQueryParameter("type","whiskey").toString()
        val request = Request.Builder().url(new_url).build()

        okHttpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) { }
            override fun onResponse(call: Call, response: Response){
                var resp = response.body!!.string()
                if(resp != null){
                    CoroutineScope(Dispatchers.Main).launch {
                        println(resp)
                        val gson = Gson()
                        val whiskyList: List<WhiskeyCell> = gson.fromJson(resp, Array<WhiskeyCell>::class.java).toList()
                        val items = ArrayList<WhiskeyCell>()
                        for (item in whiskyList) {
                            val id = item.id
                            val name = item.name
                            val price = item.price
                            val origin = item.origin
                            val link = item.link
                            val alcohol = item.alcohol
                            val description = item.description

                            val Data = WhiskeyCell(id,name,price,origin,link,alcohol,description)
                            items.add(Data)
                        }
                        val whiskeyAdapter = WhiskeyAdapter(items,requireContext())
                        val gridView = view.findViewById<GridView>(R.id.reviewwhiskeyView)

                        gridView.adapter = whiskeyAdapter

                        gridView.setOnItemClickListener { parent, view, position, id ->
                            val clickedItem = items[position]
                            println(clickedItem)
                            val intent = Intent(activity, ReviewActivity::class.java)
                            intent.putExtra("id",clickedItem.id.toString())
                            println(clickedItem.id)
                            intent.putExtra("name",clickedItem.name)
                            intent.putExtra("price",clickedItem.price)
                            intent.putExtra("alcohol",clickedItem.alcohol)
                            intent.putExtra("origin",clickedItem.origin)
                            intent.putExtra("description",clickedItem.description)
                            intent.putExtra("link",clickedItem.link)
                            startActivity(intent)
                        }
                    }
                }
            }
        })



        return view
    }


}