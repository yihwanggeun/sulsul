package edu.skku.cs.pp_2019312647

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.Places
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

class HomeFragment : Fragment(), OnMapReadyCallback {
    private lateinit var mMap: GoogleMap
    private lateinit var mapView: MapView

    private val REQUEST_LOCATION_PERMISSION = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val layout = inflater.inflate(R.layout.fragment_home, container, false)
        val searchButton = layout.findViewById<Button>(R.id.searchbutton)
        val inputTextView = layout.findViewById<EditText>(R.id.inputEditText)
        Places.initialize(requireContext(), "AIzaSyCj66IATDncqelfczoWWIYI_JbA_5_8T4M")

        searchButton.setOnClickListener {
            if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // 권한이 허용되지 않았을 경우, 권한을 요청합니다.
                ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_LOCATION_PERMISSION)
            } else {
                // 권한이 이미 허용되었을 경우, 원하는 기능을 수행합니다.
                // 여기에 맵과 관련된 작업을 수행하는 코드를 추가하세요.
            }
            val userQuery = inputTextView.text.toString()
            val placeSearchUrl = "https://maps.googleapis.com/maps/api/place/findplacefromtext/json?input=$userQuery&inputtype=textquery&fields=geometry&key=AIzaSyCj66IATDncqelfczoWWIYI_JbA_5_8T4M"


            val client = OkHttpClient()
            val placeSearchRequest = Request.Builder().url(placeSearchUrl).build()

            client.newCall(placeSearchRequest).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    e.printStackTrace()
                }

                override fun onResponse(call: Call, response: Response) {
                    if (response.isSuccessful) {
                        val placeSearchResponse = response.body?.string()
                        println(placeSearchResponse)
                        val placeSearchResult = Gson().fromJson(placeSearchResponse, PlaceSearchResult::class.java)
                        if (placeSearchResult.candidates.isNotEmpty()) {
                            val location = placeSearchResult.candidates[0].geometry.location


                            val placesUrl =
                                "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=${location.lat},${location.lng}&radius=1500&type=bar&key=AIzaSyCj66IATDncqelfczoWWIYI_JbA_5_8T4M"
                            println(placesUrl)
                            val placesRequest = Request.Builder().url(placesUrl).build()

                            client.newCall(placesRequest).enqueue(object : Callback {
                                override fun onFailure(call: Call, e: IOException) {
                                    e.printStackTrace()
                                }

                                override fun onResponse(call: Call, response: Response) {

                                    if (response.isSuccessful) {
                                        val placesResponse = response.body?.string()
                                        println(placesResponse)
                                        val placesResult = Gson().fromJson(
                                            placesResponse,
                                            PlacesNearbySearchResult::class.java
                                        )


                                        activity?.runOnUiThread {
                                            mMap.clear()


                                            for (result in placesResult.results) {
                                                val latLng = LatLng(
                                                    result.geometry.location.lat,
                                                    result.geometry.location.lng
                                                )
                                                val markerOptions = MarkerOptions().position(latLng)
                                                    .title(result.name)
                                                mMap.addMarker(markerOptions)


                                            }
                                            mMap.moveCamera(
                                                CameraUpdateFactory.newLatLngZoom(
                                                    LatLng(
                                                        placesResult.results[0].geometry.location.lat,
                                                        placesResult.results[0].geometry.location.lng
                                                    ), 15F
                                                )
                                            )
                                        }
                                    }
                                }
                            })
                        }
                        else{
                            activity?.runOnUiThread {
                                Toast.makeText(requireContext(), "검색 결과가 없습니다.", Toast.LENGTH_SHORT).show()
                            }
                        }
                        }
                    }
                })
            }


        mapView = layout.findViewById(R.id.vmap)
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)

        return layout
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        val marker = LatLng(37.295881, 126.975931)
        mMap.addMarker(MarkerOptions().position(marker).title("Marker"))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(marker, 10F))
    }

    override fun onStart() {
        super.onStart()
        mapView.onStart()
    }

    override fun onStop() {
        super.onStop()
        mapView.onStop()
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    override fun onDestroy() {
        mapView.onDestroy()
        super.onDestroy()
    }
}
