package com.example.cryptocurrency

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.BasicNetwork
import com.android.volley.toolbox.DiskBasedCache
import com.android.volley.toolbox.HurlStack
import com.android.volley.toolbox.JsonObjectRequest
import org.json.JSONObject


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var CoinObjectArray: Array<CoinObject> = Array<CoinObject>(40) { CoinObject() }

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
                    CoinObjectArray = dataParsing(response)


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

fun dataParsing(response: JSONObject?): Array<CoinObject> {
    var coinObjectArray: Array<CoinObject> = Array<CoinObject>(40) { CoinObject() }

    for (i in 0..39) {

        var data = response?.getJSONObject("data")
        var name = data?.getJSONArray("coins")?.getJSONObject(i)?.getString("name")
        var symbol =
            data?.getJSONArray("coins")?.getJSONObject(i)?.getString("symbol")
        var description =
            data?.getJSONArray("coins")?.getJSONObject(i)?.getString("description")
        var color =
            data?.getJSONArray("coins")?.getJSONObject(i)?.getString("color")
        var iconUrl =
            data?.getJSONArray("coins")?.getJSONObject(i)?.getString("iconUrl")
        var price =
            data?.getJSONArray("coins")?.getJSONObject(i)?.getString("price")

        coinObjectArray[i] = CoinObject(
            name.toString(), symbol.toString(), description.toString(),
            price?.toDouble()!!, color.toString(), iconUrl.toString()
        )
    }
    return coinObjectArray
}

