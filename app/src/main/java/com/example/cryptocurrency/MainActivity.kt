package com.example.cryptocurrency

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import java.text.DecimalFormat

class MainActivity : AppCompatActivity() {

    var symbolCoinArray: Array<String> = Array<String>(40) { "" }
    var priceCoinArray: Array<String> = Array<String>(40) { "" }
    var iconUrlCoinsArray: Array<String> = Array<String>(40) { "" }
    var colorCoinsArray: Array<String> = Array<String>(40) { "" }
    var nameCoinsArray: Array<String> = Array<String>(40) { "" }
    var descriptionCoinsArray: Array<String> = Array<String>(40) { "" }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        var coinAdapter =
            CoinsArrayAdapter(
                this,
                R.layout.custom_coins_row,
                R.id.tvCoinsSymbol,
                symbolCoinArray,
                priceCoinArray,
                iconUrlCoinsArray,
                colorCoinsArray
            )

        val url = "https://api.coinranking.com/v1/public/coins"
        val coinsObject = JsonObjectRequest(
            Request.Method.GET,
            url,
            null,
            object : Response.Listener<JSONObject> {

                override fun onResponse(response: JSONObject?) {

                    getCoinsFeatures(response, "symbol", symbolCoinArray)
                    getCoinsFeatures(response, "price", priceCoinArray)
                    getCoinsFeatures(response, "iconUrl", iconUrlCoinsArray)
                    getCoinsFeatures(response, "color", colorCoinsArray)
                    getCoinsFeatures(response, "name", nameCoinsArray)
                    getCoinsFeatures(response, "description", descriptionCoinsArray)
                    listCoins.adapter = coinAdapter
                    coinAdapter.notifyDataSetChanged()

                    listCoins.setOnItemClickListener { parent, view, position, id ->

                        var intent=Intent(this@MainActivity,CoinsDetailActivity::class.java)
                        intent.putExtra("position",position)
                        intent.putExtra("price",priceCoinArray)
                        intent.putExtra("iconUrl",iconUrlCoinsArray)
                        intent.putExtra("description",descriptionCoinsArray)
                        intent.putExtra("name",nameCoinsArray)
                        intent.putExtra("symbol",symbolCoinArray)
                        startActivity(intent)

                    }

                }

            },
            object : Response.ErrorListener {
                override fun onErrorResponse(error: VolleyError?) {
                    Toast.makeText(applicationContext, " Error", Toast.LENGTH_SHORT).show()
                }
            })

        MySingleton.getInstance(this).addToRequestQueue(coinsObject)
   }

    private fun getCoinsFeatures(response: JSONObject?, option: String, array: Array<String>) {

        for (i in 0..39) {
            if (option == "price") {

                var data = response?.getJSONObject("data")
                val df = DecimalFormat("#.##")
                array[i] =
                    (df.format(data?.getJSONArray("coins")?.getJSONObject(i)?.getString(option)?.toDouble())).toString()
            } else {

                var data = response?.getJSONObject("data")
                array[i] =
                    data?.getJSONArray("coins")?.getJSONObject(i)?.getString(option).toString()

            }
        }
    }
}

