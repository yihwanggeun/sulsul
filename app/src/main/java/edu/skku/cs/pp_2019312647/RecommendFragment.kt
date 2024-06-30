package edu.skku.cs.pp_2019312647

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.SeekBar
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

class RecommendFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_recommend, container, false)
        // Inflate the layout for this fragment

        val seekBar1 = view.findViewById<SeekBar>(R.id.seekBar4)
        val seekBar2 = view.findViewById<SeekBar>(R.id.seekBar5)
        val seekBar3 = view.findViewById<SeekBar>(R.id.seekBar6)
        val seekBar4 = view.findViewById<SeekBar>(R.id.seekBar7)
        val recommendButton = view.findViewById<Button>(R.id.recommendbutton)
        var first = 0
        var second = 0
        var third = 0
        var fourth = 0
        recommendButton.setOnClickListener {
            var targetId = first+second+third+fourth
            print ("targetID : ");println(targetId)
            val okHttpClient = OkHttpClient()
            val url = "https://hx5k2dqw2m.execute-api.us-east-1.amazonaws.com/default/SearchWhiskey"
            val new_url = Uri.parse(url).buildUpon().appendQueryParameter("type","whiskey").toString();
            val request = Request.Builder().url(new_url).build()

            okHttpClient.newCall(request).enqueue(object : Callback{
                override fun onFailure(call: Call, e: IOException) { }
                override fun onResponse(call: Call,response: Response){
                    var resp = response.body!!.string();
                    if(resp != null){
                        CoroutineScope(Dispatchers.Main).launch {
                            val gson = Gson()
                            val whiskyList: List<WhiskeyCell> = gson.fromJson(resp, Array<WhiskeyCell>::class.java).toList()
                            val items = ArrayList<WhiskeyCell>()
                            for (item in whiskyList) {
                                if(item.id==targetId){
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
                            }

                            val clickedItem = items[0]
                            val intent = Intent(activity, InfoActivity::class.java)
                            intent.putExtra("id",clickedItem.id.toString())
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
            })

        }



        seekBar1.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if(progress>=2) first = 1000
                else first = 0

            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }
        })

        seekBar2.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if(progress>=2) second = 100
                else second = 0

            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }
        })
        seekBar3.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if(progress>=2) third = 10
                else third = 0
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }
        })
        seekBar4.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if(progress>=2) fourth = 1
                else fourth = 0
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })




        return view
    }

}