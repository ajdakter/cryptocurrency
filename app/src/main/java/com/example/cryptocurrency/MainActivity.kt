package com.example.cryptocurrency

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import com.android.volley.Response
import java.util.HashMap

class MainActivity : AppCompatActivity() {

    var coinObjectArray: Array<CoinObject> = Array<CoinObject>(40) { CoinObject() }
    var myBaseAdapter = CoinsBaseAdapter(this, coinObjectArray)
    val url = "https://api.coinranking.com/v1/public/coins"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val coinsObject = JsonObjectRequest(Request.Method.GET, url, null,
            object : Response.Listener<JSONObject> {
               //header üzerinde packageName anahtar kelimesiyle uygulamanın paket adını yollama
                @Throws(AuthFailureError::class)
                fun getHeaders(): Map<String, String>? {
                    val myCoinHeader: MutableMap<String, String> = HashMap()
                    myCoinHeader.put("packageName", "package com.example.cryptocurrency")

                    return myCoinHeader
                }

                override fun onResponse(response: JSONObject?) {
                    progress.visibility = View.INVISIBLE
                    getHeaders()
                    dataParsing(response, coinObjectArray)

                    listCoins.adapter = myBaseAdapter
                    listCoins.setOnItemClickListener { parent, view, position, id ->
                        setMyDetailsPage(coinObjectArray, position)
                    }
                }
            },
            object : Response.ErrorListener {
                override fun onErrorResponse(error: VolleyError?) {
                    var progress: ProgressBar = findViewById(R.id.progress)
                    progress.isIndeterminate = true
                    Toast.makeText(
                        applicationContext,
                        "Checking your internet connection...",
                        Toast.LENGTH_LONG
                    ).show()
                    progress.visibility = View.VISIBLE
                }
            })

        MySingleton.getInstance(this).addToRequestQueue(coinsObject)
    }

    fun setMyDetailsPage(coinObjectArray: Array<CoinObject>, position: Int) {

        var intent = Intent(this@MainActivity, CoinsDetailActivity::class.java)

        intent.putExtra("c_description", coinObjectArray[position].c_description)
        intent.putExtra("c_symbol", coinObjectArray[position].c_symbol)
        intent.putExtra("c_price", coinObjectArray[position].c_price)
        intent.putExtra("c_iconUrl", coinObjectArray[position].c_iconUrl)
        intent.putExtra("c_name", coinObjectArray[position].c_name)
        startActivity(intent)

    }

}

fun dataParsing(response: JSONObject?, coinObjectArray: Array<CoinObject>) {

    for (i in 0..39) {

        var data = response?.getJSONObject("data")
        var name = data?.getJSONArray("coins")?.getJSONObject(i)?.getString("name")
        var symbol = data?.getJSONArray("coins")?.getJSONObject(i)?.getString("symbol")
        var description = data?.getJSONArray("coins")?.getJSONObject(i)?.getString("description")
        var color = data?.getJSONArray("coins")?.getJSONObject(i)?.getString("color")
        var iconUrl = data?.getJSONArray("coins")?.getJSONObject(i)?.getString("iconUrl")
        var price =
            (data?.getJSONArray("coins")?.getJSONObject(i)?.getString("price")?.toDouble()).toString()

        if (description == "null") {
            description = "A flat disc or piece of metal with an official stamp, used as money."
        }
        coinObjectArray[i] = CoinObject(
            name.toString(), symbol.toString(), description.toString(),
            price.toDouble(), color.toString(), iconUrl.toString()
        )
    }
}


