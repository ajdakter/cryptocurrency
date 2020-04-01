package com.example.cryptocurrency

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.*
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val cache = DiskBasedCache(cacheDir, 1024 * 1024) // 1MB cap
        val network = BasicNetwork(HurlStack())

        val requestQueue = RequestQueue(cache, network).apply {
            start()
        }
        val url = "https://api.coinranking.com/v1/public/coins"
        val coinsObject = JsonObjectRequest(
            Request.Method.GET,
            url,
            null,
            object : Response.Listener<JSONObject> {

                override fun onResponse(response: JSONObject?) {

                    var data = response?.getJSONObject("data")
                    var name = data?.getJSONArray("coins")?.getJSONObject(0)?.getString("name")
                    var symbol = data?.getJSONArray("coins")?.getJSONObject(0)?.getString("symbol")
                    var description =
                        data?.getJSONArray("coins")?.getJSONObject(0)?.getString("description")
                    var color = data?.getJSONArray("coins")?.getJSONObject(0)?.getString("color")
                    var iconUrl =
                        data?.getJSONArray("coins")?.getJSONObject(0)?.getString("iconUrl")
                    var price = data?.getJSONArray("coins")?.getJSONObject(0)?.getString("price")

                }

            },
            object : Response.ErrorListener {
                override fun onErrorResponse(error: VolleyError?) {
                    Toast.makeText(applicationContext, " Error", Toast.LENGTH_SHORT).show()
                }
            })

        MySingleton.getInstance(this).addToRequestQueue(coinsObject)


    }
}
