package com.example.lubuntupc.volleyapplication

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    var volleyRequest: RequestQueue? = null
    val stringLink = "http://magadistudio.com/complete-android-developer-course-source-files/string.html"
    val movieLink = "https://jsonplaceholder.typicode.com/posts"
    val earthquakeLink = "https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/1.0_hour.geojson"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        volleyRequest = Volley.newRequestQueue(this)

        getJsonObject(earthquakeLink)
       // getJsonArray(movieLink)
        //getString(stringLink)

    }

    fun getJsonObject(Url: String) {
        val jsonObjectReq = JsonObjectRequest(Request.Method.GET, Url,
                Response.Listener {
                    response: JSONObject ->
                    try {

//                        var type = response.getString("type")
//                        Log.d("Type: ", type)

                        val metadataObject = response.getJSONObject("metadata")
                        val title = metadataObject.getString("title")
                        Log.d("Title", title)

                        //get feature jsonarray
                        val featuresArray = response.getJSONArray("features")
                        for (i in 0..featuresArray.length() - 1) {
                            val propertyObj = featuresArray.getJSONObject(i).getJSONObject("properties")
                            val place =propertyObj.getString("place")
                            Log.d("Place", place)

                            //geometry object
                            val geometryObj = featuresArray.getJSONObject(i).getJSONObject("geometry")
                            val coordJsonArray = geometryObj.getJSONArray("coordinates")

                            //for (g in 0..coordJsonArray.length() - 1) {
                                val firstCoor = coordJsonArray.getDouble(0)
                                Log.d("coordinate: ", firstCoor.toString())
                          //  }

                        }

                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                },
                Response.ErrorListener {
                    error: VolleyError? ->
                    try {

                        Log.d("Error: ", error.toString())

                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                })
        volleyRequest!!.add(jsonObjectReq)
    }

    fun getJsonArray(Url: String) {
        val jsonArray = JsonArrayRequest(Request.Method.GET, Url,
                Response.Listener {
                    response: JSONArray ->
                    try {
                        Log.d("Response:===>", response.toString())

                        for ( i in 0..response.length() -1 ) {
                            val titleObj = response.getJSONObject(i)

                            var showTitle = titleObj.getString("title")

                            Log.d("Show Title: ", showTitle)
                        }

                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                },
                Response.ErrorListener {
                    error: VolleyError? ->
                    try {
                        Log.d("Error:===>", error.toString())
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                })
        volleyRequest!!.add(jsonArray)
    }

    fun getString(Url: String) {
        val stringReq = StringRequest(Request.Method.GET, Url,
                Response.Listener {
                    response -> String
                    try {
                        Log.d("Response: ", response)
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                 },
                Response.ErrorListener {
                    error: VolleyError? ->
                    try {
                        Log.d("Error:", error.toString())
                    }catch (e: JSONException) {
                        e.printStackTrace()
                    }
                })
        volleyRequest!!.add(stringReq)
    }
}
