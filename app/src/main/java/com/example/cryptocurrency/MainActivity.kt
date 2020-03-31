package com.example.cryptocurrency

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.BasicNetwork
import com.android.volley.toolbox.DiskBasedCache
import com.android.volley.toolbox.HurlStack
import com.android.volley.toolbox.StringRequest

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val cache = DiskBasedCache(cacheDir, 1024 * 1024) // 1MB cap

// Set up the network to use HttpURLConnection as the HTTP client.
        val network = BasicNetwork(HurlStack())

// Instantiate the RequestQueue with the cache and network. Start the queue.
        val requestQueue = RequestQueue(cache, network).apply {
            start()
        }

        val url = "https://api.coinranking.com/v1/public/coins"


// Formulate the request and handle the response.
        val stringRequest = StringRequest(Request.Method.GET, url,
                Response.Listener<String> { response -> Toast.makeText(applicationContext," Done", Toast.LENGTH_SHORT).show()
                    // Do something with the response
                },
                Response.ErrorListener { error -> Toast.makeText(applicationContext," ERROR", Toast.LENGTH_SHORT).show()
                })

// Add the request to the RequestQueue.
        requestQueue.add(stringRequest)


    }
}
