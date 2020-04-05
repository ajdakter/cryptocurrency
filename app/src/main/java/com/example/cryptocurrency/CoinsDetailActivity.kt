package com.example.cryptocurrency

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou
import kotlinx.android.synthetic.main.activity_coins_detail.*
import java.lang.Exception
import java.text.DecimalFormat

class CoinsDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coins_detail)

        val df = DecimalFormat("0.00")

        var c_description = intent.extras?.get("c_description")
        var c_price = intent.extras?.get("c_price") as Double
        val c_symbol = intent.extras?.get("c_symbol")
        val c_name = intent.extras?.get("c_name")
        val c_iconUri: Uri = Uri.parse(intent.extras?.get("c_iconUrl").toString())

        try {
            supportActionBar?.setTitle(c_symbol.toString())
            tvCoinsDetails.setText(c_description.toString())
            tvCoinsName.setText(c_name.toString())

            tvCoinsDPrice.setText(df.format(c_price))
            GlideToVectorYou.justLoadImage(
                this,
                c_iconUri,
                imgCoinsDetail
            )
        } catch (error: Exception) {

            Toast.makeText(
                this@CoinsDetailActivity,
                "Unexpected error has occurred",
                Toast.LENGTH_LONG
            ).show()
            Log.e("Error : ", "CoinsDetailActivity : " + error.toString())
        }
    }
}
